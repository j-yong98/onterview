<script setup>
import { ref, onMounted, onBeforeUnmount, watch } from 'vue';
import { OpenVidu } from 'openvidu-browser';
import { useRouter } from "vue-router";
import { useInterviewStore, useWebsocketStore } from "@/stores/interview";
import { useUserStore } from "@/stores/user"
import { fileServer } from "@/api/video";
import { deleteInterviewRoom } from "@/api/interview";
import OvVideo from "@/components/interview/OvVideo.vue";
import { v4 as uuidv4 } from 'uuid'
import logo from '@/assets/logo.png'

const userStore = useUserStore()
const websocketStore = useWebsocketStore()
const interviewStore = useInterviewStore()
const router = useRouter()

const OV = new OpenVidu();
const session = OV.initSession()
const publisher = ref(undefined);
const subscribers = ref([]);
const myStream = ref(null)
const dialog = ref(false)
const loading = ref(false)
const headers = {
    Authorization: userStore.accessToken
}
OV.enableProdMode()

let recorder
let recordedChunks = [];
let endOfChunk = 0;
const file = ref({
  filename: null,
  time: null,
  title: null,
})
let checkId = -1;
const uploadData = ref([])

const name = userStore.nickname

// 방 입장
const joinSession = async function () {
  
  session.connect(websocketStore.roomData.token, { clientData: {name: name, id: websocketStore.roomData.index} })
    .then(() => {
  
    publisher.value = OV.initPublisher(undefined, {
      audioSource: undefined,
      videoSource: undefined,
      publishAudio: true,
      publishVideo: true,
      resolution: "320x240",
      frameRate: 30,
      insertMode: "APPEND",
      mirror: false,
    })

    session.publish(publisher.value)
    myStream.value = Object(publisher.value.stream)
    
    subscribers.value.push({
      sub: publisher.value,
      id: websocketStore.roomData.index,
    })
  })

  session.on("streamCreated", ({ stream }) => {
    const subscriber = session.subscribe(stream, stream.streamId);
    //console.log("Stream created by", stream, subscriber);
    subscribers.value.push({
      sub: subscriber,
      id: JSON.parse(subscriber.stream.connection.data).clientData.id,
    });
  });
}

const leaveSession = () => {
  
  if (session) {
    session.disconnect();
    if (publisher.value) {
      session.unpublish(publisher.value)
    }
    //mainStreamManager = undefined;
    subscribers.value = [];

    websocketStore.stomp.disconnect()
  }

  websocketStore.now = {
    turn: -1,
    question: {
      id: 0,
      commonQuestion: "",
    },
    orders: [],
    people: 4,
  }
};

//녹화 기능

const startRecording = function() {
  const myVideoStream = document.querySelector(`#${myStream.value.streamId}`).captureStream()
  file.value.filename = uuidv4();
  file.value.time = new Date()
  endOfChunk = 0
  let idxOfChunk = 0
  recordedChunks.length = 0

  recorder = new MediaRecorder(myVideoStream);

  recorder.ondataavailable = (e) => {
    if (e.data.size > 0) {
      recordedChunks.push(e.data);
      idxOfChunk++;
    }
    sendToServer(e.data, idxOfChunk);
  }
  recorder.start(3000);
}

const sendToServer = async function(chunk, idx) {
  try {
    // FormData를 생성하고 녹화된 데이터를 추가
    const formData = new FormData();
    formData.append('chunk', chunk);

    const jsonData = {
      filename: file.value.filename,
      chunkNumber: idx,
      endOfChunk: endOfChunk,
    }
    formData.append('jsonData', new Blob([JSON.stringify(jsonData)], {
      type: "application/json",
    }))
    // axios를 사용하여 POST 요청을 서버로 보냄
    const response = await fileServer.uploadVideo(formData);
    //console.log(`Chunk ${idx}`, response);
    if (response.status === 200) {
      console.log('upload success', response.data);
    }
  } catch (error) {
    console.error('Error sending chunk to server:', error);
  }
}

const stopRecording = function () {
  endOfChunk = 1;
  recorder.stop();
  recordedChunks.length = 0

  const videoLength = Math.floor((new Date() - file.value.time) / 1000)

  const req_body = {
    title: websocketStore.now.question.commonQuestion,
    videoLength: videoLength,
    videoUrl: {
      saveFilename: `${file.value.filename}.mkv`,
      originFilename: `${file.value.filename}.mkv`,
    },
    thumbnailUrl: {
      saveFilename: `${file.value.filename}.png`,
      originFilename: `${file.value.filename}.png`,
    },
  }
  uploadData.value.push(req_body)
}

const saveRecording = async function () {

  try {
    await websocketStore.stomp.send(`/server/answer/${websocketStore.roomData.sessionId}`, headers, JSON.stringify({
      type: 'SAVE',
      index: websocketStore.roomData.index,
      videos: uploadData.value,
    }))
    loading.value = true
  } catch (error) {
    console.log(error)
  }
  leaveSession()
  router.push({ name: "video-list-interview", params:{roomType:'multi'} })
}

const cancelRecording = async function () {
  loading.value = true
  try {
    for (let i = 0; i < uploadData.value.length; i++){
      await fileServer.cancelUpload(uploadData.value[i].filename)
    }
    dialog.value = false
  } catch (error) {
    console.log(error)
  }
  websocketStore.stomp.disconnect()

  deleteInterviewRoom({
    interviewRoomIdList: [websocketStore.roomData.roomId]
  })
  leaveSession()
  router.push({name: 'interview'})
}
// 순서 재배치
const reArrangeById = (arr, idOrder) => {
  // 주어진 ID 배열의 순서대로 배열을 재배치하는 함수
  return idOrder.map(id => arr.find(item => item.id === id));
};

const changePosition = function (orders) {
  subscribers.value = reArrangeById(subscribers.value, orders)
}

// 웹소켓 통신
const sendMessage = async function (type) {
  await websocketStore.stomp.send(`/server/answer/${websocketStore.roomData.sessionId}`, headers, JSON.stringify({
    type: type,
    index: websocketStore.roomData.index,
  }))
}

const receive = async function (message) {
  let trollIdx = -1
  const result = JSON.parse(message.body);
  websocketStore.message = result;

  switch (result.type) {
    case 'ENTER':
      websocketStore.now.orders = result.orders;
      websocketStore.now.question.commonQuestion = result.question.commonQuestion;
      websocketStore.flag.interviewer = !websocketStore.flag.interviewer;
      await interviewStore.TTS(interviewStore.script.enter)
      
      setTimeout(() => {
        changePosition(result.orders)
        setTimeout(() => {
          sendMessage('START')
        }, 3000)
      }, 3000)
      break;

    case 'START':
      websocketStore.flag.interviewer = !websocketStore.flag.interviewer;
      await interviewStore.TTS(websocketStore.now.question.commonQuestion)
      break;

    case 'PROCEEDING':
      websocketStore.flag.interviewer = !websocketStore.flag.interviewer;
      websocketStore.now.turn = result.number
      break;

    case 'TIMEOUT':
      websocketStore.flag.interviewer = !websocketStore.flag.interviewer;
      websocketStore.now.turn = result.number
      break;

    case 'FINISH':
      websocketStore.flag.interviewer = !websocketStore.flag.interviewer;
      websocketStore.now.turn += 1
      websocketStore.now.question.commonQuestion = result.question.commonQuestion;

      websocketStore.now.orders = result.orders;
      await interviewStore.TTS(interviewStore.script.finish)
      
      setTimeout(() => {
        changePosition(result.orders);
        websocketStore.now.turn = -1
        setTimeout(() => {
          sendMessage('START')
        }, 3000)
      }, 3000)
      break;

    case 'SAVED':
      if (result.index === websocketStore.roomData.index) {
        console.log('saved!', message.body)
        loading.value = false
        dialog.value = false
        websocketStore.stomp.disconnect()
      }
      break;

    case 'END':
      dialog.value = true
      websocketStore.flag.interviewer = !websocketStore.flag.interviewer;
      break;

    case 'CHECK':
      break;

    case 'LEAVE':
      trollIdx = subscribers.value.findIndex(item => item.id === result.idx)
      if (trollIdx !== -1) {
        subscribers.value.splice(trollIdx , 1) 
      }
      break;

    default:
      break;
  }
};

onMounted(() => {
  joinSession()

  websocketStore.stomp.unsubscribe()

  websocketStore.stomp.subscribe(`/client/answer/${websocketStore.roomData.sessionId}`, function (message) {
    receive(message)
  }, headers) // 면접장 용
  websocketStore.stomp.send(`/server/answer/${websocketStore.roomData.sessionId}`, headers,
    JSON.stringify({
      type: 'ENTER',
      index: websocketStore.roomData.index,
    })
  )

  checkId = setInterval(() => {
    sendMessage('CHECK')
  }, 30000)
})

onBeforeUnmount(() => {
  leaveSession()
  clearInterval(checkId)
})

watch(interviewStore.mediaToggle ,
  () => {
    if (publisher.value) {
      publisher.value.publishVideo(interviewStore.mediaToggle.video)
      publisher.value.publishAudio(interviewStore.mediaToggle.audio)
    }
  }
)

// 녹화
watch(() => websocketStore.flag.record,
  (newVal) => {
    if (newVal) {
      startRecording()
    }
    else {
      stopRecording()
    }
  }
)
</script>

<template>
  <div class="w-100 h-100 d-flex align-center">
    <div id="video-container" class="w-100 h-100 d-flex align-center justify-space-around">
      <div v-for="(item, idx) in subscribers" :key="item.sub.stream.streamId" class="ma-2">
        <div class="w-100 bg-grey-lighten-1 text-center my-1" style="border-radius: 12px;">{{ idx+1 }}번 째 답변자</div>
        <ov-video
          :id="item.sub.stream.streamId"
          :stream-manager="item.sub"
          :muted="item.sub.id===websocketStore.roomData.index"
          :class="{'redbox' : idx === websocketStore.now.turn}"
        />
        <div class="d-flex align-center">
          <v-card v-if="idx === websocketStore.now.turn" class="pa-1" color="red-darken-1">답변 중</v-card>
          <v-card v-else-if="idx < websocketStore.now.turn" class="pa-1" color="light-green-lighten-1">답변 완료</v-card>
          <v-card v-else class="pa-1" color="light-blue-darken-1">답변 대기</v-card>
          <div class="mx-2">{{ JSON.parse(item.sub.stream.connection.data).clientData.name }}</div>
        </div>
        
      </div>
    </div>

  </div>

  <v-dialog v-model="dialog" width="auto" persistent>
    <v-card class="text-center px-10 py-3">
      <v-card-title><v-img :src="logo"></v-img></v-card-title>
      <v-divider class="border-opacity-100"></v-divider>
      <v-card-text>
        면접이 종료 되었습니다.<br>면접 영상을 저장 하시겠습니까?
      </v-card-text>
      <div class="d-flex justify-center">
        <v-card-actions>
          <v-btn color="purple-lighten-1" variant="flat" block @click="saveRecording" width="100" v-if="!loading">저장하기</v-btn>
          <v-btn color="purple-lighten-1" variant="flat" block @click="saveRecording" width="100" disabled v-else>저장중...</v-btn>
        </v-card-actions>
        <v-card-actions>
          <v-btn color="grey-lighten-1" variant="flat" block @click="cancelRecording" width="100">나가기</v-btn>
        </v-card-actions>
      </div>
    </v-card>
  </v-dialog>
</template>

<style scoped>
video {
  transform: rotateY(180deg);
  -webkit-transform: rotateY(180deg);
  /* Safari and Chrome */
  -moz-transform: rotateY(180deg);
  /* Firefox */
}
.redbox{
  border: 1px solid red;
}
</style>
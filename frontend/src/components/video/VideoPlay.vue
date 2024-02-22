<script setup>
import { useSelfSpeechStore } from '@/stores/selfSpeech';
import { useUserStore } from "@/stores/user";
import { onMounted, ref, watch } from "vue";
import { fileServer } from "@/api/video";
import { useRouter } from 'vue-router'
import { apiMethods } from "@/api/video";

const router = useRouter()

const selfSpeechStore = useSelfSpeechStore();
const userStore = useUserStore()

const goSelfSpeechMain = function () {
  router.push({name: 'selfspeech-main'})
}

const backToRecording = function() {
  selfSpeechStore.display = true;
  selfSpeechStore.listIdx = 1;
}

const urlRef = ref(null)
const mediaPlay = ref(false)
const isCompleted = ref(false)

const markVideo = async function (id, bool) {
  try {
    const req_body = {
      bookmark: !bool
    }
    await apiMethods.patchVideo(id, req_body)
  } catch (error) {
    console.log(error);
  }
  selfSpeechStore.questionData.videoInformationResponseList[selfSpeechStore.selectedVideo].bookmark=!selfSpeechStore.questionData.videoInformationResponseList[selfSpeechStore.selectedVideo].bookmark
}

const refreshVideo = async function () {
  try {
    const result = await apiMethods.getVideoAll(
      selfSpeechStore.selectedQuestion
    )
    selfSpeechStore.questionData = result.data
  } catch (error) {
    console.log(error)
  }
}

const getAllChunks = async function (filename) {
  isCompleted.value = false
  
  const chunkSize = 1024 * 1024; // 1MB 단위로 청크를 받음
  let start = 0;
  let end = chunkSize - 1;
  
  let chunks = [];
  
  while (!isCompleted.value) {
    try {
      const response = await fileServer.playVideo(filename, start, end)

      console.log(response)

      if (response.status === 206) {
        console.log(`Received chunk ${start}-${end}`);
        chunks.push(response.data);
        start = end + 1;
        end = start + chunkSize - 1;
      } else if (response.status === 200) {
        console.log('Received the last chunk');
        chunks.push(response.data);
        isCompleted.value = true;
        const blob = new Blob(chunks, { type: 'video/mp4' });
        const url = URL.createObjectURL(blob);

        urlRef.value = url
        // if (com === 0) renderVideo(url)
        // else reRenderVideo(url)
      }
    } catch (error) {
      console.error('호제야...', error);
      break;
    }
  }          
}

const playRecorded = function () {
  document.querySelector('#my-video').play()
}

const deleteVideo = async function (v_id) {
  try {
    await apiMethods.deleteVideos({
      videos : [v_id],
    })
  } catch (error) {
    console.log(error)
  }

  refreshVideo()
  backToRecording()
}

onMounted(async () => {
  try {
    await getAllChunks(selfSpeechStore.videoData.videoUrl.saveFilename, 0)
  } catch (error) {
    console.warn(error)
  }
}),

watch(() => selfSpeechStore.videoData,
  async (newValue, oldValue) => {
    try {
      await getAllChunks(newValue.videoUrl.saveFilename, 1)
    } catch (error) {
      console.warn(error)
    }
  }
)
</script>

<template>
  <div class="container h-100 d-flex flex-column justify-space-between">
    <div class="nav-bar d-flex align-center">
      <div class="ma-1">{{ selfSpeechStore.questionData.question }}</div>
      <v-icon class="exit-btn ma-1 ml-auto" color="black" size="32" icon="mdi-close-circle-outline" @click="goSelfSpeechMain"></v-icon>
    </div>

    <div class="pa-1">
      <div class="empty-player-container">
        <!-- <video 
          ref="videoPlayer" 
          class="video-js vjs-big-play-centered"
          id="my-video"
          data-setup='{}'
          controls
        ></video> -->
        <video 
          id="my-video" 
          :src="urlRef" 
          controls="true"
          style="max-width: 100%; min-width: 100px;"
        ></video>
        <v-btn v-show="!mediaPlay" class="play-button" @click="playRecorded(), mediaPlay=!mediaPlay" icon="mdi-play"></v-btn>
      </div>
    </div>

    <div class="btn-container w-100 d-flex align-center">
      <v-btn class="mx-3 my-5" 
        @click="markVideo(selfSpeechStore.videoData.videoId,selfSpeechStore.videoData.bookmark), selfSpeechStore.videoData.bookmark=!selfSpeechStore.videoData.bookmark"
        :disabled="!selfSpeechStore.selectedVideo^selfSpeechStore.selectedVideo===0"
      >
        <v-icon color="purple" size="32" icon="mdi-bookmark-outline" v-show="!selfSpeechStore.videoData.bookmark"></v-icon>
        <v-icon color="purple" size="32" icon="mdi-bookmark-check" v-show="selfSpeechStore.videoData.bookmark"></v-icon>
        <div v-show="!selfSpeechStore.videoData.bookmark">북마크 추가</div>
        <div v-show="selfSpeechStore.videoData.bookmark">북마크 해제</div>
      </v-btn>
      <v-btn class="mx-3 my-5" 
        @click="deleteVideo(selfSpeechStore.videoData.videoId)" 
        :disabled="!selfSpeechStore.selectedVideo^selfSpeechStore.selectedVideo===0"
      >
        <v-icon color="black" size="32" icon="mdi-trash-can-outline"></v-icon>
        <div>삭제</div>
      </v-btn>
      <v-btn class="mx-3 my-5 ml-auto bg-red" @click="backToRecording" variant="outlined">녹화하러가기</v-btn>
    </div>
  </div>
</template>

<style scoped>
.nav-bar{
  border: 1px solid black;
  background-color: #f0f0f0;
}
.container{
  background-color: black;
}
.empty-player-container {
  width: 100%;
  height: 390px;
  background-color: black;
  position: relative;
}
#my-video{
  width: 640px;
  height: 390px;
  background-color: black;
  display: block;
  margin: 0 auto;
}
.play-button{
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>

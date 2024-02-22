<script setup>
import { ref, watch } from "vue";
import mainImg from '@/assets/interview/interviewMainIcon.png'
import { useInterviewStore, useWebsocketStore } from "@/stores/interview";
import { useUserStore } from "@/stores/user"
import interviewMultiHelpModal from "@/components/interview/interviewMultiHelpModal.vue"
import TimerComponent from '@/components/interview/Timer2.vue'

const userStore = useUserStore()
const interviewStore = useInterviewStore()
const websocketStore = useWebsocketStore()

const btnActive = ref(false)
const isActiveTimer = ref(false)
const needResetTimer = ref(false)
const logMessages = ref([])
const headers = {
    Authorization: userStore.accessToken
}

const addLog = function (text) {
  // if (logMessages.value.length > 2) {
  //   logMessages.value.shift()
  // }
  logMessages.value.unshift({
    message: text,
    isClosed: false,
  })
  setTimeout(() => {
    // 변경하고자 하는 로그의 인덱스를 찾습니다.
    const index = logMessages.value.findIndex(log => log.message === text);
    if (index !== -1) {
      // 해당 로그의 isClosed 값을 변경합니다.
      closeLog(index)
    }
  }, 5000);
}

const closeLog = function (idx) {
  logMessages.value[idx].isClosed = true
  setTimeout(() => {
    logMessages.value.splice(idx, 1)
  }, 1000)
}

const controlMedia = function (com) {
  if (com === 0) {
    interviewStore.mediaToggle.video = !interviewStore.mediaToggle.video
  }
  else if (com === 1) {
    interviewStore.mediaToggle.audio = !interviewStore.mediaToggle.audio
  }
}

const openHelp = function () {
  interviewStore.dialog.help = true
}

const finishAnswer = async function () {
  websocketStore.flag.record = false
  interviewStore.mediaToggle.audio = false
  await sendMessage('PROCEEDING')
}
const timeOut = async function () {

  if (websocketStore.myTurn) {
    websocketStore.flag.record = false
    interviewStore.mediaToggle.audio = false
    await sendMessage('TIMEOUT')
  }
}

const goTimer = function () {
  isActiveTimer.value = true
  btnActive.value = true
}

const sendMessage = async function (type) {
  
  await websocketStore.stomp.send(`/server/answer/${websocketStore.roomData.sessionId}`, headers, JSON.stringify({
    type: type,
    index: websocketStore.roomData.index,
  }))
}

watch(() => websocketStore.flag.interviewer, async () => {
  switch (websocketStore.message.type) {
    case 'ENTER':
      addLog("다인 모의 면접에 입장하셨습니다.")
      break;

    case 'START':
      if (websocketStore.now.question.id !== 0) {
        addLog(`${websocketStore.now.question.id}번 질문 시작!`)
      }
      else {
        addLog(`1분 자기 소개 시작!`)
      }
      websocketStore.now.turn = 0
      if (websocketStore.myTurn) {
        interviewStore.mediaToggle.audio = true
        addLog("당신의 차례입니다.")
        websocketStore.flag.record = true
      }
      else {
        interviewStore.mediaToggle.audio = false
      }
      await interviewStore.TTS(interviewStore.script.start)
      setTimeout(goTimer, 2000)
      break;

    case 'PROCEEDING':
      isActiveTimer.value = false
      btnActive.value = false
      addLog(`${websocketStore.now.turn}번 째 참가자 답변 완료`)
      if (websocketStore.myTurn) {
        interviewStore.mediaToggle.audio = true
        addLog("당신의 차례입니다.")
        websocketStore.flag.record = true
      }
      else {
        interviewStore.mediaToggle.audio = false
      }
      //await interviewStore.TTS(interviewStore.script.proceeding)
      setTimeout(goTimer, 2000)
      break;

    case 'TIMEOUT':
      isActiveTimer.value = false
      btnActive.value = false
      addLog(`${websocketStore.now.turn}번 째 참가자 시간 초과!`)
      if (websocketStore.myTurn) {
        interviewStore.mediaToggle.audio = true
        addLog("당신의 차례입니다.")
        websocketStore.flag.record = true
      }
      else {
        interviewStore.mediaToggle.audio = false
      }
      //await interviewStore.TTS(interviewStore.script.proceeding)
      setTimeout(goTimer, 2000)
      break;

    case 'FINISH':
      isActiveTimer.value = false
      btnActive.value = false
      addLog(`${websocketStore.now.turn}번 째 참가자 답변 완료`)
      if (websocketStore.now.question.id !== 0) {
        addLog(`${websocketStore.now.question.id}번 질문 종료...`)
      }
      else {
        addLog(`1분 자기 소개 종료`)
      }
      websocketStore.now.question.id += 1;
      break;

    case 'END':
      isActiveTimer.value = false
      btnActive.value = false
      websocketStore.now.turn = -1
      addLog("수고 하셨습니다")
      break;
  }}
)

</script>

<template>
  <div class="w-100 h-100 d-flex justify-space-between align-center">
    
    <div class="h-100" style="width: 30%;">
      
      <div class="log w-100 d-flex justify-space-between pa-2 ma-2" 
        style="border: 1px solid white; border-radius: 12px;" 
        v-for="(log, idx) in logMessages" :key="idx"
        :class="{'closedLog': log.isClosed}"
      >
        <v-icon icon="mdi-alert-outline" color="#FF8911"></v-icon>
        <div class="w-100 mx-1">
          <div>{{ log.message }}</div>
        </div>
        <v-icon  @click="closeLog(idx)" icon="mdi-close"></v-icon>
      </div>

    </div>

    <div class="h-100 d-flex align-center justify-space-between" style="width: 60%;">
      <div class="d-flex justify-center align-center">
        <div class="">
          <v-img :src="mainImg" width="200" @click="console.log(websocketStore.myTurn)"></v-img>
        </div>
      </div>

      <div class="d-flex flex-column align-center my-auto offset-1 v-col-3 py-0 px-0">
        <TimerComponent 
          :start-timer="isActiveTimer" 
          :reset-timer="needResetTimer"
          @finish-timer="timeOut"
          style="width: 150px; height: 150px;" 
        />
        <v-btn 
          v-if="websocketStore.myTurn"
          @click="finishAnswer" 
          :disabled="!btnActive" 
          rounded="xl" 
          size="x-large" 
          class="active-btn mt-4 mx-2 px-15"
        >답변 완료</v-btn>
      </div>

      <!-- <div>
        <div>
          현재 턴 : {{ websocketStore.now.turn }}
        </div>
        <div>
          현재 발화자 : {{ websocketStore.now.orders[websocketStore.now.turn] }}
        </div>
        <div>
          내 번호 : {{ websocketStore.roomData.index }}
        </div>
      </div> -->

      <div class="btn-container d-flex flex-column h-100">
        <v-btn class="ma-3" @click="controlMedia(0)" v-if="interviewStore.mediaToggle.video" icon="mdi-video" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="controlMedia(0)" v-else icon="mdi-video-off" color="red-darken-1"></v-btn>
        <v-btn class="ma-3" @click="controlMedia(1)" v-if="interviewStore.mediaToggle.audio" icon="mdi-microphone" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="controlMedia(1)" v-else icon="mdi-microphone-off" color="red-darken-1"></v-btn>
        <v-btn class="ma-3" @click="openHelp" icon="mdi-help" color="grey-lighten-1"></v-btn>
      </div>
    </div>

  </div>

  <interviewMultiHelpModal />
</template>

<style scoped>
.log{
  transition: opacity 1s;
}
.closedLog{
  opacity: 0;
}
</style>
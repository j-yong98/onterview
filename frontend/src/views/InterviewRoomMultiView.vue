<script setup>
import {ref, watch} from 'vue'
import interviewMultiInterviewer from "@/components/interview/interviewMultiInterviewer.vue";
import interviewMultiOV from "@/components/interview/interviewMultiOV.vue";
import { useRouter } from "vue-router";
import { useInterviewStore, useWebsocketStore } from "@/stores/interview";

const interviewStore = useInterviewStore()
const websocketStore = useWebsocketStore()
const router = useRouter()

// 1인 면접 진행 시간 스톱 워치
const startTime = ref(Date.now())
const timeDifference = ref(0)
const timerId = ref(null)

const updateTime = function () {
  timeDifference.value = Math.floor((Date.now() - startTime.value) / 1000)
}

// 스톱워치 실행
startTime.value = Date.now()
timerId.value = setInterval(updateTime, 1000)

const formatTime = function (seconds) {
  // 초를 'mm:ss' 형식의 문자열로 변환
  const minutes = Math.floor(seconds / 60)
  const remainingSeconds = seconds % 60
  return `${minutes < 10 ? '0' + minutes : minutes}:${remainingSeconds < 10 ? '0' + remainingSeconds : remainingSeconds}`
}

const goInterviewMain = function () {
  interviewStore.ov = false
  router.push({name: 'interview'})
}

interviewStore.ov = true

watch(() => websocketStore.flag.room,
  () => {
    console.log("room event!")
  }
)
</script>

<template>
<div class="main-container w-screen h-screen d-flex flex-column bg-black">

  <v-layout style="height: 50px;">
    <v-system-bar 
      class="nav-bar d-flex justify-space-between pa-0"
      height="50" 
      window
      color="black" 
      style="border-bottom: 1px solid white;"
    >
      <div class="d-flex align-center justify-center" style="border: 0px">
        <div style="font-size: 12px;">{{ interviewStore.choice.type }}</div>
      </div>

      <div class="d-flex align-center" style="font-size: 12px;">
        <v-icon icon="mdi-radiobox-marked" class="mx-1" color="red"></v-icon>
        {{ formatTime(timeDifference) }}
      </div>

      <div class="d-flex align-center px-3 w-75" style="font-size: large;" >
        Q. {{ websocketStore.now.question.commonQuestion }}
      </div>

      <button @click="goInterviewMain" class="d-flex justify-center align-center mx-1">
        <v-icon icon="mdi-close"></v-icon>
        <div>나가기</div>
      </button>

    </v-system-bar>
  </v-layout>

  <div class="interviewer-container w-100 h-50">
    <interviewMultiInterviewer />
  </div>

  <div class="w-100 h-50">
    <interviewMultiOV />
  </div>

</div>
</template>

<style scoped>
.nav-bar>*{
  border-left: 1px solid white;
  height: 100%;
  padding: 8px;
}
.interviewer-container{
  border-bottom: 1px solid white;
}
</style>
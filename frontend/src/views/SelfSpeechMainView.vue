<script setup>
import testImg from '@/assets/selfSpeech/SelfSpeechMainIcon.png'
import { useRouter } from 'vue-router'

const router = useRouter()

const goSelfSpeechRoom = function () {
  router.push({ name: 'selfspeech-room' })
}
const goStorage = function () {
  router.push({ name: 'storage-question' })
}
</script>

<template>
  <div class="container pa-10" style="background-color: #eed8e7">
    <div class="mt-10 pa-10 d-flex justify-center align-center">
      <div class="ma-10 mr-15">
        <v-img :src="testImg" :width="200"></v-img>
      </div>
      <div class="text-center">
        <div class="pa-5" style="font-size: 48px">1문 1답<br />셀프 스피치</div>
        <div class="pa-5">
          면접 문항과 답변을 등록하고 녹화해서<br />나의 답변 내용, 시간, 발음, 표정을 점검해보세요
        </div>
        <div class="pa-5">
          <v-btn variant="outlined" class="ma-3" @click="goSelfSpeechRoom"> 시작하기 </v-btn>
          <v-btn variant="outlined" class="ma-3" @click="goStorage"> 면접 문항 관리 </v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
html,
body {
  width: 100%;
  height: 100%;
}

.container {
  width: 100%;
  height: 100%;
  position: fixed;
}
</style>

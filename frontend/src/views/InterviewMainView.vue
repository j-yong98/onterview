<script setup>
import { ref } from "vue"
import mainImg from '@/assets/interview/interviewMainIcon.png'
import BtnImg1 from "@/assets/interview/interviewMainBtnIcon1.png"
import BtnImg2 from "@/assets/interview/interviewMainBtnIcon2.png"
import BtnImg4 from "@/assets/interview/interviewMainBtnIcon4.png"
import frontEndImg from "@/assets/interview/front-end.png"
import backEndImg from "@/assets/interview/back-end.png"
import { useRouter } from "vue-router"
import { useInterviewStore } from "@/stores/interview"
import { useUserStore } from "@/stores/user"
import interviewMultiHelpModal from "@/components/interview/interviewMultiHelpModal.vue"
import interviewMultiWait from "@/components/interview/interviewMultiWait.vue"

const router = useRouter()
const interviewStore = useInterviewStore()
const userStore = useUserStore()

const steps = ref(["면접 인원 선택", "면접 유형 선택", "시작하기"])
const peopleCount = ref(interviewStore.choice.people === 'MULTI')  // 1인 - 다인
const type = ref('')  // 면접 유형

const choosePeople = function (val) {
  peopleCount.value = val
  interviewStore.choice.people = peopleCount.value ? 'MULTI' : 'SINGLE'
}

const chooseType = function (val, detail) {
  type.value = val
  if (val) {
    interviewStore.choice.type = '직무면접'
    interviewStore.choice.typeDetail = detail
  } else {
    interviewStore.choice.type = '인성면접'
    interviewStore.choice.typeDetail = ''
  }
}

const enter = function () {

  if (userStore.accessToken === null) {
    alert('로그인이 필요합니다!')
    router.push({ name: 'login' })
    return;
  }

  if (interviewStore.choice.people === 'MULTI') {
    interviewStore.dialog.wait = true
  }
  else {
    router.push('/interview/single')
  }
}

const openHelp = function () {
  interviewStore.dialog.help = true
}

</script>

<template>
  <div class="pa-3 d-flex justify-center align-center">
    <div class="mr-3" style="font-size: 24px;">
      모의 면접관과 <br> 함께하는 <br> 모의 면접 <br> 시작하기
    </div>
    <div class="ml-3">
      <v-img :src="mainImg" width="200"></v-img>
    </div>
  </div>

  <div>
    <div class="item-header d-flex justify-space-between">
      <div class="step" v-for="(step, n) in steps" :key="n">
        step {{ n + 1 }} - {{ step }}
      </div>
    </div>

    <div class="item-wrapper d-flex justify-space-between">

      <div class="item">
        <div class="item-body">
          <div class="fit">
            <button class="btn d-flex justify-center align-center ma-2 my-4" :class="{ isSelect: !peopleCount }"
              @click="choosePeople(false)">
              <h3 class="mx-5">1인</h3>
              <div class="w-25 ma-2"><v-img :src="BtnImg1"></v-img></div>
            </button>

            <button class="btn d-flex justify-center align-center ma-2 my-4" :class="{ isSelect: peopleCount }"
              @click="choosePeople(true)">
              <h3 class="mx-5">4인</h3>
              <div class="w-25 ma-2"><v-img :src="BtnImg2"></v-img></div>
            </button>
          </div>
        </div>
      </div>

      <div class="item">
        <div class="item-body">
          <div class="fit d-flex justify-center align-center">
            <button class="btn d-flex justify-center align-center ma-2"
              :class="{ isSelect: interviewStore.choice.typeDetail === '' }" @click="chooseType(false, '')">
              <h3 class="mx-5">인성면접</h3>
              <div class="w-25 ma-2"><v-img :src="BtnImg4"></v-img></div>
            </button>
          </div>
          <div class="job d-flex justify-center align-center ma-2">
            <button class="btn sub-btn d-flex justify-center align-center flex-column mx-2"
              :class="{ isSelect: interviewStore.choice.typeDetail === 'FRONTEND' }"
              @click="chooseType(true, 'FRONTEND')">
              <h3 class="mx-5">프론트엔드</h3>
              <div class="w-25 ma-2"><v-img :src="frontEndImg"></v-img></div>
            </button>
            <button class="btn sub-btn d-flex justify-center align-center flex-column mx-2"
              :class="{ isSelect: interviewStore.choice.typeDetail === 'BACKEND' }" @click="chooseType(true, 'BACKEND')">
              <h3 class="mx-5">백엔드</h3>
              <div class="w-25 ma-1"><v-img :src="backEndImg"></v-img></div>
            </button>
          </div>
        </div>
      </div>

      <div class="item">
        <div class="item-body enter-body">
          <button @click="enter" class="btn enter">
            <div class="text-h3" style="font-weight: bold;">입장하기</div>
            <br>
            <div>환경세팅 및 매칭 대기가 시작됩니다.</div>
          </button>
          <v-btn class="help-btn" icon="mdi-help" @click="openHelp"></v-btn>
        </div>
      </div>
    </div>
  </div>

  <!-- 매칭 대기 모달 창 -->
  <interviewMultiWait />

  <!-- 도움말 모달 창 -->
  <interviewMultiHelpModal />
</template>

<style scoped>
.item {
  width: 100%;
  margin: 0 0.1em;
}

.item-header {
  font-weight: bold;
}

.step {
  background-color: #C4BEDE;
  text-align: center;
  width: 100%;
  margin: 0.1em;
  padding: 0.3em 0;
}

.item-body {
  background-color: #E0DCF2;
  height: 60vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.btn {
  width: 50vh;
  border: 0;
  font-weight: bold;
  cursor: pointer;
  background-color: #fff;
  border-radius: 6px;
  height: 25vh;
  /* height: 220px; */
}

.sub-btn {
  width: 24vh;
}

/* .fit {
  width: 100%;
}

.job {
  width: 90%;
} */

.enter {
  background-color: #B3ABD6;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 3px, rgba(0, 0, 0, 0.24) 0px 1px 2px;
}

.enter-body {
  position: relative;
}

.help-btn {
  position: absolute;
  bottom: 5%;
  right: 5%;
}

.isSelect {
  border: 0.2em solid #8A439C;
}
</style>
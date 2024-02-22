<script setup>
import { ref, onBeforeUnmount } from 'vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech';
import VideoThumbnail from "@/components/video/VideoThumbnail.vue";
import { apiMethods } from "@/api/video";
import { patchUpdateMyQuestionAnswer } from "@/api/question";

const selfSpeechStore = useSelfSpeechStore();
const maxCounter = ref(1500);

const items = ref([
  {
    title: '나의 답변',
    id: 1,
  },
  {
    title: '영상 보관함',
    id: 2,
  },
  {
    title: '자가 진단',
    id: 3,
  },
])

const switchTab = function (page) {
  selfSpeechStore.listIdx = page;
}
const rules = ref([
  v => {
    if (v === undefined || v === null) return true;
    return v.length <= maxCounter.value || `${maxCounter.value}글자 이하로 작성해주세요`
  }
])

const saveFeedback = async function () {
  if (selfSpeechStore.videoData.feedback.length > maxCounter.value) return
  try {
    await apiMethods.patchVideo(selfSpeechStore.videoData.videoId, {
      feedback: selfSpeechStore.videoData.feedback,
    })
    console.log("save feedback succesfully!")
  } catch (error) {
    console.log(error)
  }
}

const saveScript = async function () {
  if (selfSpeechStore.questionData.answer.length > maxCounter.value) return
  try {
    await patchUpdateMyQuestionAnswer(selfSpeechStore.questionData.myQuestionId ,{
      answer: selfSpeechStore.questionData.answer,
    })
    console.log("save answer succesfully!")
  } catch (error) {
    console.log(error)
  }
}

onBeforeUnmount(() => {
  selfSpeechStore.selectedQuestion = -1;
  selfSpeechStore.listIdx = 1;
})
</script>

<template>
  <div class="container d-flex align-center justify-center">
    <!-- 전환 탭 -->
    <v-card class="text-center" min-width="120" max-height="120" variant="text">
      <template v-for="item in items" :key="item.id">
        <v-list-item @click="switchTab(item.id)" v-if="item.id === 3 && selfSpeechStore.display" disabled>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
        <v-list-item @click="switchTab(item.id)" v-if="item.id === 2 && selfSpeechStore.selectedQuestion === -1" disabled>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
        <v-list-item @click="switchTab(item.id)" v-else>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </template>
    </v-card>

    <!-- 답변 스크립트 -->
    <div class="content-container w-100 h-100">
      <div v-if="selfSpeechStore.listIdx === 1">
        <v-container fluid>
          <v-textarea counter="200" :counter-max="maxCounter" label="답변" :rules="rules"
            v-model="selfSpeechStore.questionData.answer" no-resize :disabled="selfSpeechStore.selectedQuestion===-1" @blur="saveScript">
            {{ selfSpeechStore.questionData.answer }}
          </v-textarea>
        </v-container>
      </div>

      <!-- 썸네일 -->
      <div v-else-if="selfSpeechStore.listIdx === 2" class="h-100">
        <v-container fluid>
          <VideoThumbnail />
        </v-container>
      </div>

      <!-- 자가 진단 -->
      <div v-else-if="selfSpeechStore.listIdx === 3">
        <v-container fluid>
          <v-textarea counter="200" :counter-max="maxCounter" label="자가진단" :rules="rules"
            v-model="selfSpeechStore.videoData.feedback" no-resize @blur="saveFeedback">
            {{ selfSpeechStore.videoData.feedback }}
          </v-textarea>
        </v-container>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  background-color: #EAEAEA;
}
</style>
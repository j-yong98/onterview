<script setup>
import SelfSpeechRecord from '@/components/selfSpeech/SelfSpeechRecord.vue';
import SelfSpeechTab from '@/components/selfSpeech/SelfSpeechTab.vue';
import VideoPlay from '@/components/video/VideoPlay.vue';
import SelfSpeechQuestion from "@/components/selfSpeech/SelfSpeechQuestion.vue";
import { useSelfSpeechStore } from '@/stores/selfSpeech';

const selfSpeechStore = useSelfSpeechStore();

</script>

<template>
  <div class="container d-flex">

    <v-row no-gutters>
      <v-col cols="3">
        <SelfSpeechQuestion />
      </v-col>
      <v-col class="d-flex flex-column">
        <div v-if="selfSpeechStore.display" class="flex-grow-1">
          <SelfSpeechRecord />
        </div>
        <div v-else class="flex-grow-1">
          <VideoPlay />
        </div>
        <div class="flex-grow-1">
          <SelfSpeechTab />
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<style scoped>
html,
body {
  width: 100%;
  height: 100%;
}

.container {
  /* border: 1px solid black; */
  width: 100%;
  height: 100%;
  overflow: hidden;
  /* 브라우저의 기본 스크롤 바를 숨김 */
}
</style>
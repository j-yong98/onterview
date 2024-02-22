<script setup>
import helpImg1 from "@/assets/interview/interviewMultiHelp1.png"
import helpImg2 from "@/assets/interview/interviewMultiHelp2.png"
import helpImg3 from "@/assets/interview/interviewMultiHelp3.png"
import helpImg4 from "@/assets/interview/interviewMultiHelp4.png"
import helpImg5 from "@/assets/interview/interviewMultiHelp5.png"
import { ref } from "vue";

const helpText = ref([
  {
    id: 1,
    text: "5개의 면접 질문이 출제됩니다. (최대 20분 소요)",
    img: helpImg1,
  },
  {
    id: 2,
    text: "면접 문항마다 답변 순서가 랜덤으로 지정됩니다.",
    img: helpImg2,
  },
  {
    id: 3,
    text: "본인 차례에 답변 후 [답변 완료] 버튼을 누르세요.",
    img: helpImg3,
  },
  {
    id: 4,
    text: "60초 이내에 답변을 완료하지 못한 경우 자동으로 다음 차례로 넘어갑니다.",
    img: helpImg4,
  },
  {
    id: 5,
    text: "면접 종료 시, 나의 면접 영상을 저장할 수 있습니다.",
    img: helpImg5,
  },
])

const tab = ref(null)
</script>

<template>
  <div class="w-100 h-100 d-flex">
    <!-- <div class="w-50 h-100 d-flex justify-center align-center ma-3">
      
    </div> -->
    <div class="w-75 h-100">
      <v-window v-model="tab" class="w-100 h-100">
        <v-window-item v-for="n in helpText.length" :key="n" :value="n" class="my-auto">
          <v-img :src="helpText[n - 1].img" max-height="480px"></v-img>
        </v-window-item>
      </v-window>
    </div>

    <v-divider vertical inset class="border-opacity-100 ma-3"></v-divider>

    <div class="d-flex justify-center align-center w-auto">
      <v-tabs v-model="tab" color="deep-purple-accent-4" align-tabs="" mandatory direction="vertical">
        <v-tab v-for="item in helpText" :key="item.id" :value="item.id">{{ item.id }}. {{ item.text }}</v-tab>
      </v-tabs>
    </div>
  </div>
</template>

<style scoped></style>
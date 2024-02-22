import { ref } from 'vue'
import { defineStore } from 'pinia'


export const useSelfSpeechStore = defineStore('selfSpeech', () => {
  const display = ref(true);
  const listIdx = ref(1);
  const selectedQuestion = ref(-1);
  const seletedVideo = ref(null)
  const questionData = ref({
    answser: "",
    myQuestionId: undefined,
    question: "질문을 선택하세요",
    videoInformationResponseList: [],
  });
  const videoData = ref({
    feedback: "자가진단진단",
    bookmark: false,
    videoUrl: {
      saveFilename: "",
    }
  });

  return { display,listIdx,selectedQuestion,seletedVideo,questionData,videoData }
})

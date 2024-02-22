<script setup>
import draggable from 'vuedraggable'
import { ref, onMounted } from 'vue'
import { apiMethods } from '@/api/video'

// store
import { storeToRefs } from 'pinia'
import { useQuestionStore } from '@/stores/question.js'
import { useSelfSpeechStore } from '@/stores/selfSpeech'
import StorageQuestionView from '@/views/StorageQuestionView.vue'

const questionStore = useQuestionStore()
const selfSpeechStore = useSelfSpeechStore()
const { myQuestionList } = storeToRefs(questionStore)

const dialog = ref(false)

onMounted(() => {
  questionStore.requestMyQuestionList()
})

const selectQuestion = async function (ele) {
  selfSpeechStore.selectedQuestion = ele.myQuestionId
  try {
    const result = await apiMethods.getVideoAll(
      selfSpeechStore.selectedQuestion
    )
    selfSpeechStore.questionData = result.data
  } catch (error) {
    console.log(error)
  }
}
</script>

<template>
  <div class="question-title pa-3 d-flex align-center justify-space-between">
    <div>나의 면접 문항 목록</div>
    <v-btn color="grey-lighten-1" @click="dialog = true">관리</v-btn>
  </div>
  <div style="max-height: 80%; overflow-y: auto">
    <v-expansion-panels variant="accordion">
      <!-- 폴더가 없는 경우 -->
      <template v-if="myQuestionList.length == 0">
        <div class="text-grey" style="margin-top: 40vh">
          관리 버튼을 눌러 나의 면접 문항을 추가해주세요
        </div>
      </template>
      <!-- 폴더가 있는 경우 -->
      <template v-else>
        <v-expansion-panel
          v-for="folder in myQuestionList"
          :key="folder.myQuestionFolderId"
          :title="folder.myQuestionFolder"
          :value="folder.myQuestionFolderId"
        >
          <draggable
            :list="folder.myQuestionList"
            :group="{ name: 'question', pull: 'clone', put: false }"
            item-key="myQuestionId"
            :disabled="true"
          >
            <template #item="{ element }">
              <v-expansion-panel-text
                class="my-question"
                @click="selectQuestion(element)"
              >
                {{ element.question }}
              </v-expansion-panel-text>
            </template>
          </draggable>
        </v-expansion-panel>
      </template>
    </v-expansion-panels>
  </div>

  <v-dialog v-model="dialog">
    <v-card class="bg-purple-lighten-4 pa-5">
      <v-card-title class="d-flex flex-row-reverse">
        <v-icon
          class="exit-btn ma-1 ml-auto"
          color="black"
          size="32"
          icon="mdi-close-circle-outline"
          @click="dialog = false"
        ></v-icon>
      </v-card-title>
      <v-card-text>
        <div class="w-100 h-100">
          <StorageQuestionView />
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<style scoped>
.question-title {
  background-color: #7d797f;
  color: white;
}
.my-question {
  cursor: pointer;
  font-size: 12px;
}
</style>

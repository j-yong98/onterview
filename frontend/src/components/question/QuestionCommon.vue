<script setup>
import draggable from 'vuedraggable'
import { ref, onMounted } from 'vue'
import folderImage from '@/assets/question/folderImage.svg'

// store
import { storeToRefs } from 'pinia'
import { useQuestionStore } from '@/stores/question.js'
const questionStore = useQuestionStore()
const { commonQuestionList } = storeToRefs(questionStore)

onMounted(() => {
  questionStore.requestCommonQuestionList()
})

const cloneList = function (data) {
  const questionObject = {
    commonQuestionId: data.commonQuestionId,
    commonQuestion: data.commonQuestion
  }
  return questionObject
}

const log = function () {
  questionStore.requestCommonQuestionList()
}

const search = ref('')
// @click:append-inner="onClick"
</script>

<template>
  <div class="question-title pa-3">빈출 면접 문항 목록</div>
  <div class="intro pa-3 d-flex mb-1">
    <v-icon class="mr-2">mdi-gesture-tap</v-icon>
    <span style="font-size: 0.9rem"
      >Drag & Drop해서 My 질문에 추가해보세요!</span
    >
  </div>
  <!-- <v-text-field
    v-model="search"
    label="검색어를 입력해주세요"
    append-inner-icon="mdi-magnify"
    single-line
    variant="solo"
    density="compact"
    hide-details
  ></v-text-field> -->
  <div class="mt-1" style="max-height: 82%; overflow-y: auto">
    <v-expansion-panels
      variant="accordion"
      v-for="folder in commonQuestionList"
      :key="folder.commonQuestionFolderId"
      color="grey-lighten-2"
    >
      <v-expansion-panel>
        <v-expansion-panel-title color="grey-lighten-2">
          <v-col cols="auto">
            <v-img width="20" :src="folderImage"></v-img>
          </v-col>
          {{ folder.commonQuestionFolder }}
        </v-expansion-panel-title>
        <draggable
          :list="folder.commonQuestionList"
          :group="{ name: 'question', pull: 'clone', put: false }"
          :clone="cloneList"
          item-key="common_question_id"
          @change="log"
        >
          <template #item="{ element }">
            <v-expansion-panel-text>{{
              element.commonQuestion
            }}</v-expansion-panel-text>
          </template>
        </draggable>
      </v-expansion-panel>
    </v-expansion-panels>
  </div>
</template>

<style scoped>
.question-title {
  background-color: #7d797f;
  color: white;
}

.v-expansion-panel-title {
  padding: 0px !important;
  padding-left: 10px !important;
  padding-right: 10px !important;
}

.v-expansion-panel-text {
  box-shadow: 1px 1px 0px rgba(0, 0, 0, 0.2);
}

.intro {
  color: #925050;
  padding-left: 0;
  padding-right: 0;
  border: 1px solid #925050;
}
</style>

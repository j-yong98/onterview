<script setup>
import { ref } from 'vue'
import deleteButton from '@/assets/question/deleteButton.svg'
import { deleteDeleteMyQuestion, deleteDeleteMyQuestionFolder } from '@/api/question'

// store
import { useQuestionStore } from '@/stores/question.js'
const questionStore = useQuestionStore()

const props = defineProps({
  content: String,
  myQuestionFolderId: Number,
  myQuestionFolder: String,
  myQuestion: Object
})

const requestDeleteMyQuestionFolder = async function () {
  try {
    const response = await deleteDeleteMyQuestionFolder(props.myQuestionFolderId)
    console.log('response delete my question folder', response)

    questionStore.requestMyQuestionList()
    dialog.value = false
  } catch (error) {
    console.log('error delete my question folder', error)
  }
}

const requestDeleteMyQuestion = async function () {
  try {
    const response = await deleteDeleteMyQuestion(props.myQuestion.myQuestionId)
    console.log('reponse delete my question', response)

    questionStore.requestMyQuestionList()
    dialog.value = false
  } catch (error) {
    console.log('error delete my question', error)
  }
}

const dialog = ref(false)
</script>

<template>
  <v-dialog v-model="dialog" width="auto">
    <template v-slot:activator="{ props }">
      <v-img @click.stop v-bind="props" width="30" :src="deleteButton"></v-img>
    </template>
    <v-card class="px-6 pt-3" width="800">
      <v-card-text v-if="content == '폴더'">
        '{{ myQuestionFolder }}' 폴더에 있는 모든 질문도 함께 삭제됩니다. 정말로 삭제하시겠습니까?
      </v-card-text>
      <v-card-text v-if="content == '파일'">
        '{{ myQuestion.question }}' 질문을 삭제하시겠습니까?
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn variant="text" color="grey-darken-1" @click="dialog = false">취소</v-btn>
        <v-btn
          variant="text"
          color="red-accent-4"
          @click="content === '폴더' ? requestDeleteMyQuestionFolder() : requestDeleteMyQuestion()"
          >삭제</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped></style>

<script setup>
import { ref } from 'vue'
import { postCreateMyQuestionFolder, postCreateMyQuestion } from '@/api/question'
import createButton from '@/assets/question/createButton.svg'

// store
import { useQuestionStore } from '@/stores/question'
const questionStore = useQuestionStore()

const props = defineProps({
  content: String,
  myQuestionFolderId: Number
})

const requestCreateMyQuestionFolder = async function () {
  try {
    const payload = {
      myQuestionFolder: valueTextField.value
    }

    const response = await postCreateMyQuestionFolder(payload)
    console.log('response create my question folder', response)

    questionStore.requestMyQuestionList()
    dialog.value = false
    valueTextField.value = ''
  } catch (error) {
    console.log('error create my question folder', error)
  }
}

const requestCreateMyQuestion = async function () {
  try {
    const payload = {
      myQuestionFolderId: props.myQuestionFolderId,
      question: valueTextField.value
    }

    const response = await postCreateMyQuestion(payload)
    console.log('response create my question', response)

    questionStore.requestMyQuestionList()
    dialog.value = false
    valueTextField.value = ''
  } catch (error) {
    console.log('error create my question', error)
  }
}

const dialog = ref(false)
const valueTextField = ref('')
</script>

<template>
  <div>
    <v-dialog v-model="dialog" width="auto">
      <template v-slot:activator="{ props }">
        <v-btn
          v-show="content === '폴더'"
          v-bind="props"
          class="ml-3"
          variant="elevated"
          color="blue-accent-2"
          >새 {{ content }}</v-btn
        >
        <v-img
          v-show="content === '질문'"
          @click.stop
          v-bind="props"
          width="30"
          :src="createButton"
        ></v-img>
      </template>
      <v-card class="px-6 pt-3" width="1000">
        <v-card-text>새 {{ content }} 추가</v-card-text>
        <v-text-field
          variant="solo"
          :placeholder="`추가할 ${content}명을 작성해주세요`"
          class="pb-6"
          v-model="valueTextField"
          @keyup.enter="
              content === '폴더' ? requestCreateMyQuestionFolder() : requestCreateMyQuestion()
            "
        ></v-text-field>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn variant="text" color="grey-darken-1" @click="dialog = false">취소</v-btn>
          <v-btn
            variant="text"
            color="deep-purple-accent-4"
            @click="
              content === '폴더' ? requestCreateMyQuestionFolder() : requestCreateMyQuestion()
            "
            >추가</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<style scoped></style>

<script setup>
import { ref } from 'vue'
import { patchUpdateMyPostContent } from '@/api/community'

const props = defineProps({
  articleId: String,
  content: String,
  writerNickname: String
})

const emit = defineEmits(['requestPostDetail'])

const requestUpdateMyPostContent = async function () {
  try {
    const payload = {
      content: valueTextField.value
    }

    const response = await patchUpdateMyPostContent(props.articleId, payload)
    console.log('response update my post content', response)

    emit('requestPostDetail')
  } catch (error) {
    alert('게시글 수정에 실패했습니다. 다시 시도해주세요.')
    valueTextField.value = props.content
  }

  dialog.value = false
}

const valueTextField = ref(props.content)
const dialog = ref(false)
</script>

<template>
  <v-dialog v-model="dialog" width="auto">
    <template v-slot:activator="{ props }">
      <v-btn v-bind="props" class="updatebutton" variant="plain">수정</v-btn>
    </template>
    <v-card class="px-6 pt-6" width="1000">
      <div class="writer-and-content">
        <div class="mb-3">
          <span>{{ props.writerNickname }}</span>
          <span class="text-grey">님의 고민</span>
        </div>
        <v-textarea
          variant="outlined"
          v-model="valueTextField"
          style="font-family: Pretendard-Regular"
          :no-resize="true"
        ></v-textarea>
      </div>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn variant="text" color="grey-darken-1" @click="dialog = false"
          >취소</v-btn
        >
        <v-btn
          variant="text"
          color="deep-purple-accent-4"
          @click="requestUpdateMyPostContent()"
          >수정</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped></style>

<script setup>
import { ref } from 'vue'
import { deleteDeleteMyComment } from '@/api/community'

const props = defineProps({
  // articleId: Number,
  comment: Object
})

const emit = defineEmits(['requestCommentDetail'])

const requestDeleteMyPost = async function () {
  try {
    const response = await deleteDeleteMyComment(props.comment.commentId)
    console.log('response delete my post', response)
    emit('requestCommentDetail')
  } catch (error) {
    alert('댓글을 삭제하지 못했습니다. 다시 시도해주세요. ')
  }
  dialog.value = false
}

const dialog = ref(false)
</script>

<template>
  <v-dialog v-model="dialog" width="auto">
    <template v-slot:activator="{ props }">
      <span
        v-bind="props"
        v-show="comment.isMyComment"
        class="delete mr-3 text-grey"
        >삭제</span
      >
    </template>
    <v-card class="px-6 pt-3" width="500">
      <v-card-text> 정말로 댓글을 삭제하시겠습니까? </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn variant="text" color="grey-darken-1" @click="dialog = false"
          >취소</v-btn
        >
        <v-btn
          variant="text"
          color="red-accent-4"
          @click="requestDeleteMyPost()"
          >삭제</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>
.delete:hover {
  color: #6a3692 !important;
  cursor: pointer;
}
</style>

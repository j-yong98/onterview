<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { deleteDeleteMyPost } from '@/api/community'

const router = useRouter()

const props = defineProps({
  articleId: String,
  title: String
})

const requestDeleteMyPost = async function () {
  try {
    const response = await deleteDeleteMyPost(props.articleId)
    console.log(response)
    alert('게시글이 정상적으로 삭제되었습니다. ')
    router.push({ name: 'community-list' })
  } catch (error) {
    console.error(error)
    alert('게시글을 삭제하지 못했습니다. 다시 시도해주세요. ')
  }
  dialog.value = false
}

const dialog = ref(false)
</script>

<template>
  <v-dialog v-model="dialog" width="auto">
    <template v-slot:activator="{ props }">
      <v-btn v-bind="props" class="deletebutton" color="red" variant="plain"
        >삭제</v-btn
      >
    </template>
    <v-card class="px-6 pt-3" width="800">
      <v-card-text>
        '{{ props.title }}' 게시글을 삭제합니다. 정말로 삭제하시겠습니까?
      </v-card-text>
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

<style scoped></style>

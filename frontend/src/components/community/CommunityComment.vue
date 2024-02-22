<script setup>
import { ref } from 'vue'
import CommunityModalDeleteComment from '@/components/community/modal/CommunityModalDeleteComment.vue'

const props = defineProps({
  isParent: Boolean,
  comment: Object
})

const emit = defineEmits(['requestCommentDetail'])

const parent = ref({
  backgroundColor: '#faf3ff'
})

const child = ref({
  backgroundColor: '#f0f0f0'
  // paddingLeft: '50px !important'
})
</script>

<template>
  <div>
    <v-row class="" :style="isParent ? parent : child">
      <v-col cols="1" v-if="!isParent" class="text-right">ㄴ</v-col>
      <v-col cols="5">
        <span>{{ comment.writerNickname }}</span>
        <span v-show="comment.isWriterComment" class="ml-1 text-purple"
          >(글쓴이)</span
        >
      </v-col>
      <v-col cols="1" v-if="isParent"></v-col>
      <v-col cols="6" class="text-right">
        <CommunityModalDeleteComment
          :comment="comment"
          @request-comment-detail="emit('requestCommentDetail')"
        />
        <span class="writtendate">{{ comment.writtenDate }}</span>
      </v-col>
    </v-row>

    <v-row class="pt-0" :style="isParent ? parent : child">
      <v-col cols="1" v-if="!isParent"></v-col>
      <v-col cols="11">
        <div
          v-html="comment.content.replaceAll('\n', '<br />')"
          style="font-family: Pretendard-Regular"
          class="mb-6"
        ></div>
      </v-col>
    </v-row>
  </div>
</template>

<style scoped>
div {
  font-size: 0.9rem;
}

.v-col {
  padding-bottom: 0 !important;
}
</style>

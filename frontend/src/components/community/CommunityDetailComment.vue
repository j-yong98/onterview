<script setup>
// lib
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

// api
import { getCommentDetail, postCreateMyComment } from '@/api/community'

// component
import CommunityComment from '@/components/community/CommunityComment.vue'

onMounted(() => {
  requestCommentDetail()
  const { commentCount } = comments.value
  childComment.value = new Array(commentCount).fill('')
})

const route = useRoute()
const articleId = route.params.articleId

const requestCommentDetail = async function () {
  try {
    const response = await getCommentDetail(articleId)
    comments.value = response.data
    // console.log('response comment detail', response)
  } catch (error) {
    alert('댓글 목록을 불러오지 못했습니다. ')
  }
}

const requestCreateParentComment = async function () {
  try {
    const payload = {
      articleId: articleId,
      parentCommentId: null,
      content: parentComment.value
    }
    await postCreateMyComment(payload)

    alert(`댓글이 정상적으로 등록되었습니다.`)
    requestCommentDetail()
  } catch (error) {
    alert(`댓글을 등록하지 못했습니다. 다시 시도해주세요.`)
  }
  parentComment.value = ''
}

const requestCreateChildComment = async function (commentId, idx) {
  try {
    const payload = {
      articleId: articleId,
      parentCommentId: commentId,
      content: childComment.value[idx]
    }
    await postCreateMyComment(payload)

    alert(`댓글이 정상적으로 등록되었습니다.`)
    requestCommentDetail()
  } catch (error) {
    alert(`댓글을 등록하지 못했습니다. 다시 시도해주세요.`)
  }
  childComment.value[idx] = ''
}

const comments = ref({})
const parentComment = ref('')
const childComment = ref([])
</script>

<template>
  <v-container>
    <div>
      <div class="mb-3">댓글 쓰기</div>

      <v-row>
        <v-col cols="11">
          <v-textarea
            placeholder="피드백 내용을 작성해주세요"
            :no-resize="true"
            variant="outlined"
            v-model="parentComment"
          >
          </v-textarea>
        </v-col>
        <v-col cols="1">
          <v-btn height="150" @click="requestCreateParentComment()">작성</v-btn>
        </v-col>
      </v-row>
    </div>

    <div>
      <div class="mb-6">댓글 {{ comments.commentCount }}</div>
      <div v-for="(comment, idx) in comments.comments" :key="idx">
        <CommunityComment
          :is-parent="true"
          :comment="comment.parentComment"
          @request-comment-detail="requestCommentDetail()"
        />
        <div
          v-for="(child, idxChild) in comment.childCommentList"
          :key="idxChild"
        >
          <CommunityComment
            :is-parent="false"
            :comment="child"
            @request-comment-detail="requestCommentDetail()"
          />
        </div>
        <v-row class="pt-3">
          <v-col cols="1" style="font-size: 0.9rem">대댓글 쓰기</v-col>
          <v-col cols="10">
            <v-textarea
              v-model="childComment[idx]"
              rows="2"
              :no-resize="true"
              variant="outlined"
            >
            </v-textarea>
          </v-col>
          <v-col cols="1">
            <v-btn
              @click="
                requestCreateChildComment(comment.parentComment.commentId, idx)
              "
              >작성</v-btn
            >
          </v-col>
        </v-row>
      </div>
    </div>
  </v-container>
</template>

<style scoped>
.parent-comment {
  background-color: #faf3ff;
}

.child-comment {
  background-color: #f0f0f0;
}
</style>

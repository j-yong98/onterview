<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { postCreateMyPost } from '@/api/community'
import CommunityStorageVideo from '@/components/community/CommunityStorageVideo.vue'

const router = useRouter()

const tab = ref(1)
const title = ref(null)
const content = ref('')
const selectVideoId = ref(null)
const selectRoomType = ref('')

const selectVideo = function (video, roomType) {
  console.log(video)
  title.value = video.question
  selectVideoId.value = video.videoId
  selectRoomType.value = roomType
}

const requestCreateMyPost = async function () {
  try {
    const payload = {
      videoId: selectVideoId.value,
      title: title.value,
      content: content.value,
      roomType: selectRoomType.value.toUpperCase()
    }
    const response = await postCreateMyPost(payload)
    goCommunityDetail(response.data.articleId)
  } catch (error) {
    console.error('게시글 등록 실패', error)
    alert(`게시글을 등록하지 못했습니다. 다시 시도해주세요. `)
    goCommunityList()
  }
}

// router
const goCommunityList = function () {
  router.push({ name: 'community-list' })
}

const goCommunityDetail = function (articleId) {
  router.push({
    name: 'community-detail',
    params: { articleId: articleId }
  })
}
</script>

<template>
  <v-container>
    <div class="text-grey">제목</div>
    <div class="title mb-3" v-if="title === null">영상을 선택해주세요</div>
    <div class="title mb-3" v-else>{{ title }}</div>

    <!-- Tab -->
    <v-tabs v-model="tab" color="deep-purple-accent-4">
      <v-tab :value="1">셀프 스피치</v-tab>
      <v-tab :value="2">1인 모의면접</v-tab>
      <v-tab :value="3">다인 모의면접</v-tab>
    </v-tabs>

    <v-window v-model="tab">
      <v-window-item :value="1">
        <CommunityStorageVideo
          room-type="self"
          :select-video-id="selectVideoId"
          @select-video="(video, roomType) => selectVideo(video, roomType)"
        />
      </v-window-item>
      <v-window-item :value="2">
        <CommunityStorageVideo
          room-type="single"
          :select-video-id="selectVideoId"
          @select-video="(video, roomType) => selectVideo(video, roomType)"
        />
      </v-window-item>
      <v-window-item :value="3">
        <CommunityStorageVideo
          room-type="multi"
          :select-video-id="selectVideoId"
          @select-video="(video, roomType) => selectVideo(video, roomType)"
        />
      </v-window-item>
    </v-window>

    <!-- 고민 내용 -->
    <label for="content" class="text-grey mt-3">고민 내용</label>
    <textarea
      name="content"
      rows="4"
      v-model="content"
      placeholder="피드백 받고 싶은 내용을 작성해주세요"
    ></textarea>

    <!-- 버튼 -->
    <div class="d-flex justify-center justify-space-evenly mt-2">
      <v-btn color="grey" variant="tonal" @click="goCommunityList()"
        >취소</v-btn
      >
      <v-btn
        color="#BB66FF"
        variant="tonal"
        @click="requestCreateMyPost()"
        :disabled="title == null || content == ''"
        >글쓰기</v-btn
      >
    </div>
  </v-container>
</template>

<style scoped>
label {
  display: block;
}

textarea,
.title {
  resize: none;
  padding: 10px;
  border: 5px solid #f0e8f6;
  width: 100%;
  outline-color: #f0e8f6;
}

textarea::placeholder {
  color: #9e9e9e;
}
</style>

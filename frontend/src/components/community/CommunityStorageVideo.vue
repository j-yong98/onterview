<script setup>
import { ref, onMounted } from 'vue'
import { apiMethods } from '@/api/video'
import selectButton from '@/assets/community/selectButton.svg'

const props = defineProps({
  roomType: String,
  selectVideoId: Number
})

onMounted(() => {
  requestStorageVideoList()
})

const requestStorageVideoList = async function () {
  try {
    const response = await apiMethods.getUserVideoAll(props.roomType)
    videoList.value = response.data
  } catch (error) {
    console.error('영상 목록 조회 실패', error)
  }
}

const videoList = ref([])

const emit = defineEmits(['selectVideo'])

const selectVideo = function (video) {
  emit('selectVideo', video, props.roomType)
}
</script>

<template>
  <div class="layout px-3 py-2 d-flex flex-wrap">
    <div cols="auto" v-for="video in videoList" :key="video.videoId">
      <v-card class="mx-3 my-2" width="200" @click="selectVideo(video)">
        <v-img
          class="thumbnail"
          :src="`${video.thumbnailUrl.saveFilename}`"
          height="105px"
          cover
        >
          <v-img
            v-if="props.selectVideoId == video.videoId"
            :src="selectButton"
          ></v-img>
        </v-img>
        <v-card-subtitle class="pa-1">
          {{ video.question }}
        </v-card-subtitle>
        <v-tooltip activator="parent" location="top" open-delay="300">{{
          video.title
        }}</v-tooltip>
      </v-card>
    </div>
  </div>
</template>

<style scoped>
.layout {
  background-color: #f0e8f6;
  height: 42vh;
  max-height: 100%;
  overflow-y: auto;
}

.thumbnail {
  padding-left: 85%;
}
</style>

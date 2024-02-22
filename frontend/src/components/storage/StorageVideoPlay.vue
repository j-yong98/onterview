<script setup>
import { useStorageStore } from '@/stores/storage'
import { useUserStore } from '@/stores/user'
import { onBeforeUnmount, ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { fileServer } from '@/api/video'
import { apiMethods } from '@/api/video'
import editImage from '@/assets/question/editImage.png'

const storageStore = useStorageStore()
const userStore = useUserStore()

const route = useRoute()
const videoId = route.params.videoId

onMounted(async () => {
  await requestVideo(videoId)
  await requestAllChunks()
})

onBeforeUnmount(() => {
  if (player) {
    player.dispose()
  }
})

let player
const videoPlayer = ref(undefined)
const maxCounter = ref(20)

const rules = ref([
  (v) => {
    if (v === undefined) return true
    return (
      v.length <= maxCounter.value ||
      `${maxCounter.value}글자 이하로 작성해주세요`
    )
  }
])

// video
const isCompleted = ref(false)
const urlRef = ref(null)

const getAllChunks = async function (filename) {
  isCompleted.value = false

  const chunkSize = 1024 * 1024 // 1MB 단위로 청크를 받음
  let start = 0
  let end = chunkSize - 1

  let chunks = []

  while (!isCompleted.value) {
    try {
      const response = await fileServer.playVideo(filename, start, end)

      console.log(response)

      if (response.status === 206) {
        console.log(`Received chunk ${start}-${end}`)
        chunks.push(response.data)
        start = end + 1
        end = start + chunkSize - 1
      } else if (response.status === 200) {
        console.log('Received the last chunk')
        chunks.push(response.data)
        isCompleted.value = true
        const blob = new Blob(chunks, { type: 'video/mp4' })
        const url = URL.createObjectURL(blob)

        urlRef.value = url
        // if (com === 0) renderVideo(url)
        // else reRenderVideo(url)
      }
    } catch (error) {
      console.error('호제야...', error)
      break
    }
  }
}

const requestVideo = async function (videoId) {
  try {
    const res = await apiMethods.getVideo(videoId)
    storageStore.videoData = res.data
  } catch (error) {
    console.log(error)
  }
}

const requestAllChunks = async function () {
  try {
    await getAllChunks(storageStore.videoData.videoUrl.saveFilename, 0)
    console.log(storageStore.videoData.videoUrl.saveFilename)
  } catch (error) {
    console.warn(error)
  }
}

// patch
const isEditTitle = ref(false)
const editableTitle = ref('')

const saveFeedback = async function () {
  try {
    const result = await apiMethods.patchVideo(storageStore.videoData.videoId, {
      feedback: storageStore.videoData.feedback
    })
    console.log(result.data)
  } catch (error) {
    console.log(error)
  }
}

const enableEditVideoTitle = function () {
  isEditTitle.value = true
  editableTitle.value = storageStore.videoData.title
}

const requestUpdateVideoTitle = async function () {
  try {
    const result = await apiMethods.patchVideo(videoId, {
      title: editableTitle.value
    })
    console.log(result.data)
    requestVideo(videoId)
    storageStore.requestUserVideoAll('self')
  } catch (error) {
    console.log(error)
  }

  isEditTitle.value = false
}
</script>

<template>
  <v-container class="d-flex flex-column justify-center">
    <div>
      <v-btn @click="storageStore.goStorageVideoList()">목록 보기</v-btn>
    </div>
    <div class="d-flex justify-center align-center mt-3">
      <template v-if="isEditTitle">
        <v-text-field
          v-model="editableTitle"
          label="제목 수정"
          single-line
          variant="solo"
          density="compact"
          hide-details
          @blur="requestUpdateVideoTitle()"
          @keyup.enter="requestUpdateVideoTitle()"
        ></v-text-field>
      </template>
      <template v-else>
        <div>
          {{ storageStore.videoData.title }}
        </div>
        <div class="ml-2">
          <v-img
            :src="editImage"
            height="20"
            width="20"
            @click="enableEditVideoTitle()"
          ></v-img>
        </div>
      </template>
    </div>

    <v-row>
      <v-col cols="12">
        <div
          class="d-flex justify-center bg-white py-3 my-1 rounded-lg"
          style="border: 3px solid rgb(213, 213, 213)"
        >
          <video
            id="my-video"
            :src="urlRef"
            controls="true"
            style="max-width: 80%; min-width: 100px"
            class="rounded-lg"
          ></video>
        </div>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12" class="mb-10">
        <v-textarea
          label="자가진단"
          placeholder="나의 답변 영상을 보고 피드백을 남겨보세요"
          no-resize
          variant="outlined"
          v-model="storageStore.videoData.feedback"
          @blur="saveFeedback"
          style="background-color: white"
          rows="10"
        >
        </v-textarea>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.main-container {
  background-color: #efe6ef;
}
.empty-player-container {
  width: 100%;
  height: 360px;
  background-color: #f0f0f0;
  position: relative;
}
#my-video {
  /* width: 640px;
  height: 360px;
  background-color: #f0f0f0; */
}
:deep(.v-input__details) {
  display: none;
}
</style>

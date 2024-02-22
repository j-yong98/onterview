<script setup>
import { useStorageStore } from '@/stores/storage'
import { useUserStore } from '@/stores/user'
import { onBeforeUnmount, ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { fileServer } from '@/api/video'
import { apiMethods } from '@/api/video'

const storageStore = useStorageStore()
const userStore = useUserStore()

const route = useRoute()
const interviewRoomId = route.params.interviewRoomId

const interviewList = ref({})

onMounted(async () => {
  await requestInterviewDetail(interviewRoomId)
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

const requestInterviewDetail = async function () {
  try {
    const res = await apiMethods.getInterviewDetail(interviewRoomId)
    interviewList.value = res.data
    console.log('request video', interviewList.value)
  } catch (error) {
    console.log(error)
  }
}

const requestVideo = async function (video) {
  try {
    // selectVideo.value = video
    const res = await apiMethods.getVideo(video.videoInformation.videoId)
    selectVideo.value = res.data
    console.log(selectVideo.value, '선택')

    await getAllChunks(selectVideo.value.videoUrl.saveFilename, 0)
  } catch (error) {
    console.log(error)
  }
}

const requestAllChunks = async function () {
  try {
    await getAllChunks(interviewList.value.videoUrl.saveFilename, 0)
    console.log(interviewList.value.videoUrl.saveFilename)
  } catch (error) {
    console.warn(error)
  }
}

// patch
const isEditTitle = ref(false)
const editableTitle = ref('')
const selectVideo = ref({ videoId: null })

const saveFeedback = async function () {
  try {
    const result = await apiMethods.patchVideo(selectVideo.value.videoId, {
      feedback: selectVideo.value.feedback
    })
  } catch (error) {
    console.log(error)
  }
}

const enableEditVideoTitle = function () {
  isEditTitle.value = true
  editableTitle.value = interviewList.value.title
}

const requestUpdateVideoTitle = async function () {
  try {
    const result = await apiMethods.patchVideo(videoId, {
      title: editableTitle.value
    })
    console.log(result.data)
    requestVideo(videoId)
  } catch (error) {
    console.log(error)
  }

  isEditTitle.value = false
}
</script>

<template>
  <v-container>
    <v-row>
      <!-- 영상, 자가진단 -->
      <v-col cols="8">
        <div class="d-flex justify-space-between align-end">
          <h3>
            {{ interviewList.questionType }}
          </h3>
          <div>
            {{ interviewList.createAt }}
          </div>
        </div>
        <div class="d-flex flex-column justify-center">
          <div class="d-flex justify-center align-center"></div>
          <v-row>
            <v-col cols="12">
              <div
                class="d-flex justify-center bg-white py-3 my-1 rounded-lg"
                style="height: 55vh; border: 3px solid rgb(213, 213, 213)"
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
                @blur="saveFeedback"
                style="background-color: white"
                rows="5"
                v-model="selectVideo.feedback"
              >
              </v-textarea>
            </v-col>
          </v-row>
        </div>
      </v-col>

      <!-- 재생목록 -->
      <v-col cols="4">
        <div class="d-flex justify-end mb-2">
          <v-btn
            @click="
              storageStore.goStorageVideoListInterview(route.params.roomType)
            "
            density="comfortable"
            >목록 보기</v-btn
          >
        </div>
        <div
          class="rounded-lg"
          style="
            background-color: white;
            height: 80vh;
            border: 3px solid rgb(213, 213, 213);
          "
        >
          <h3 align="center" class="my-3">모의 면접 영상 목록</h3>

          <div style="height: 72vh; overflow-y: auto">
            <!-- 재생목록 -->
            <div
              v-for="video in interviewList.interviewQuestionList"
              :key="video.videoInformation.videoId"
            >
              <div
                class="playlist d-flex align-center pa-2"
                style="font-size: 0.9rem; color: gray"
                @click="requestVideo(video)"
              >
                <div
                  v-if="selectVideo.videoId === video.videoInformation.videoId"
                  style="width: 5%; font-size: 0.7rem"
                >
                  >
                </div>
                <div v-else style="width: 5%; font-size: 0.7rem"></div>
                <div style="width: 30%" class="mr-3">
                  <v-img
                    :src="video.videoInformation.thumbnailUrl.saveFilename"
                    class="rounded-lg"
                  ></v-img>
                </div>
                <div style="width: 65%">
                  {{ video.commonQuestion }}
                </div>
              </div>
            </div>
          </div>
        </div>
        <div></div>
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
.playlist:hover {
  background-color: rgb(225, 225, 225);
}
</style>

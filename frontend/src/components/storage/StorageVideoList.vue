<script setup>
import { ref, onMounted } from 'vue'
import { apiMethods } from '@/api/video'
import { useStorageStore } from '@/stores/storage'
import { storeToRefs } from 'pinia'

const storageStore = useStorageStore()
const { keyword, bookmark } = storeToRefs(storageStore)

const selectedId = ref([])
const isSelectedAll = ref(false)

onMounted(() => {
  storageStore.requestUserVideoAll('self')
})

const deleteVideo = async function () {
  try {
    const result = await apiMethods.deleteVideos({
      videos: selectedId.value
    })
    console.log(result.data)
    selectedId.value = []
    storageStore.requestUserVideoAll('self')
  } catch (error) {
    console.log(error)
  }
}

const markVideo = async function (id, bool) {
  try {
    const req_body = {
      bookmark: !bool
    }
    const result = await apiMethods.patchVideo(id, req_body)
    console.log(result.data)
    storageStore.requestUserVideoAll('self')
  } catch (error) {
    console.log(error)
  }
}

const selectAll = function () {
  if (selectedId.value.length === storageStore.storageData.length) {
    selectedId.value = []
  } else {
    for (const item of storageStore.storageData) {
      if (!selectedId.value.includes(item.videoId)) {
        selectedId.value.push(item.videoId)
        isSelectedAll.value = true
      }
    }
  }
}

const selectVideo = async function (v_id) {
  try {
    const res = await apiMethods.getVideo(v_id)
    storageStore.videoData = res.data
  } catch (error) {
    console.log(error)
  }
}

// const toggleBookmark = function () {
//   bookmark.value = 1
// }
</script>

<template>
  <!-- list (추후에 vuetify data tables 컴포넌트 변경?)-->
  <div class="d-flex flex-column justify-start w-screen h-screen">
    <v-container class="tool-bar d-flex align-center">
      <v-btn variant="tonal" class="mr-3" @click="selectAll"> 전체 선택 </v-btn>
      <v-btn variant="tonal" @click="deleteVideo"> 삭제 </v-btn>

      <div class="ml-auto d-flex align-center">
        <v-text-field
          v-model="keyword"
          label="검색어를 입력해주세요"
          append-inner-icon="mdi-magnify"
          single-line
          variant="solo"
          density="compact"
          hide-details
          class="mr-3"
          style="width: 300px"
          @keyup.enter="storageStore.requestUserVideoAll('self')"
          @blur="storageStore.requestUserVideoAll('self')"
        ></v-text-field>

        <v-btn
          v-if="bookmark == 1"
          @click="(bookmark = 0), storageStore.requestUserVideoAll('self')"
          variant="elevated"
          color="purple"
          ><v-icon size="x-large">mdi-bookmark-check</v-icon></v-btn
        >
        <v-btn
          v-else
          @click="(bookmark = 1), storageStore.requestUserVideoAll('self')"
          variant="elevated"
          color="purple"
          ><v-icon size="x-large">mdi-bookmark-outline</v-icon></v-btn
        >

        <v-btn
          class="ml-3"
          variant="elevated"
          color="purple"
          @click="storageStore.goStorageVideoGrid()"
        >
          그리드 보기
        </v-btn>
      </div>
    </v-container>
    <v-container class="bg-white overflow-auto" style="height: 77vh">
      <div class="pa-2">
        <v-table fixed-header height="">
          <thead>
            <tr>
              <th class="text-left">선택</th>
              <th class="text-left">번호</th>
              <th class="text-left">영상 제목</th>
              <th class="text-left">면접 질문</th>
              <th class="text-left">북마크</th>
            </tr>
          </thead>
          <tbody v-for="(dt, n) in storageStore.storageData" :key="n" hover>
            <tr
              class="list-item"
              @click="storageStore.goStorageVideoPlay(dt.videoId)"
            >
              <td>
                <v-checkbox
                  @click.stop
                  v-model="selectedId"
                  :value="dt.videoId"
                ></v-checkbox>
              </td>
              <td>{{ n + 1 }}</td>
              <td>
                {{ dt.title }}
              </td>
              <td>{{ dt.question }}</td>
              <td>
                <v-icon
                  v-show="!dt.bookmark"
                  color="purple"
                  size="32"
                  icon="mdi-bookmark-outline"
                  @click.stop="
                    markVideo(dt.videoId, dt.bookmark),
                      (dt.bookmark = !dt.bookmark)
                  "
                >
                </v-icon>
                <v-icon
                  v-show="dt.bookmark"
                  color="purple"
                  size="32"
                  icon="mdi-bookmark-check"
                  @click.stop="
                    markVideo(dt.videoId, dt.bookmark),
                      (dt.bookmark = !dt.bookmark)
                  "
                >
                </v-icon>
              </td>
            </tr>
          </tbody>
        </v-table>
      </div>
    </v-container>
  </div>
</template>

<style scoped>
/* .tool-bar > * {
  margin: 8px;
} */
.list-item {
  cursor: pointer;
}

.list-item:hover {
  background-color: rgb(242, 242, 242);
}

:deep(.v-input__details) {
  display: none;
}
</style>

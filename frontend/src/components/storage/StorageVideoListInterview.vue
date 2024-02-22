<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { apiMethods } from '@/api/video'
import { useStorageStore } from '@/stores/storage'

const storageStore = useStorageStore()

const selectedId = ref([])
const isSelectedAll = ref(false)
const route = useRoute()

const page = ref(1)

onMounted(async () => {
  await storageStore.requestInterviewList(route.params.roomType)
  page.value = storageStore.page + 1
})

watch(
  () => route.params.roomType,
  async (newValue, oldValue) => {
    await storageStore.requestInterviewList(newValue)
  }
)

watch(
  () => page.value,
  async (newValue, oldValue) => {
    // store의 page를 변경
    storageStore.page = newValue - 1
    await storageStore.requestInterviewList(route.params.roomType)
  }
)

const deleteInterview = async function () {
  try {
    const result = await apiMethods.deleteInterview({
      interviewRoomIdList: selectedId.value
    })
    console.log(result.data)
    selectedId.value = []
    await storageStore.requestInterviewList(route.params.roomType)
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
  } catch (error) {
    console.log(error)
  }
}

const selectAll = function () {
  if (selectedId.value.length === storageStore.interviewData.content.length) {
    selectedId.value = []
  } else {
    for (const item of storageStore.interviewData.content) {
      if (!selectedId.value.includes(item.interviewRoomId)) {
        selectedId.value.push(item.interviewRoomId)
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
</script>

<template>
  <!-- list (추후에 vuetify data tables 컴포넌트 변경?)-->
  <div class="d-flex flex-column justify-start w-screen h-screen">
    <v-container class="tool-bar d-flex align-center">
      <v-btn variant="tonal" class="mr-3" @click="selectAll"> 전체 선택 </v-btn>
      <v-btn variant="tonal" @click="deleteInterview"> 삭제 </v-btn>
    </v-container>
    <v-container class="bg-white overflow-auto">
      <div>
        <v-table fixed-header style="height: 76vh">
          <thead>
            <tr>
              <th class="text-left">선택</th>
              <th class="text-left">번호</th>
              <th class="text-left">유형</th>
              <th class="text-left">일시</th>
            </tr>
          </thead>
          <tbody
            v-for="(dt, n) in storageStore.interviewData.content"
            :key="n"
            hover
          >
            <tr
              class="list-item"
              @click="
                storageStore.goStorageVideoPlayInterview(
                  route.params.roomType,
                  dt.interviewRoomId
                )
              "
            >
              <td>
                <v-checkbox
                  @click.stop
                  v-model="selectedId"
                  :value="dt.interviewRoomId"
                ></v-checkbox>
              </td>
              <td>{{ n + 1 + storageStore.page * 8 }}</td>
              <td>
                {{ dt.questionType }}
              </td>

              <td>{{ dt.createAt }}</td>
            </tr>
          </tbody>
          <template v-slot:bottom>
            <div class="text-center pt-2">
              <v-pagination
                v-model="page"
                :length="storageStore.interviewData.totalPages"
                rounded="circle"
                prev-icon="mdi-menu-left"
                next-icon="mdi-menu-right"
                active-color="#BB66FF"
                density="comfortable"
              ></v-pagination>
            </div>
          </template>
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

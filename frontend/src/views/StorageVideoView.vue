<script setup>
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useStorageStore } from '@/stores/storage'

const storageStore = useStorageStore()
const router = useRouter()
const route = useRoute()

const roomType = ref('')

const goInterviewList = async function (rf) {
  roomType.value = rf

  router.push({
    name: 'video-list-interview',
    params: { roomType: rf }
  })
}

onMounted(() => {
  if (route.params.roomType == undefined) {
    roomType.value = 'self'
  } else {
    roomType.value = route.params.roomType
  }
})

watch(
  () => route.params.roomType,
  (newValue, oldValue) => {
    if (newValue == undefined) {
      roomType.value = 'self'
    } else {
      roomType.value = newValue
    }
  }
)
</script>

<template>
  <!-- 전체보기, 내가 쓴 게시글 보기 토글 -->
  <v-container>
    <v-row justify="end" align="center">
      <div
        class="test"
        :class="{
          active: roomType === 'self',
          inactive: roomType !== 'self'
        }"
        @click="(roomType = 'self'), router.push({ name: 'video-list' })"
      >
        셀프 스피치
      </div>
      <div style="color: rgb(190, 190, 190)">|</div>
      <div
        class="test"
        :class="{
          active: roomType === 'single',
          inactive: roomType !== 'single'
        }"
        @click="goInterviewList('single')"
      >
        1인 모의 면접
      </div>
      <div style="color: rgb(190, 190, 190)">|</div>
      <div
        class="test"
        :class="{
          active: roomType === 'multi',
          inactive: roomType !== 'multi'
        }"
        @click="goInterviewList('multi')"
      >
        다인 모의 면접
      </div>
    </v-row>
  </v-container>

  <div style="background-color: #efe6ef">
    <RouterView />
  </div>
</template>

<style scoped>
.active {
  font-size: 0.9rem;
  margin: auto 10px;
}

.inactive {
  font-size: 0.9rem;
  margin: auto 10px;
  color: grey;
}

.inactive:hover {
  color: black;
}

.test{
  cursor: pointer;
}
</style>

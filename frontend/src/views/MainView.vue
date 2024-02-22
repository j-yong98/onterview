<script setup>
import { useRouter } from 'vue-router'
import introduceImage1 from '@/assets/main/introduceImage1.png'
import introduceImage2 from '@/assets/main/introduceImage2.png'
import introduceImage3 from '@/assets/main/introduceImage3.png'
import introduceImage5 from '@/assets/main/introduceImage5.png'

const images = [
  introduceImage5,
  introduceImage2,
  introduceImage1,
  introduceImage3
]
const titles = ['ONTERVIEW', '셀프 스피치', '모의 면접', '커뮤니티']
const contents = [
  '당신의 든든한 면접 파트너, 온터뷰',
  '나를 찾아가는 여정. 나만의 스토리를 완성하다.',
  '실전 대비의 기적. 여러 목소리와 시선 속에서 함께 성장하다.',
  '함께 나아가는 힘. 다양한 시선으로 인사이트를 확장하다.'
]

const router = useRouter()
const goMain = function (i) {
  if (i == 1) {
    router.push({ name: 'selfspeech-main' })
  } else if (i == 2) {
    router.push({ name: 'interview' })
  } else if (i == 3) {
    router.push({ name: 'community' })
  }
}
</script>

<template>
  <div>
    <v-carousel
      cycle
      height="94vh"
      hide-delimiter-background
      show-arrows="hover"
    >
      <v-carousel-item v-for="(image, i) in images" :key="i" :src="image" cover>
        <v-sheet
          height="100%"
          style="
            background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5));
          "
          @click="goMain(i)"
        >
          <div
            class="d-flex fill-height flex-column justify-center"
            style="margin-left: 10%"
          >
            <div
              class="text-white"
              style="font-size: 3rem; font-family: 'Pretendard-Medium'"
            >
              {{ titles[i] }}
            </div>
            <div
              class="text-white mt-1"
              style="font-size: 1.3rem; font-family: 'Pretendard-SemiBold'"
            >
              {{ contents[i] }}
            </div>
          </div>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
  </div>
</template>

<style scoped></style>

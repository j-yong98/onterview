<script setup>
// import 'vue3-dropdown-navbar/preflight.css'
import {
  TheDropDownNavbar,
  TheDropDownMenu,
  TheDropDownItem,
  TheDropDownNavbarLogo
} from 'vue3-dropdown-navbar'
import { ref } from 'vue'

import logo from '@/assets/logo.png'
import profile from '@/assets/profile.png'
import { useUserStore } from '@/stores/user'

const dropdownMenu = ref(null)
const userStore = useUserStore()
</script>

<template>
  <TheDropDownNavbar>
    <template #logo>
      <TheDropDownNavbarLogo>
        <RouterLink to="/">
          <v-img
            class="logo"
            :width="150"
            aspect-ratio="16/9"
            cover
            :src="logo"
          ></v-img>
        </RouterLink>
      </TheDropDownNavbarLogo>
    </template>
    <TheDropDownItem link="/selfspeech"> 셀프 스피치 </TheDropDownItem>
    <TheDropDownItem link="/interview"> 모의 면접 </TheDropDownItem>
    <TheDropDownItem link="/community/list"> 커뮤니티 </TheDropDownItem>
    <TheDropDownMenu text="보관함" ref="dropdownMenu">
      <TheDropDownItem link="/storage/question">
        면접 문항 목록
      </TheDropDownItem>
      <TheDropDownItem link="/storage"> 녹화 영상 목록 </TheDropDownItem>
    </TheDropDownMenu>

    <RouterLink v-if="userStore.accessToken === null" to="/login">
      <v-btn variant="tonal" color="deep-purple-accent-4">로그인</v-btn>
    </RouterLink>
    <TheDropDownMenu v-else text="마이페이지" ref="dropdownMenu">
      <v-list>
        <v-list-item
          :prepend-avatar="profile"
          :title="userStore.nickname + ' 님'"
          subtitle="환영합니다!"
        >
        </v-list-item>
      </v-list>
      <TheDropDownItem link="/mypage">나의 정보 수정</TheDropDownItem>
      <TheDropDownItem @click="userStore.logout">로그아웃</TheDropDownItem>
    </TheDropDownMenu>

    <div class="mr-4"></div>
  </TheDropDownNavbar>
</template>

<style>
.logo {
  margin-left: 20%;
}

ul {
  list-style-type: none;
  /* 불릿 제거 */
  /* padding: 0; 일부 브라우저에서 기본적으로 적용된 패딩 제거 */
}

a {
  text-decoration: none;
  /* 링크 밑줄 제거 */
  color: black;
  /* 링크 색상, 필요에 따라 조정 */
}

.dd-nav-p-4 {
  padding: 0.5rem !important;
}
</style>

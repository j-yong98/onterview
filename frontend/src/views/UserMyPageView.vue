<script setup>
import { ref } from 'vue'
import UserUpdate from '@/components/user/UserUpdate.vue'
import UserUpdatePwd from '@/components/user/UserUpdatePwd.vue'
import UserDelete from '@/components/user/UserDelete.vue'

const tab = ref(1)
</script>

<template>
  <div class="body offset-2 v-col-8">

    <v-tabs class="mb-5" v-model="tab" color="deep-purple-accent-4">
      <v-tab :value="1">회원 정보</v-tab>
      <v-tab :value="2">비밀번호 변경</v-tab>
      <v-tab :value="3">회원 탈퇴</v-tab>
    </v-tabs>

    <v-window v-model="tab">
      <!-- 회원정보 수정 -->
      <v-window-item :value="1">
        <UserUpdate />
      </v-window-item>
      <!-- 비밀번호 변경 -->
      <v-window-item :value="2">
        <UserUpdatePwd />
      </v-window-item>
      <!-- 회원 탈퇴 -->
      <v-window-item :value="3">
        <UserDelete />
      </v-window-item>
    </v-window>
  </div>
</template>

<style scoped>
html {
  height: 100%;
}

.body {
  min-height: 100vh;
}

.profile {
  border: 1px solid #BB66FF;
  padding: 10px;
}
</style>

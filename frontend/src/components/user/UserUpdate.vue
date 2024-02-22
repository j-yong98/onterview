<script setup>
import { ref, watch } from 'vue'
import { useUserStore } from '@/stores/user.js'
import { patchUpdateUserNickname } from '@/api/user.js'

const userStore = useUserStore()
const formRef = ref(null)
const nickname = ref(userStore.nickname)
const updateBtnActivated = ref(true)
const dialog = ref(false)

const watchNickname = watch(nickname, () => {
  if (nickname.value != userStore.nickname.value) {
    updateBtnActivated.value = false
  } else {
    updateBtnActivated.value = true
  }
})

const nicknameRules = [
  (value) => {
    if (value) {
      return true
    } else {
      return '닉네임을 입력해주세요.'
    }
  },
  (value) => {
    const koreanRegex = /^[가-힣]{2,8}$/

    if (koreanRegex.test(value)) {
      return true
    } else {
      return '닉네임은 2~8자의 한글이어야 합니다.'
    }
  },
]

const requestUpdateUserNickname = function () {
  console.log(`회원정보 업데이트 요청`)
  const isValid = formRef.value.validate()

  isValid.then((response) => {
    if (response.valid) {
      const payload = {
        nickname: nickname.value
      }

      const success = function () {
        dialog.value = true
        updateBtnActivated.value = true
        userStore.nickname = nickname.value
      }

      const error = function (error) {
        alert(error)
        console.log(error)
      }

      patchUpdateUserNickname(payload, success, error)
    } else {
      alert('닉네임 입력 형식을 확인하세요.')
    }
  })
}
</script>

<template>
  <div class="profile px-5">
    <v-sheet width="90%" class="mx-auto my-10">
      <h3 class="mt-5">닉네임</h3>
      <h6 class="text-grey">한글 2~8자 이하</h6>
      <v-form ref="formRef" fast-fail @submit.prevent="requestUpdateUserNickname">
        <v-text-field label="닉네임" :rules="nicknameRules" v-model="nickname"></v-text-field>
        <div class="d-flex justify-center">
          <v-btn type="submit" class="d-flex justify-center mt-2 px-15" :disabled="updateBtnActivated" color="success">
            <h3>회원정보 수정</h3>
          </v-btn>
        </div>
      </v-form>
    </v-sheet>

    <v-dialog v-model="dialog" width="auto">
      <v-card class="pa-3 text-center text-success" title="✔ 성공!">
        <v-card-text class="mt-3 text-black">
          닉네임을 성공적으로 변경했습니다!
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="grey" variant="text" @click="dialog = false">
            닫기
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<style scoped></style>
<script setup>
import { ref } from 'vue'
import { patchChangeUserPwd } from '@/api/user.js'

const formRef = ref(null)
const original = ref(null)
const password = ref(null)
const confirm = ref(null)
const dialog = ref(false)

const originalRules = [
  (value) => {
    if (value) {
      return true
    } else {
      return '현재 비밀번호를 입력해주세요.'
    }
  },
]

const passwordRules = [
  (value) => {
    if (value) {
      return true
    } else {
      return '새 비밀번호를 입력해주세요.'
    }
  },
  (value) => {
    // 영문, 숫자, 특수문자 중 2가지 이상 포함
    const complexityRegex = /^(?=.*[a-zA-Z])(?=.*\d|(?=.*\W)).{8,32}$/

    if (complexityRegex.test(value)) {
      return true
    } else {
      return '비밀번호는 영문/숫자/특수문자 중 2가지 이상을 포함하여 8자 이상 32자 이하로 입력해주세요.'
    }
  },
]

const passwordConfirmRules = [
  (value) => {
    if (value) {
      return true
    } else {
      return '새 비밀번호 확인을 입력해주세요.'
    }
  },
  (value) => {
    // 영문, 숫자, 특수문자 중 2가지 이상 포함
    const complexityRegex = /^(?=.*[a-zA-Z])(?=.*\d|(?=.*\W)).{8,32}$/

    if (complexityRegex.test(value)) {
      return true
    } else {
      return '비밀번호는 영문/숫자/특수문자 중 2가지 이상을 포함하여 8자 이상 32자 이하로 입력해주세요.'
    }
  },
  (value) => {
    if (value === password.value) {
      return true
    } else {
      return '비밀번호가 일치하지 않습니다.'
    }
  }
]

const closeDialog = function () {
  dialog.value = false
}

const requestUserPwdChange = function () {

  const isValid = formRef.value.validate()

  isValid.then((response) => {
    if (response.valid) {
      const payload = {
        original: original.value,
        password: password.value,
        confirm: confirm.value
      }

      const success = function (response) {
        // 모달 - 비밀번호 변경을 완료했습니다.
        dialog.value = true

        // 입력되어 있는 데이터 지우기
        original.value = null
        password.value = null
        confirm.value = null
      }

      const error = function (error) {
        // 리턴되는 값에 따라 적용
        alert(error.response.data.errorMessage)

        // 입력되어 있는 데이터 지우기
        original.value = null
        password.value = null
        confirm.value = null
      }

      patchChangeUserPwd(payload, success, error)
    }
  })
}
</script>

<template>
  <div class="profile">
    <v-sheet width="90%" class="mx-auto my-10">
      <h3 class="mb-5">비밀번호 변경</h3>
      <v-form ref="formRef" fast-fail @submit.prevent="requestUserPwdChange">
        <v-text-field v-model="original" type="password" label="현재 비밀번호"></v-text-field>
        <v-text-field v-model="password" type="password" :rules="passwordRules" label="새 비밀번호"></v-text-field>
        <v-text-field v-model="confirm" type="password" :rules="passwordConfirmRules" label="새 비밀번호 확인"></v-text-field>
        <div class="d-flex justify-center">
          <v-btn type="submit" class="d-flex justify-center mt-2 px-15" color="success">
            <h3>비밀번호 변경</h3>
          </v-btn>
        </div>
      </v-form>
    </v-sheet>

    <v-dialog v-model="dialog" width="auto">
      <v-card class="text-center text-success" title="✔ 성공!">
        <v-card-text class="text-black mt-3">비밀번호 변경을 완료했습니다.</v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text="닫기" @click="closeDialog"></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<style scoped></style>
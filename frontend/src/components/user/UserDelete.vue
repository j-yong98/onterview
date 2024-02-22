<script setup>
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { deleteDeleteUser } from '@/api/user.js'
import { useUserStore } from "@/stores/user.js"

const router = useRouter()
const userStore = useUserStore()
const password = ref('')
const dialog = ref(false)
const deleteBtnActivated = ref(true)

const watchPassword = watch(password, () => {
  if (password.value !== '') {
    deleteBtnActivated.value = false
  } else {
    deleteBtnActivated.value = true
  }
})

const requestDeleteUser = function () {
  const payload = {
    password: password.value
  }

  const success = function () {
    alert('지금까지 onterview 서비스를 이용해주셔서 감사합니다.')
    userStore.logout()
    router.push({ name: "main" })
  }

  const error = function (error) {
    console.log(error)
    alert('비밀번호가 옳지 않습니다.')
    password.value = ''
    dialog.value = false
  }

  deleteDeleteUser(payload, success, error)
}
</script>

<template>
  <div class="profile">
    <v-sheet width="90%" class="mx-auto my-10">
      <h3 class="mb-5">회원 탈퇴</h3>
      <p>탈퇴 시 직접 작성한 콘텐츠(동영상, 답변, 자가 진단 등)가 자동으로 삭제됩니다.</p>
      <p>탈퇴 후 해당 데이터는 복구할 수 없습니다.</p>
      <p>그래도 탈퇴를 희망하시면, 아래에 비밀번호를 입력해주세요.</p>
      <v-form class="mt-5" ref="formRef" fast-fail @submit.prevent="requestDeleteUser">
        <v-text-field v-model="password" type="password" label="비밀번호"></v-text-field>
        <div class="d-flex justify-center">
          <v-dialog v-model="dialog" width="auto">
            <template v-slot:activator="{ props }">
              <v-btn color="red" class="mt-2 px-15" @click="dialog = true" :disabled="deleteBtnActivated" v-bind="props">
                회원 탈퇴
              </v-btn>
            </template>

            <v-card class="pa-3 text-center" title="정말 삭제하시나요?😢">
              <v-card-text class="mt-3">
                탈퇴 후 모든 데이터는 복구할 수 없어요.
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="grey" variant="text" @click="dialog = false">
                  취소
                </v-btn>
                <v-btn color="red" variant="text" @click="requestDeleteUser">
                  삭제
                </v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </div>
      </v-form>
    </v-sheet>
  </div>
</template>

<style scoped></style>
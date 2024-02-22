<script setup>
import { ref, watch, computed } from "vue"
import { useRouter } from "vue-router"
import { postSignUp, getIsDuplicatedEmail, getIsDuplicatedNickname } from '@/api/user.js'

const router = useRouter()
const email = ref('')
const nickname = ref('')
const password = ref('')
const confirm = ref('')
const formRef = ref(null)
const isDuplicatedEmail = ref(null)
const isDuplicatedNickname = ref(null)
const emailSuccess = ref(false)
const emailError = ref(false)
const nicknameSuccess = ref(false)
const nicknameError = ref(false)

const emailWatch = watch(email, () => {
  emailError.value = false
  emailSuccess.value = false
})

const nicknameWatch = watch(nickname, () => {
  nicknameError.value = false
})

const emailHintComputed = computed(() => {
  if (emailSuccess.value) {
    return '사용 가능한 이메일입니다!'
  } else if (emailError.value) {
    return '사용 중인 이메일입니다!'
  } else {
    return null
  }
})

const nicknameHintComputed = computed(() => {
  if (nicknameSuccess.value) {
    return '사용 가능한 닉네임입니다!'
  } else if (nicknameError.value) {
    return '사용 중인 닉네임입니다!'
  } else {
    return null
  }
})

const emailRules = [
  (value) => {
    if (value) {
      return true
    } else {
      return '이메일을 입력해주세요.'
    }
  },
  (value) => {
    if (/.+@.+\..+/.test(value)) {
      return true
    } else {
      return '이메일 형식이 올바르지 않습니다.'
    }
  },
]

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

const passwordRules = [
  (value) => {
    if (value) {
      return true
    } else {
      return '비밀번호를 입력해주세요.'
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

const passwordCheckRules = [
  (value) => {
    if (value) {
      return true
    } else {
      return '비밀번호 확인을 입력해주세요.'
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

const requestSignUp = function () {

  const isValid = formRef.value.validate()

  isValid.then((response) => {
    if (response.valid) {
      const payload = {
        email: email.value,
        nickname: nickname.value,
        password: password.value,
        confirm: confirm.value,
      }

      const success = function (response) {
        console.log(response)
        if (response.status === 201) {
          router.push({ name: "login" })
          return
        } else {
          console.log(response)
          alert(`알 수 없는 이유로 회원가입에 실패했습니다. \n관리자에게 문의해주세요.`)
        }
      }

      const error = function (error) {
        // 실패 사유에 따른 조건 분기
        const reason = error.response.data
        if (reason === "EMAIL_DUPLICATED") {
          emailError.value = true
          alert(`이미 존재하는 이메일 계정입니다!`)
        } else if (reason === "NICKNAME_DUPLICATED") {
          alert(`이미 존재하는 닉네임입니다!`)
        } else if (reason === "PASSWORD_CANNOT_CONFIRM") {
          alert(`비밀번호 확인이 일치하지 않습니다!`)
        } else {
          alert(`알 수 없는 이유로 회원가입에 실패했습니다. \n관리자에게 문의해주세요.`)
        }
      }

      postSignUp(payload, success, error)
    }
  })

}

const requestIsDuplicatedEmail = function () {

  for (const rule of emailRules) {
    const validationResult = rule(email.value)

    // 규칙을 만족하지 않으면 오류 메시지 출력 및 함수 종료
    if (validationResult !== true) {
      alert(validationResult)
      return
    }
  }

  const success = function (response) {
    if (response.data.isAvaliable) {
      emailSuccess.value = true
      alert(`사용 가능한 이메일입니다!`)
    } else {
      emailError.value = true
      alert(`이미 사용 중인 이메일입니다!`)
    }
    console.log(response)
  }

  const error = function (error) {
    alert(`알 수 없는 이유로 이메일 중복 조회를 실패했습니다. \n관리자에게 문의해주세요.`)
  }

  getIsDuplicatedEmail(email.value, success, error)
}

const requestIsDuplicatedNickname = function () {


  for (const rule of nicknameRules) {
    const validationResult = rule(nickname.value)

    // 규칙을 만족하지 않으면 오류 메시지 출력 및 함수 종료
    if (validationResult !== true) {
      alert(validationResult)
      return
    }
  }

  const success = function (response) {
    if (response.data.isAvaliable) {
      nicknameSuccess.value = true
      alert(`사용 가능한 닉네임입니다!`)
    } else {
      nicknameError.value = true
      alert(`이미 사용 중인 닉네임입니다!`)
    }
  }

  const error = function (error) {
    alert(`알 수 없는 이유로 닉네임 중복 조회를 실패했습니다. \n관리자에게 문의해주세요.`)
  }

  getIsDuplicatedNickname(nickname.value, success, error)
}


</script>


<template>
  <div class="offset-4 v-col-4 mt-10">
    <div class="rounded-0">
      <div class="text-center mb-5">
        <img class="mb-5" src="@/assets/logo.png" width="200px">
        <h2 class="mb-2">회원가입</h2>
      </div>
      <v-sheet width="90%" class="mx-auto">
        <v-form ref="formRef" fast-fail @submit.prevent="requestSignUp">

          <label for="email">이메일</label>
          <v-row justify="center">
            <v-col cols="9">
              <v-text-field v-model="email" :class="{ success: emailSuccess }" label="example@onterview.com"
                :rules="emailRules" :error="emailError" :hint="emailHintComputed" id="email"></v-text-field>
            </v-col>
            <v-col cols="3" class="mt-2">
              <v-btn @click="requestIsDuplicatedEmail">중복 확인</v-btn>
            </v-col>
          </v-row>

          <label for="nickname">닉네임</label>
          <v-row justify="center">
            <v-col cols="9">
              <v-text-field v-model="nickname" :class="{ success: nicknameSuccess }" label="한글 닉네임" :rules="nicknameRules"
                :error="nicknameError" :hint="nicknameHintComputed" id="nickname"></v-text-field>
            </v-col>
            <v-col cols="3" class="mt-2">
              <v-btn @click="requestIsDuplicatedNickname">중복 확인</v-btn>
            </v-col>
          </v-row>

          <label for="password">비밀번호</label>
          <v-text-field v-model="password" label="비밀번호" :rules="passwordRules" type="password"
            id="password"></v-text-field>

          <label for="confirm">비밀번호 확인</label>
          <v-text-field v-model="confirm" label="비밀번호 확인" :rules="passwordCheckRules" type="password"
            id="confirm"></v-text-field>


          <v-btn type="submit" block class="mt-5">가입하기</v-btn>
        </v-form>
      </v-sheet>
    </div>
  </div>
</template>

<style scoped>
.success {
  color: green;
}
</style>

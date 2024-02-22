import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { defineStore } from 'pinia'
import { postLogin } from '@/api/user'

export const useUserStore = defineStore('user', () => {
    const router = useRouter()
    const nickname = ref(null)
    const email = ref(null)
    const accessToken = ref(null)
    const refreshToken = ref(null)

    const logout = function () {
        nickname.value = null
        email.value = null
        accessToken.value = null
        refreshToken.value = null

        router.push({ name: 'main' })
    }

    const requestLogin = function (payload) {
        console.log(`로그인 요청`)
        
        const success = function (response) {
            console.log(`로그인 성공`)
            nickname.value = response.data.nickname
            email.value = response.data.email
            accessToken.value = response.data.accessToken
            refreshToken.value = response.data.refreshToken

            router.push({ name: "main" })
        }
      
        const error = function (response) {
            console.log(`로그인 실패`)
            alert(`이메일 또는 비밀번호를 확인해주세요.`)
        }
      
        postLogin(payload, success, error)
    }

  return { nickname, email, accessToken, refreshToken, logout, requestLogin }
}, { persist: true })

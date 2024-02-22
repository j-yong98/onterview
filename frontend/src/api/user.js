import { localAxios } from "@/api/index.js"
import { useUserStore } from "@/stores/user"

const api = localAxios()


const postSignUp = function (payload, success, error) {
    console.log("request post, sign up")
    api.post(`/api/user/signup`, payload).then(success).catch(error)
}

const postLogin = function (payload, success, error) {
    console.log("request post, login")
    api.post(`/api/user/login`, payload).then(success).catch(error)
}

const getIsDuplicatedEmail = function (email, success, error) {
    console.log("request get, is duplicated email")
    api.get(`/api/user/check/email?email=${email}`).then(success).catch(error)
}

const getIsDuplicatedNickname = function (nickname, success, error) {
    console.log("request get, is duplicated nickname")
    api.get(`/api/user/check/nickname?nickname=${nickname}`).then(success).catch(error)
}

const patchUpdateUserNickname = function (payload, success, error) {
    console.log("request patch, update user")
    const userStore = useUserStore()
    api.defaults.headers["Authorization"] = userStore.accessToken
    api.patch(`/api/user/nickname`, payload).then(success).catch(error)
}

const patchChangeUserPwd = function (payload, success, error) {
    console.log("request patch, change user password")
    const userStore = useUserStore()
    api.defaults.headers["Authorization"] = userStore.accessToken
    api.patch(`/api/user/password`, payload).then(success).catch(error)
}

const deleteDeleteUser = function (payload, success, error) {
    console.log("request delete, delete user")
    const userStore = useUserStore()
    api.defaults.headers["Authorization"] = userStore.accessToken
    // axios delete method는 데이터를 보낼 때 data: ... 형태로 감싸서 보내야 한다.
    const data = {
        data: payload
    }
    api.delete(`/api/user`, data).then(success).catch(error)
}

export { postSignUp, postLogin, getIsDuplicatedEmail, getIsDuplicatedNickname, patchUpdateUserNickname, patchChangeUserPwd, deleteDeleteUser }

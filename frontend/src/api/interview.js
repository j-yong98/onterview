import { localAxios } from "@/api/index.js"
import { useUserStore } from "@/stores/user"

const api = localAxios()
const userStore = useUserStore()


const postInterviewQuestions = function (payload, success, error) {
    console.log("request post, interview questions")
    api.defaults.headers["Authorization"] = userStore.accessToken
    api.post(`/api/interview-room`, payload).then(success).catch(error)
}
const deleteInterviewRoom = function (payload) {
  api.post(`/api/interview-room/delete`, payload)
}

export {
  postInterviewQuestions,
  deleteInterviewRoom,
}
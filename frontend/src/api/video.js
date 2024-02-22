import { localAxios } from "@/api/index.js"
import axios from 'axios'
import { useUserStore } from "@/stores/user";

const userStore = useUserStore()

const api = localAxios()
const api2 = axios.create({
    baseURL: 'https://i10a504.p.ssafy.io/',
    headers: {
        "Authorization": `${userStore.accessToken}`
    }
});



export const apiMethods = {
    getVideo: function (v_id) {
        api.defaults.headers.common['Authorization'] = `${userStore.accessToken}`
        return api.get(`/api/video/${v_id}`)
    },
    getVideoAll: function (q_id) {
        api.defaults.headers.common['Authorization'] = `${userStore.accessToken}`
        return api.get(`/api/my-question/${q_id}`)
    },
    getUserVideoAll: function (category) {
        api.defaults.headers.common['Authorization'] = `${userStore.accessToken}`
        return api.get(`/api/video?category=${category.toUpperCase()}`)
    },
    getSelfVideoList: function (category, keyword, bookmark) {
        console.log(userStore.accessToken)
        api.defaults.headers.common['Authorization'] = `${userStore.accessToken}`
        return api.get(`/api/video?category=${category.toUpperCase()}&keyword=${keyword}&bookmark=${bookmark}`)
    },
    deleteVideos: function (v_ids) {
        api.defaults.headers.common['Authorization'] = `${userStore.accessToken}`
        return api.post('/api/video/delete', v_ids)
    },
    patchVideo: function (v_id, req_body) {
        api.defaults.headers.common['Authorization'] = `${userStore.accessToken}`
        return api.patch(`/api/video/${v_id}`, req_body)
    },
    saveVideo: function (req_body) {
        api.defaults.headers.common['Authorization'] = `${userStore.accessToken}`
        return api.post(`/api/video`, req_body)
    },
    getInterviewList: function (roomType, page, size) {
        api.defaults.headers.common['Authorization'] = `${userStore.accessToken}`
        console.log(`/api/interview-room?roomType=${roomType.toUpperCase()}&page=${page}&size=${size}`)
        return api.get(`/api/interview-room?roomType=${roomType.toUpperCase()}&page=${page}&size=${size}`)
    },
    getInterviewDetail: function (interviewRoomId) {
        api.defaults.headers.common['Authorization'] = `${userStore.accessToken}`
        return api.get(`/api/interview-room/${interviewRoomId}`)
    },
    deleteInterview: function (payload) {
        api.defaults.headers.common['Authorization'] = `${userStore.accessToken}`
        return api.post(`/api/interview-room/delete`, payload)
    }
}

export const fileServer = {
    uploadVideo: function (formData) {
        api2.defaults.headers.common['Authorization'] = `${userStore.accessToken}`
        return api2.post(`/api-file/chunk/upload`, formData)
    },
    playVideo: function (filename, st, ed) {
        api2.defaults.headers.common['Authorization'] = `${userStore.accessToken}`
        return api2.get(`/api-file/chunk/stream/${filename}`, {
            responseType: 'arraybuffer',
            headers: {
                Range: `bytes=${st}-${ed}`,
            }
        });
    },
    cancelUpload: function (fileName) {
        api2.defaults.headers.common['Authorization'] = `${userStore.accessToken}`
        return api2.delete(`/api-file/chunk?fileName=${fileName}`)
    },
}

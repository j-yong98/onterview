import { localAxios } from '@/api/index'
import { useUserStore } from '@/stores/user'

const api = localAxios()
const userStore = useUserStore()

const getAllPostList = function (order, category, query) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.get(`/api/community?order=${order}&category=${category}&query=${query}`)
}

const getMyPostList = function (order, category, query) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.get(`/api/community/my?order=${order}&category=${category}&query=${query}`)
}

const getPostDetail = function (articleId) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.get(`/api/community/post/detail/${articleId}`)
}

const getCommentDetail = function (articleId) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.get(`/api/community/comment/detail/${articleId}`)
}

const postCreateMyPost = function (payload) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.post(`/api/community`, payload)
}

const deleteDeleteMyPost = function (articleId) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.delete(`/api/community/${articleId}`)
}

const patchUpdateMyPostContent = function (articleId, payload) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.patch(`/api/community/${articleId}`, payload)
}

const postCreateMyComment = function (payload) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.post(`/api/community/comment`, payload)
}

const deleteDeleteMyComment = function (commentId) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.delete(`/api/community/comment/${commentId}`)
}

const patchLikePost = function (articleId) {
    api.defaults.headers.common["Authorization"] = userStore.accessToken
    return api.patch(`/api/community/like/${articleId}`)
}


export {
    getAllPostList,
    getMyPostList,
    getPostDetail,
    getCommentDetail,
    postCreateMyPost,
    deleteDeleteMyPost,
    patchUpdateMyPostContent,
    postCreateMyComment,
    deleteDeleteMyComment,
    patchLikePost,
}
import { ref } from 'vue'
import { defineStore } from 'pinia'
import { getCommonQuestionList, getMyQuestionList } from "@/api/question"

export const useQuestionStore = defineStore('question', () => {
  const commonQuestionList = ref([]) 
  
  const myQuestionList = ref([])

  const requestCommonQuestionList = async function () {
    try {
      const response = await getCommonQuestionList()
      commonQuestionList.value = response.data
      console.log('response common question list', response)
    } catch (error) {
      console.log('error common question list')
    }
  }
  
  const requestMyQuestionList = async function () {
    try {
      const response = await getMyQuestionList()
      myQuestionList.value = response.data
      console.log('response my question list', response)
    } catch (error) {
      alert(`나의 면접 문항 목록을 불러오지 못했습니다. `)
      console.log('error my question list', error)
    }
  }
  
  return {
    commonQuestionList,
    myQuestionList,
    requestCommonQuestionList,
    requestMyQuestionList,
  }
})
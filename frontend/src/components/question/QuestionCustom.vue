<script setup>
// lib
import draggable from 'vuedraggable'
import { ref, onMounted, onUpdated, watch } from 'vue'

// api
import {
  patchUpdateMyQuestionFolder,
  patchUpdateMyQuestion,
  postCreateMyQuestion,
  patchMoveMyQuestion
} from '@/api/question'

// assets
import QuestionModalCreate from '@/components/question/modal/QuestionModalCreate.vue'
import QuestionModalDelete from '@/components/question/modal/QuestionModalDelete.vue'
import editImage from '@/assets/question/editImage.png'
import folderImage from '@/assets/question/folderImage.svg'

// store
import { storeToRefs } from 'pinia'
import { useQuestionStore } from '@/stores/question.js'
const questionStore = useQuestionStore()
const { myQuestionList } = storeToRefs(questionStore)

onMounted(() => {
  questionStore.requestMyQuestionList()
})

const start = ref({
  myQuestionId: null,
  myQuestionFolderId: null
})
const end = ref({
  myQuestionId: null,
  myQuestionFolderId: null,
  question: null,
  commonQuestionId: null
})

const toggle = ref(false)

watch(
  [start, end],
  async ([newStart, newEnd]) => {
    if (toggle.value == true) {
      toggle.value = false
    } else if (newStart.myQuestionId === newEnd.myQuestionId) {
      try {
        const payload = {
          myQuestionId: newStart.myQuestionId,
          fromMyQuestionFolderId: newStart.myQuestionFolderId,
          toMyQuestionFolderId: newEnd.myQuestionFolderId
        }

        const response = await patchMoveMyQuestion(payload)
        console.log('response move my question', response)

        end.value.myQuestionId = null
        toggle.value = true
        questionStore.requestMyQuestionList()
      } catch (error) {
        console.log('error move my question', error)
      }
    } else {
      try {
        const payload = {
          myQuestionFolderId: newEnd.myQuestionFolderId,
          commonQuestionId: newEnd.commonQuestionId,
          question: newEnd.question
        }

        const response = await postCreateMyQuestion(payload)
        console.log('response create my question', response)

        end.value.myQuestionId = null
        toggle.value = true
        questionStore.requestMyQuestionList()
      } catch (error) {
        console.log('error create my question', error)
      }
    }
  },
  { deep: true }
)

const log = async function (event, folder) {
  if (event.removed) {
    start.value.myQuestionId = event.removed.element.myQuestionId
    start.value.myQuestionFolderId = folder.myQuestionFolderId
  }
  if (event.added) {
    end.value.myQuestionId = event.added.element.myQuestionId
    end.value.myQuestionFolderId = folder.myQuestionFolderId
    end.value.question = event.added.element.commonQuestion
    end.value.commonQuestionId = event.added.element.commonQuestionId
  }

  questionStore.requestMyQuestionList()
}

const enableEditingMyQuestion = function (element) {
  if (element.commonQuestionId) {
    alert('공통 면접 질문은 수정할 수 없습니다. ')
  } else {
    element.isEditing = true
    element.editableQuestion = element.question
  }
}

const requestUpdateMyQuestion = async function (element) {
  element.isEditing = false
  // element.question = element.editableQuestion

  try {
    const payload = { question: element.editableQuestion }
    const response = await patchUpdateMyQuestion(element.myQuestionId, payload)
    console.log('response upadte my question', response)

    questionStore.requestMyQuestionList()
  } catch (error) {
    console.log('error update my question', error)
  }
}

const enableEditingMyQuestionFolder = function (folder) {
  folder.isEditing = true
  folder.editableQuestion = folder.myQuestionFolder
}

const requestUpdateMyQuestionFolder = async function (folder) {
  folder.isEditing = false
  // element.question = element.editableQuestion

  try {
    const payload = { myQuestionFolder: folder.editableQuestion }
    const response = await patchUpdateMyQuestionFolder(
      folder.myQuestionFolderId,
      payload
    )
    console.log('response upadte my question folder', response)

    questionStore.requestMyQuestionList()
  } catch (error) {
    console.log('error update my question folder', error)
  }
}

const search = ref('')
</script>

<template>
  <div class="question-title pa-3">나의 면접 문항 목록</div>
  <div class="bg-white pa-3 d-flex align-center">
    <div class="me-auto d-flex align-center"></div>
    <!-- <v-text-field
      v-model="search"
      label="검색어를 입력해주세요"
      append-inner-icon="mdi-magnify"
      single-line
      variant="solo"
      density="compact"
      hide-details
    ></v-text-field> -->
    <QuestionModalCreate content="폴더" />
  </div>
  <div style="max-height: 80%; overflow-y: auto; overflow-x: hidden">
    <v-expansion-panels variant="accordion" multiple>
      <!-- 폴더가 없는 경우 -->
      <template v-if="myQuestionList.length == 0">
        <div class="text-grey" style="margin-top: 25vh">
          새 폴더를 생성해주세요
        </div>
      </template>
      <!-- 폴더가 있는 경우 -->
      <template v-else>
        <v-expansion-panel
          v-for="folder in myQuestionList"
          :key="folder.myQuestionFolderId"
        >
          <v-hover v-slot="{ isHovering, props }">
            <v-expansion-panel-title v-bind="props" color="grey-lighten-2">
              <v-col cols="auto">
                <v-img
                  v-if="isHovering"
                  width="20"
                  :src="editImage"
                  @click.stop="enableEditingMyQuestionFolder(folder)"
                ></v-img>
                <v-img v-else width="20" :src="folderImage"></v-img>
              </v-col>
              <v-col class="d-flex align-center">
                <template v-if="folder.isEditing">
                  <v-text-field
                    single-line
                    variant="solo"
                    density="compact"
                    hide-details
                    v-model="folder.editableQuestion"
                    @blur="requestUpdateMyQuestionFolder(folder)"
                    @keyup.enter="requestUpdateMyQuestionFolder(folder)"
                  ></v-text-field>
                </template>
                <template v-else>
                  {{ folder.myQuestionFolder }}
                </template>
              </v-col>
              <v-col cols="auto" class="d-flex align-center">
                <QuestionModalCreate
                  content="질문"
                  :my-question-folder-id="folder.myQuestionFolderId"
                />
                <QuestionModalDelete
                  content="폴더"
                  :my-question-folder-id="folder.myQuestionFolderId"
                  :my-question-folder="folder.myQuestionFolder"
                />
              </v-col>
            </v-expansion-panel-title>

            <!-- 폴더에 질문이 없는 경우 -->
            <template v-if="folder.myQuestionList.length == 0">
              <draggable
                :list="['질문을 생성해주세요.']"
                group="question"
                @change="(event) => log(event, folder)"
                item-key="myQuestionId"
              >
                <template #item="{ element }">
                  <v-expansion-panel-text class="text-grey">
                    {{ element }}
                  </v-expansion-panel-text>
                </template>
              </draggable>
            </template>

            <!-- 폴더에 질문이 있는 경우 -->
            <template v-else>
              <draggable
                :list="folder.myQuestionList"
                group="question"
                @change="(event) => log(event, folder)"
                item-key="myQuestionId"
              >
                <template #item="{ element }">
                  <v-expansion-panel-text>
                    <v-row class="d-flex">
                      <v-col
                        class="d-flex align-center"
                        @dblclick="enableEditingMyQuestion(element)"
                      >
                        <template v-if="element.isEditing">
                          <v-text-field
                            single-line
                            variant="solo"
                            density="compact"
                            hide-details
                            v-model="element.editableQuestion"
                            @keyup.enter="requestUpdateMyQuestion(element)"
                            @blur="requestUpdateMyQuestion(element)"
                          ></v-text-field>
                        </template>
                        <template v-else>
                          {{ element.question }}
                        </template>
                      </v-col>
                      <v-col cols="auto">
                        <QuestionModalDelete
                          content="파일"
                          :my-question="element"
                        />
                      </v-col>
                    </v-row>
                  </v-expansion-panel-text>
                </template>
              </draggable>
            </template>
          </v-hover>
        </v-expansion-panel>
      </template>
    </v-expansion-panels>
  </div>
</template>

<style scoped>
.question-title {
  background-color: #4f2960;
  color: white;
}

.checkbox-22-messages {
  display: none !important;
}

.v-input__details {
  display: none !important;
}

.v-expansion-panel-title {
  padding: 0px !important;
  padding-left: 10px !important;
  padding-right: 10px !important;
}

.v-expansion-panel-text :deep(.v-expansion-panel-text__wrapper) {
  padding: 4px 24px 8px !important;
  box-shadow: 1px 1px 0px rgba(0, 0, 0, 0.2);
}

.text-grey {
  color: rgb(193, 193, 193);
}
</style>

<style>
.v-input__details {
  display: none !important;
}
</style>

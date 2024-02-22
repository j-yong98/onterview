<script setup>
// lib
import { ref, computed, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'

// api
import { getAllPostList, getMyPostList } from '@/api/community'

// asset
import searchButton from '@/assets/community/searchButton.svg'

// store
import { useUserStore } from '@/stores/user'

onMounted(() => {
  requestAllPostList()
})

const requestAllPostList = async function () {
  try {
    const response = await getAllPostList(
      selectPost.value.order,
      searchCategory.value.value,
      searchQuery.value
    )

    postList.value = response.data
    isAllPostList.value = true
  } catch (error) {
    alert(`전체 게시글을 조회하지 못했습니다.`)
  }
}

const requestMyPostList = async function () {
  try {
    const response = await getMyPostList(
      selectPost.value.order,
      searchCategory.value.value,
      searchQuery.value
    )

    postList.value = response.data
    isAllPostList.value = false
  } catch (error) {
    alert('내가 쓴 게시글을 조회하지 못했습니다.')
  }
}

const isLogin = function () {
  const userStore = useUserStore()
  return userStore.accessToken === null
}

const isAllPostList = ref(true)

// 검색
const searchCategory = ref({ category: '선택', value: '' })
const searchQuery = ref('')

const openSearchMenu = ref(false)

const clickSearchButton = function () {
  if (searchCategory.value.value == '' && searchQuery.value != '') {
    openSearchMenu.value = true
  } else {
    isAllPostList.value ? requestAllPostList() : requestMyPostList()
  }
}

// 정렬
const selectPost = ref({ title: '최신순', order: 'recent' })
const orderPost = ref([
  { title: '최신순', order: 'recent' },
  { title: '추천많은 순', order: 'like' },
  { title: '댓글많은 순', order: 'comment' }
])

watch(selectPost, (newOrder, oldOrder) => {
  if (isAllPostList.value) {
    requestAllPostList()
  } else {
    requestMyPostList()
  }
})

// 게시판
const postList = ref([])

const page = ref(1)
const itemsPerPage = ref(9)
const headers = ref([
  {
    align: 'start',
    key: 'writerNickname',
    sortable: false,
    title: '작성자'
  },
  { title: '면접 질문', key: 'title', sortable: false },
  { title: '추천수', key: 'likeCount', sortable: false, align: 'center' },
  { title: '댓글수', key: 'commentCount', sortable: false, align: 'center' },
  { title: '작성날짜', key: 'writtenDate', sortable: false }
])

const pageCount = computed(() => {
  return Math.ceil(postList.value.length / itemsPerPage.value)
})

// router
const router = useRouter()

const goCommunityDetail = function (articleId) {
  router.push({ name: 'community-detail', params: { articleId: articleId } })
}

const goCommunityWrite = function () {
  router.push({ name: 'community-write' })
}
</script>

<template>
  <v-container>
    <!-- 전체보기, 내가 쓴 게시글 보기 토글 -->
    <v-row justify="end" class="mb-3">
      <v-btn
        density="compact"
        variant="plain"
        :href="'#전체보기'"
        @click="requestAllPostList()"
        >전체 보기</v-btn
      >
      <div style="color: rgb(190, 190, 190)">|</div>
      <v-btn
        density="compact"
        variant="plain"
        :href="'#내가쓴글'"
        @click="requestMyPostList()"
        :disabled="isLogin()"
        >내가 쓴 게시글 보기</v-btn
      >
    </v-row>
    <v-row>
      <v-col cols="4">
        <v-row class="align-center">
          <v-col cols="4" class="px-0 mx-0">
            <v-select
              label="선택"
              :items="[
                { category: '제목', value: 'title' },
                { category: '내용', value: 'content' }
              ]"
              v-model="searchCategory"
              item-title="category"
              item-value="value"
              single-line
              variant="solo"
              density="compact"
              hide-details
              class="mr-3"
              return-object
              :menu="openSearchMenu"
            ></v-select>
          </v-col>
          <v-col cols="6" class="px-0 mx-0">
            <v-text-field
              v-model="searchQuery"
              label="검색"
              single-line
              variant="solo"
              density="compact"
              hide-details
              @keyup.enter="clickSearchButton()"
            ></v-text-field>
          </v-col>
          <v-col cols="2">
            <v-btn
              variant="elevated"
              color="#BB66FF"
              @click="clickSearchButton()"
              ><v-icon size="x-large">mdi-magnify</v-icon></v-btn
            >
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="5"></v-col>
      <v-col cols="3" class="d-flex justify-end align-center">
        <!-- 필터 영역 -->
        <v-select
          label="정렬"
          :items="orderPost"
          v-model="selectPost"
          item-title="title"
          item-value="order"
          single-line
          variant="solo"
          density="compact"
          hide-details
          return-object
        ></v-select>
        <!-- 글쓰기 버튼 -->
        <v-btn color="#BB66FF" class="ml-2" @click="goCommunityWrite()"
          >글쓰기</v-btn
        >
      </v-col>
    </v-row>
    <!-- 게시판 -->
    <v-data-table
      v-model:page="page"
      :headers="headers"
      :items="postList"
      :items-per-page="itemsPerPage"
      hover
      style="height: 80vh"
    >
      <template v-slot:item="{ item }">
        <tr @click="goCommunityDetail(item.articleId)">
          <td>{{ item.writerNickname }}</td>
          <td>{{ item.title }}</td>
          <td align="center">{{ item.likeCount }}</td>
          <td align="center">{{ item.commentCount }}</td>
          <td>{{ item.writtenDate }}</td>
        </tr>
      </template>
      <template v-slot:bottom>
        <div class="text-center pt-2">
          <v-pagination
            v-model="page"
            :length="pageCount"
            :total-visible="8"
            rounded="circle"
            prev-icon="mdi-menu-left"
            next-icon="mdi-menu-right"
            active-color="#BB66FF"
            density="comfortable"
          ></v-pagination>
        </div>
      </template>
      <template v-slot:no-data> 게시글이 없습니다 </template>
    </v-data-table>
  </v-container>
</template>

<style scoped>
.v-btn--size-default {
  min-width: 40px !important;
}
</style>

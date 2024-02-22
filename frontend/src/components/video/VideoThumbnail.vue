<script setup>
import { ref, watch } from 'vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech';
import { apiMethods } from '@/api/video';

const selfSpeechStore = useSelfSpeechStore();

const markVideo = async function (id, bool) {
  try {
    const req_body = {
      bookmark: !bool
    }
    const result = await apiMethods.patchVideo(id, req_body)
    console.log(result.data);
  } catch (error) {
    console.log(error);
  }
}

const selectVideo = async function (v_id) {
  
  try {
    const res = await apiMethods.getVideo(v_id);
    selfSpeechStore.videoData = res.data;
  } catch (error) {
    console.log(error)
  }
  selfSpeechStore.display = false;
}

</script>

<template>
<div class="h-100">
  <v-sheet class="h-100" elevation="1" min-width="200" max-width="900">
    <v-slide-group class="h-100" v-model="selfSpeechStore.selectedVideo" show-arrows center-active>
      <v-slide-group-item v-for="video in selfSpeechStore.questionData.videoInformationResponseList" :key="video.videoId" v-slot="{ isSelected, toggle, selectedClass }">
        <v-card :color="isSelected ? 'primary' : 'grey-lighten-1'" :class="['ma-3', selectedClass]" width="150">
        <!-- <v-card :color="isSelected ? 'primary' : 'grey-lighten-1'" :class="['ma-3', selectedClass]" width="150" @click="toggle(), selectVideo(video.videoId)"> -->
          <div class="d-flex flex-column align-center justify-center pa-1">
            <div class="thumbnail-container d-flex flex-column align-center">
              <v-img :src="video.thumbnailUrl.saveFilename" width="120" height="80" class="img-container" @click="toggle(), selectVideo(video.videoId)"></v-img>
              <div style="font-size: 12px;">{{video.title}}</div>
              <v-icon 
                v-show="!video.bookmark" 
                class="icon"
                color="purple" 
                size="32" 
                icon="mdi-bookmark-outline"
                @click="markVideo(video.videoId, video.bookmark), video.bookmark=!video.bookmark, selfSpeechStore.videoData.bookmark=!selfSpeechStore.videoData.bookmark"
              >
              </v-icon>
              <v-icon 
                v-show="video.bookmark" 
                class="icon"
                color="purple" 
                size="32" 
                icon="mdi-bookmark-check"
                @click="markVideo(video.videoId, video.bookmark), video.bookmark=!video.bookmark, selfSpeechStore.videoData.bookmark=!selfSpeechStore.videoData.bookmark"
              >
              </v-icon>
            </div>
          </div>
        </v-card>
      </v-slide-group-item>
    </v-slide-group>
  </v-sheet>
</div>
</template>

<style scoped>
.img-container{
  cursor: pointer;
}
.thumbnail-container{
  position: relative;
}
.icon{
  position: absolute;
  top: -5%;
  left: -5%;
}
</style>

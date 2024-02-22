<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

const mediaToggle = ref({
  video: true,
  audio: true,
})

const startVideo = function () {
    navigator.mediaDevices.getUserMedia({
    audio: true,
    video: {
      width: { min: 150, ideal: 360, max: 720 },
      height: { min: 100, ideal: 240, max: 480 },
    },
  })
    .then(stream => {
      const previewPlayer = document.querySelector("#my-video");
      previewPlayer.srcObject = stream;
    })
}

const controlMedia = function (com) {
  const myStream = document.querySelector('#my-video').captureStream()

  if (com === 0) {
    mediaToggle.value.video = !mediaToggle.value.video
    myStream.getVideoTracks().forEach(track => {
      track.enabled = mediaToggle.value.video;
    });
  }
  else if (com === 1) {
    mediaToggle.value.audio = !mediaToggle.value.audio
    myStream.getAudioTracks().forEach(track => {
      track.enabled = mediaToggle.value.audio
    });
  }
}

onMounted(() => {
  startVideo()
})

onBeforeUnmount(() => {
  const previewPlayer = document.querySelector("#my-video")
  if (previewPlayer.srcObject) {
      const tracks = previewPlayer.srcObject.getTracks();
      tracks.forEach(track => track.stop());
      previewPlayer.srcObject = null;
  }
})
</script>

<template>
  <div class="w-100 h-100 d-flex justify-center">
    <video 
      ref="videoPlayer"
      class="video-js vjs-big-play-centered w-auto h-100"
      id="my-video"
      data-setup='{}'
      autoplay
    ></video>
    <div class="d-flex flex-column align-center justify-center">
      <v-btn class="ma-3" @click="controlMedia(0)" v-if="mediaToggle.video" icon="mdi-video" color="grey-lighten-1"></v-btn>
      <v-btn class="ma-3" @click="controlMedia(0)" v-else icon="mdi-video-off" color="grey-lighten-1"></v-btn>
      <v-btn class="ma-3" @click="controlMedia(1)" v-if="mediaToggle.audio" icon="mdi-microphone" color="grey-lighten-1"></v-btn>
      <v-btn class="ma-3" @click="controlMedia(1)" v-else icon="mdi-microphone-off" color="grey-lighten-1"></v-btn>
    </div>
  </div>

</template>

<style scoped>
video {
  transform: rotateY(180deg);
  -webkit-transform: rotateY(180deg);
  /* Safari and Chrome */
  -moz-transform: rotateY(180deg);
  /* Firefox */
}
</style>
<script setup>
import interviewMultiHelp from "@/components/interview/interviewMultiHelp.vue"
import { useInterviewStore } from "@/stores/interview"

const interviewStore = useInterviewStore()
</script>

<template>
  <v-dialog v-model="interviewStore.dialog.help">
    <v-card class="bg-purple-lighten-4 pa-5">
      <v-card-title class="d-flex flex-row-reverse">
        <v-btn @click="interviewStore.dialog.help = false" icon="mdi-close"></v-btn>
      </v-card-title>
      <v-card-text class="bg-white">
        <div class="w-100 h-100">
          <interviewMultiHelp />
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<style scoped></style>
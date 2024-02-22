<script setup>
import QuestionCommon from '@/components/question/QuestionCommon.vue'
import QuestionCustom from '@/components/question/QuestionCustom.vue'
</script>

<template>
  <v-layout class="question-view d-flex row justify-center" style="height: 94vh">
    <v-col cols="3" class="common-question-list my-5">
      <QuestionCommon />
    </v-col>
    <v-col cols="7" class="my-question-list my-5">
      <QuestionCustom />
    </v-col>
  </v-layout>
</template>

<style scoped>
.question-view {
  background-color: #efe6ef;
}

.common-question-list {
  height: 87vh;
  background-color: white;
  padding: 0;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
}
.my-question-list {
  height: 87vh;
  background-color: white;
  padding: 0;
  margin-left: 1%;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);
}

.v-expansion-panel-title {
  padding: 0px !important;
}
</style>

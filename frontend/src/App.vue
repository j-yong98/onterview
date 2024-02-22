<script setup>
import MainNav from '@/components/common/MainNav.vue'
import MainFooter from '@/components/common/MainFooter.vue'
import { useRoute } from 'vue-router'

const route = useRoute()
</script>

<template>
  <div>
    <MainNav v-if="route.meta.layout == 'main'" />
    <RouterView />
    <!-- <MainFooter v-if="route.meta.layout == 'main'" /> -->
  </div>
</template>

<style scoped></style>

<script setup>
import { ref } from 'vue'

// const links = ref(['Home', 'About Us', 'Team', 'Services', 'Blog', 'Contact Us'])
</script>

<template>
  <div>
    <v-layout>
      <v-footer height="15vh" class="footer text-center d-flex justify-center">
        <div>
          Copy &copy {{ new Date().getFullYear() }} - 조용훈 김승배 김은진 박호제 이경민 이은지
          최재용
        </div>
      </v-footer>
    </v-layout>
  </div>
</template>

<style scoped>
.footer {
  background-color: #6a3692;
  color: white;
}
</style>

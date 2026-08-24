<template>
  <div class="loading">
    <a-spin size="large"/>
  </div>
</template>

<script setup>
import {onMounted} from 'vue'
import {useRouter} from 'vue-router'

const router = useRouter()

onMounted(() => {
  const lastPage = localStorage.getItem('LAST_PAGE') || '/manage/welcome'
  router.replace(lastPage)
})
</script>

<style scoped>
.loading {
  display: flex;
  width: 100vw;
  height: 100vh;
  align-items: center;
  justify-content: center;
  background: #fff;
}
</style>

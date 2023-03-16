<template>
  <div class="loading">
    <a-spin :tip="loadingText" size="large"/>

  </div>
</template>

<script>
export default {
  name: "Loading",
  data() {
    return {
      loading: true,
      loadingText: '页面渲染中。。。',
    }
  },
  mounted() {
    this.$router.replace('/manage/index')

    setTimeout(() => {
      this.$store.commit('INIT_ROUTER', null)
      this.$router.replace('/manage/index')
    }, 3000)
  }
}
</script>

<style scoped>
.loading {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #fff;
}
</style>

<template>
  <div class="loading">
    <a-spin :tip="loadingText" size="large"/>
  </div>
</template>

<script>

export default {
  name: 'Loading',
  data() {
    return {
      loading: true,
      loadingText: '页面渲染中。。。',
    }
  },
  mounted() {
    console.log("loading页面调用初始化路由")
    this.$store.commit('INIT_ROUTER', null)
    let checkLoad = setInterval(() => {
      const loadFinish = this.$store.state.sys.loadFinish
      if (loadFinish) {
        clearInterval(checkLoad)
        this.$router.replace('/manage/index')
      }
    }, 5)
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

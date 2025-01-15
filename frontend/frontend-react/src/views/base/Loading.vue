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
    const _this = this
    let checkLoad = setInterval(() => {
      const loadFinish = this.$store.getters.LOAD_FINISH
      console.log('监听路由渲染状态:', loadFinish)
      if (loadFinish) {
        console.log('检测到路由初始化完成', '即将跳转页面')
        clearInterval(checkLoad)
        let lastPage = localStorage.getItem('LAST_PAGE')
        if (!lastPage || lastPage === '/loading') {
          lastPage = '/manage/index'
        }
        console.log('跳转页面', lastPage)
        _this.$router.replace(lastPage)
      }
    }, 1000)
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

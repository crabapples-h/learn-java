<template>
  <div id="app">
    <keep-alive>
      <router-view/>
    </keep-alive>
  </div>
</template>
<script>
import storage from '@/store/storage'
import Loading from './views/base/Loading'
import { whiteList } from '@/router'

export default {
  name: 'App',
  data() {
    return {}
  },
  created() {
    this.init()
  },
  mounted() {
    const _this = this
    window.addEventListener('beforeunload', function (event) {
      let LAST_PAGE = _this.$route.path
      let isWhiteList = whiteList.includes(LAST_PAGE)
      if (isWhiteList) {
        localStorage.setItem('LAST_PAGE', '/manage-index')
      } else {
        localStorage.setItem('LAST_PAGE', _this.$route.path)
      }
      // event.returnValue = '确定要离开页面吗？'
    })
  },
  methods: {
    init() {
      const token = storage.getToken()
      if (token) {
        this.$store.dispatch('INIT_TOKEN', token)
        this.$store.dispatch('USER_BASE_INFO')
        this.$store.dispatch('ROLES')
        this.$store.dispatch('MENUS')
        this.$store.dispatch('PERMISSIONS')
      } else {
        console.log('token不存在:', token)
      }
    },
  }
}
</script>

<style>
#app {
    margin: 0;
    padding: 0;
}
</style>

<template>
  <div id="app">
    <keep-alive>
      <router-view/>
    </keep-alive>
  </div>
</template>
<script>
import storage from '@/store/storage'
export default {
  name: 'App',
  components: {},
  data() {
    return {}
  },
  created() {
      //在页面加载时读取sessionStorage里的状态信息
      if (sessionStorage.getItem('store')) {
        this.$store.replaceState(Object.assign({}, this.$store.state, JSON.parse(localStorage.getItem('store'))));
      }

      //在页面刷新时将vuex里的信息保存到sessionStorage里
      window.addEventListener('beforeunload', () => {
        localStorage.setItem('store', JSON.stringify(this.$store.state));
      });
    // const _this = this
    // let routerMap = getRouterMap()
    // $addRouters(routerMap)
    // let path = sessionStorage.getItem("pathName")
    // sessionStorage.removeItem("pathName")
    // if (!!path) {
    //   _this.$router.push({path: path})
    // }
    // window.addEventListener("beforeunload", () => {
    //   sessionStorage.setItem("pathName", window.location.pathname)
    // })
    // this.$store.commit('setUserInfo', storage.getUserInfo())
    // this.$store.commit('setUserMenus', storage.getUserMenus())
    // this.$store.commit('setUserPermissions', storage.getPermissions())
  },
  mounted() {
  },
  methods: {}
}
</script>

<style>
#app {
  margin: 0;
  padding: 0;
}
</style>

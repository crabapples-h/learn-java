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

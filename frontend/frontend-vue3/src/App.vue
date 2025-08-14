<template>
  <div id="app">
    <router-view v-slot="{ Component }">
      <component :is="Component"/>
    </router-view>
  </div>
</template>
<script setup>
import {onMounted, ref} from 'vue'

onMounted(() => {
  {
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
  }

})
</script>

<style>
#app {
  margin: 0;
  padding: 0;
}
</style>

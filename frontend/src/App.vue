<template>
  <div id="app">
    <keep-alive>
      <router-view/>
    </keep-alive>
  </div>
</template>
<script>
import {getRouterMap, setPermissions} from '@/utils/sessionUtils'
import {$addRouters} from '@/router'
import commonApi from "@/api/CommonApi";

export default {
  name: 'App',
  components: {},
  data() {
    return {}
  },
  created() {
    const _this = this
    let routerMap = getRouterMap()
    $addRouters(routerMap)
    let path = sessionStorage.getItem("pathName")
    sessionStorage.removeItem("pathName")
    if (!!path) {
      _this.$router.push({path: path})
    }
    window.addEventListener("beforeunload", () => {
      sessionStorage.setItem("pathName", window.location.pathname)
    })
  },
  mounted() {
  },
  methods: {
    getPermissions() {
      return commonApi.getUserPermissions().then(result => {
        if (result.status !== 200) {
          return;
        }
        if (result.data !== null) {
          return Promise.resolve({status: true, data: result.data})
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
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

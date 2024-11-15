<template>
  <div id="app">
    <keep-alive>
      <router-view/>
    </keep-alive>
  </div>
</template>
<script>
import storage from '@/store/storage'
import { whiteList } from '@/router'

export default {
  name: 'App',
  data() {
    return {}
  },
  beforeCreate() {
  },
  created() {
    this.init()
  },
  mounted() {
    const _this = this
    window.addEventListener('beforeunload', function (event) {
      // 页面刷新时获取当前页面的path地址放入缓存中，
      // 如果path地址在白名单中，则说明用户并没有登录，则直接将首页放入缓存
      let LAST_PAGE = _this.$route.path
      let isWhiteList = whiteList.includes(LAST_PAGE)
      if (isWhiteList) {
        localStorage.setItem('LAST_PAGE', '/manage/index')
      } else {
        localStorage.setItem('LAST_PAGE', _this.$route.path)
      }
      // 刷新时做一个标记
      localStorage.setItem('RELOAD_PAGE', '1')
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
        // 初始化动态路由
        this.$store.dispatch('MENUS')
        this.$store.dispatch('PERMISSIONS')
        // 获取刷新标记，如果用户是刷新页面则跳到loading页，如果不是刷新页面则直接跳转到指定页面
        // - 用于处理直接在浏览器输入url的情况，防止输入时跳转到loading页
        // - loading页是从缓存中获取刷新前保存的地址，这样会导致即使输入url访问也会跳到最后一次刷新页面时保存的url
        let isReload = localStorage.getItem('RELOAD_PAGE')
        localStorage.removeItem('RELOAD_PAGE')
        if (!isReload) {
          this.$router.replace('/loading')
        }
      } else {
        console.log('token不存在:', token)
      }
    },
  }
}
</script>

<style lang="less">
@import "~@public/color.less";

#app {
  margin: 0;
  padding: 0;
  height: 100vh;
  width: 100vw;
}

/*iconfont 全局样式*/
.iconfont {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}

/*滚动条整体样式*/
*::-webkit-scrollbar {
  width: 5px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 10px;
  margin-top: 10px;
  margin-right: 10px;
  opacity: 0.2;
}

/*滚动条里面小方块(滑块 )*/
*::-webkit-scrollbar-thumb {
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 1px @primary-color;
  background: fade(@blue-5, 80%);
  //opacity: 0.2;
}

/*滚动条里面轨道(背景)*/
//*::-webkit-scrollbar-track {
//  -webkit-box-shadow: inset 0 0 2px @pink-5;
//  border-radius: 10px;
//  background: fade(@pink-5, 10%);
//  //opacity: 0.2;
//}
</style>

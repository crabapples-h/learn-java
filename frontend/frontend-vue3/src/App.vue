<template>
  <router-view />
</template>

<script setup>
import { onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { whiteList } from '@/router'
import storage from '@/store/storage'
import { useUserStore } from '@/store/modules/user'
import { useSysStore } from '@/store/modules/sys'
import { usePermissionStore } from '@/store/modules/permissions'

const route = useRoute()
const userStore = useUserStore()
const sysStore = useSysStore()
const permissionStore = usePermissionStore()

const init = () => {
  const token = storage.getToken()
  if (!token) {
    return
  }

  userStore.initToken(token)
  userStore.loadUserBaseInfo()
  sysStore.loadServerAddress()
  sysStore.loadFilePreviewAddress()
  permissionStore.loadMenusTree()
  permissionStore.loadMenusList()
  permissionStore.loadPermissions()
}

init()

onMounted(() => {
  window.addEventListener('beforeunload', () => {
    const lastPage = route.path
    const isWhiteList = whiteList.includes(lastPage)
    localStorage.setItem('LAST_PAGE', isWhiteList ? '/manage/index' : lastPage)

    if (!isWhiteList) {
      localStorage.setItem('OPEN_MENU_IDS', route.meta.pid || '')
      localStorage.setItem('SELECT_MENU_IDS', route.meta.id || '')
    }

    localStorage.setItem('RELOAD_PAGE', '1')
  })
})
</script>

<style lang="less">
@import '@public/color.less';

html,
body,
#app {
  margin: 0;
  padding: 0;
  width: 100vw;
  height: 100vh;
}

body {
  overflow: hidden;
  color: #1f2937;
  background: #f5f7fb;
}

.iconfont {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}

* {
  box-sizing: border-box;
}

*::-webkit-scrollbar {
  width: 5px;
  height: 10px;
}

*::-webkit-scrollbar-thumb {
  border-radius: 10px;
  box-shadow: inset 0 0 1px @primary-color;
  background: fade(@blue-5, 80%);
}
</style>

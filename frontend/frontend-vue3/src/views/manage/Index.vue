<template>
  <a-layout class="manage-layout">
    <c-page-header :title="title" :user-info="userInfo" :theme="theme" />
    <a-layout class="manage-body">
      <c-page-menus :menus="menus" :theme="theme" @click-menu="clickMenu" />
      <a-layout-content class="content">
        <div class="content-surface">
          <router-view />
        </div>
      </a-layout-content>
    </a-layout>
    <c-page-footer v-if="showFooter" :theme="theme" />
  </a-layout>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import CPageHeader from '@/views/common/C-PageHeader.vue'
import CPageMenus from '@/views/common/C-PageMenus.vue'
import CPageFooter from '@/views/common/C-PageFooter.vue'
import { useUserStore } from '@/store/modules/user'
import { usePermissionStore } from '@/store/modules/permissions'

const router = useRouter()
const userStore = useUserStore()
const permissionStore = usePermissionStore()

const title = ref('后台管理系统')
const theme = ref('light')
const showFooter = ref(false)
const userInfo = computed(() => userStore.USER_BASE_INFO || {})
const menus = computed(() => permissionStore.MENUS_TREE || [])

const clickMenu = menu => {
  localStorage.setItem('OPEN_MENU_IDS', menu.pid || '')
  localStorage.setItem('SELECT_MENU_IDS', menu.id || '')

  if (menu.menusType === 3 && menu.link) {
    window.open(menu.link)
    return
  }

  router.push(menu.path)
}
</script>

<style scoped lang="less">
.manage-layout {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.manage-body {
  height: calc(100vh - 56px);
}

.content {
  padding: 16px;
  overflow: hidden;
  background: #f5f7fb;
}

.content-surface {
  height: 100%;
  padding: 18px;
  overflow: auto;
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 6px;
}
</style>

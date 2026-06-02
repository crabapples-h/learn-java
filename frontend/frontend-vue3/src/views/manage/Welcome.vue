<template>
  <section class="welcome">
    <h1>首页</h1>
    <a-row :gutter="16">
      <a-col :xs="24" :md="8">
        <a-statistic title="菜单数量" :value="menus.length" />
      </a-col>
      <a-col :xs="24" :md="8">
        <a-statistic title="权限数量" :value="permissions.length" />
      </a-col>
      <a-col :xs="24" :md="8">
        <a-statistic title="登录状态" :value="token ? '已登录' : '未登录'" />
      </a-col>
    </a-row>
  </section>
</template>

<script setup>
import { computed } from 'vue'
import { useUserStore } from '@/store/modules/user'
import { usePermissionStore } from '@/store/modules/permissions'

const userStore = useUserStore()
const permissionStore = usePermissionStore()

const token = computed(() => userStore.TOKEN)
const menus = computed(() => permissionStore.MENUS_TREE || [])
const permissions = computed(() => permissionStore.PERMISSIONS || [])
</script>

<style scoped>
.welcome {
  display: grid;
  gap: 20px;
}

h1 {
  margin: 0;
  font-size: 22px;
  font-weight: 600;
}
</style>

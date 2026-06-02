<template>
  <a-layout-header class="page-header">
    <div class="brand">
      <img src="@assets/logo.png" alt="">
      <span>{{ title }}</span>
    </div>
    <a-space>
      <span class="user-name">{{ displayName }}</span>
      <a-button size="small" @click="logout">退出</a-button>
    </a-space>
  </a-layout-header>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import storage from '@/store/storage'

const props = defineProps({
  title: {
    type: String,
    default: '后台管理系统',
  },
  userInfo: {
    type: Object,
    default: () => ({}),
  },
  theme: {
    type: String,
    default: 'light',
  },
})

const router = useRouter()
const displayName = computed(() => props.userInfo.nickname || props.userInfo.username || '管理员')

const logout = () => {
  storage.logout()
  router.replace('/login')
}
</script>

<style scoped lang="less">
@import '@public/theme.less';

.page-header {
  display: flex;
  height: 56px;
  line-height: 56px;
  align-items: center;
  justify-content: space-between;
  padding: 0 18px;
  color: #fff;
  background: @primary-color;
}

.brand {
  display: flex;
  min-width: 0;
  align-items: center;
  gap: 10px;
  font-size: 18px;
  font-weight: 600;
}

.brand img {
  width: 28px;
  height: 28px;
}

.user-name {
  color: #fff;
}
</style>

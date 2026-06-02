<template>
  <div class="login-page">
    <a-form class="login-panel" :model="form" layout="vertical" @finish="submit">
      <div class="title">用户登录</div>
      <a-form-item name="username" :rules="[{ required: true, message: '请输入用户名' }]">
        <a-input v-model:value="form.username" autocomplete="off" placeholder="用户名" />
      </a-form-item>
      <a-form-item name="password" :rules="[{ required: true, message: '请输入密码' }]">
        <a-input-password v-model:value="form.password" autocomplete="off" placeholder="密码" />
      </a-form-item>
      <a-button class="login-button" type="primary" html-type="submit" :loading="loading">
        立即登录
      </a-button>
    </a-form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/modules/user'
import { useRoleStore } from '@/store/modules/roles'
import { usePermissionStore } from '@/store/modules/permissions'

const router = useRouter()
const userStore = useUserStore()
const roleStore = useRoleStore()
const permissionStore = usePermissionStore()
const loading = ref(false)

const form = reactive({
  username: '',
  password: '',
})

const submit = async () => {
  loading.value = true
  try {
    await userStore.login(form)
    await Promise.all([
      userStore.loadUserBaseInfo(),
      roleStore.loadRoles(),
      permissionStore.loadMenusTree(),
      permissionStore.loadMenusList(),
      permissionStore.loadPermissions(),
    ])
    router.replace('/loading')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="less">
@import '@public/theme.less';

.login-page {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background: url('@assets/login-background.png') no-repeat center;
  background-size: cover;
  display: flex;
  align-items: flex-start;
  justify-content: center;
}

.login-panel {
  width: min(420px, calc(100vw - 32px));
  min-height: 360px;
  margin-top: 120px;
  padding: 40px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 20px 60px rgba(15, 23, 42, 0.16);
}

.title {
  margin: 0 0 28px -58px;
  padding: 18px 10px 18px 60px;
  color: #fff;
  font-size: 16px;
  background: @primary-color;
}

.login-button {
  width: 100%;
  height: 40px;
}
</style>

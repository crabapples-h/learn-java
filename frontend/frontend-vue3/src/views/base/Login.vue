<template>
  <div class="login-page">
    <div class="bg-decoration">
      <div class="blob blob-1"></div>
      <div class="blob blob-2"></div>
      <div class="blob blob-3"></div>
    </div>

    <div class="login-card">
      <div class="login-header">
        <img class="login-logo" src="@assets/logo.png" alt="logo" />
        <h1 class="login-title">欢迎回来</h1>
        <p class="login-subtitle">请输入您的账号信息登录系统</p>
      </div>

      <a-form class="login-form" :model="form" layout="vertical" @finish="submit">
        <a-form-item name="username" :rules="[{ required: true, message: '请输入用户名' }]">
          <a-input v-model:value="form.username" size="large" autocomplete="off" placeholder="用户名">
            <template #prefix>
              <svg class="input-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
                <circle cx="12" cy="7" r="4" />
              </svg>
            </template>
          </a-input>
        </a-form-item>

        <a-form-item name="password" :rules="[{ required: true, message: '请输入密码' }]">
          <a-input-password v-model:value="form.password" size="large" autocomplete="off" placeholder="密码">
            <template #prefix>
              <svg class="input-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <rect x="3" y="11" width="18" height="11" rx="2" ry="2" />
                <path d="M7 11V7a5 5 0 0 1 10 0v4" />
              </svg>
            </template>
          </a-input-password>
        </a-form-item>

        <div class="login-options">
          <a-checkbox v-model:checked="remember">记住我</a-checkbox>
          <a class="forgot-link" @click.prevent>忘记密码？</a>
        </div>

        <a-button class="login-button" type="primary" size="large" html-type="submit" :loading="loading" block>
          登 录
        </a-button>
      </a-form>
    </div>
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
const remember = ref(false)

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
.login-page {
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #0f2b52 0%, #1e4f8f 50%, #2daef6 100%);
}

/* 背景装饰光晕 */
.bg-decoration {
  position: absolute;
  inset: 0;
  pointer-events: none;
}

.blob {
  position: absolute;
  border-radius: 50%;
  filter: blur(60px);
  opacity: 0.45;
  animation: float 12s ease-in-out infinite;
}

.blob-1 {
  width: 420px;
  height: 420px;
  top: -120px;
  left: -100px;
  background: rgba(255, 255, 255, 0.18);
}

.blob-2 {
  width: 360px;
  height: 360px;
  bottom: -100px;
  right: -80px;
  background: rgba(255, 255, 255, 0.14);
  animation-delay: -4s;
}

.blob-3 {
  width: 260px;
  height: 260px;
  bottom: 25%;
  left: 12%;
  background: rgba(64, 169, 255, 0.25);
  animation-delay: -8s;
}

@keyframes float {
  0%, 100% { transform: translateY(0) scale(1); }
  50% { transform: translateY(-24px) scale(1.06); }
}

/* 登录卡片 */
.login-card {
  position: relative;
  z-index: 1;
  width: min(400px, calc(100vw - 32px));
  padding: 44px 40px 36px;
  background: rgba(255, 255, 255, 0.96);
  border-radius: 16px;
  box-shadow: 0 24px 64px rgba(0, 20, 60, 0.35);
  backdrop-filter: blur(12px);
  animation: slideUp 0.5s ease-out;
}

@keyframes slideUp {
  from { opacity: 0; transform: translateY(24px); }
  to { opacity: 1; transform: translateY(0); }
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
}

.login-logo {
  width: 56px;
  height: 56px;
  object-fit: contain;
  margin-bottom: 12px;
}

.login-title {
  margin: 0;
  font-size: 24px;
  font-weight: 600;
  color: #1f2d3d;
  letter-spacing: 1px;
}

.login-subtitle {
  margin: 8px 0 0;
  font-size: 13px;
  color: #8a94a6;
}

.login-form {
  :deep(.ant-input-affix-wrapper) {
    border-radius: 8px;
    border-color: #d9e2ef;
    transition: all 0.25s ease;

    &:hover {
      border-color: #2daef6;
    }

    &:focus-within {
      border-color: #2daef6;
      box-shadow: 0 0 0 3px rgba(45, 174, 246, 0.15);
    }
  }
}

.input-icon {
  width: 16px;
  height: 16px;
  color: #8a94a6;
}

.login-options {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: -4px 0 20px;
  font-size: 13px;
}

.forgot-link {
  color: #2daef6;
  cursor: pointer;
}

.login-button {
  height: 44px;
  border-radius: 8px;
  font-size: 16px;
  letter-spacing: 4px;
  background: linear-gradient(135deg, #2daef6 0%, #1890ff 100%);
  border: none;
  box-shadow: 0 8px 20px rgba(45, 174, 246, 0.35);
  transition: all 0.25s ease;

  &:hover {
    transform: translateY(-1px);
    box-shadow: 0 12px 24px rgba(45, 174, 246, 0.45);
  }

  &:active {
    transform: translateY(0);
  }
}

@media (max-width: 480px) {
  .login-card {
    padding: 36px 24px 28px;
  }
}
</style>

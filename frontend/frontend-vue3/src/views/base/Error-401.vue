<template>
  <a-result status="403" title="401" sub-title="未获授权">
    <template #extra>
      <a-button type="primary" @click="$router.replace('/login')">返回登录</a-button>
    </template>
  </a-result>
</template>

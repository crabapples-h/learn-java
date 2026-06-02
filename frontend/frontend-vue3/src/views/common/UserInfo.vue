<template>
  <a-descriptions :column="1" bordered>
    <a-descriptions-item label="用户名">{{ userInfo.username || '-' }}</a-descriptions-item>
    <a-descriptions-item label="昵称">{{ userInfo.nickname || '-' }}</a-descriptions-item>
  </a-descriptions>
</template>

<script setup>
defineProps({
  userInfo: {
    type: Object,
    default: () => ({}),
  },
})
</script>

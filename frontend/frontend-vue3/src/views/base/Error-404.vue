<template>
  <a-result status="404" title="404" sub-title="找不到页面">
    <template #extra>
      <a-button type="primary" @click="$router.replace('/manage/welcome')">返回首页</a-button>
    </template>
  </a-result>
</template>

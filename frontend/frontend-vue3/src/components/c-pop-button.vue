<!--
  自定义组件，带确认提示的按钮
  color：按钮颜色
  click：点击触发的事件
  title：确认信息提示
  okText：确认按钮文字
  text：按钮文字
  cancelText：取消按钮文字
  size：按钮大小
  type：按钮类型
  loading：loading状态
-->
<template>
  <a-popconfirm :title="title" :ok-text="okText" :cancel-text="cancelText" @confirm="confirm">
    <a-button :type="type" :size="size" :style="color" :loading="loading" :ghost="ghost">
      {{ text }}
    </a-button>
  </a-popconfirm>
</template>

<script setup>
const props = defineProps({
  color: {
    type: Object,
    default: () => null,
  },
  ghost: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: '按钮',
  },
  okText: {
    type: String,
    default: '确定',
  },
  cancelText: {
    type: String,
    default: '取消',
  },
  text: {
    type: String,
    default: '按钮',
  },
  size: {
    type: String,
    default: 'small',
  },
  type: {
    type: String,
    default: '',
  },
  loading: {
    type: Boolean,
    default: false,
  },
})

const emit = defineEmits(['click'])

const confirm = () => {
  emit('click')
}
</script>

<style scoped>
</style>

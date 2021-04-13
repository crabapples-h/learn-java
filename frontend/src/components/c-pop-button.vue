<template>
  <a-popconfirm :title="title" @confirm="confirm" :okText="okText" :cancelText="cancelText" :icon="icon">
    <a-button :type="type" :size="size" :style="color" :icon="buttonIcon" :loading="loading">{{ text }}</a-button>
  </a-popconfirm>
</template>

<script>
export default {
  name: 'c-pop-button',
  props: {
    color: {
      type: Object,
    },
    ok: {
      type: Function,
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
    icon: {
      type: String,
      default: '',
    },
    buttonIcon: {
      type: String,
      default: '',
    },
    type: {
      type: String,
      default: '',
    },
    loading: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    confirm() {
      this.$emit("ok")
    }
  }

}
</script>

<style scoped>
</style>

<!--
  自定义组件，带确认提示的按钮
  color：按钮颜色
  click：点击触发的事件
  title：确认信息提示
  okText：确认按钮文字
  text：按钮文字
  cancelText：取消按钮文字
  size：按钮大小
  icon：提示信息icon
  buttonIcon：按钮icon
  type：按钮类型
  loading：loading状态
-->
<template>
  <a-popconfirm :title="title" @confirm="confirm" :okText="okText" :cancelText="cancelText" :icon="icon">
    <a-button :type="type" :size="size" :style="color" :icon="buttonIcon" :loading="loading" :ghost="ghost">
      {{ text }}
    </a-button>
  </a-popconfirm>
</template>

<script>
export default {
  name: 'c-pop-button',
  props: {
    color: {
      type: Object,
    },
    click: {
      type: Function,
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
  watch: {},
  data() {
    return {
      // innerText: this.$slots.default[0].text
    }
  },
  methods: {
    confirm() {
      this.$emit("click")
    }
  }

}
</script>

<style scoped>
</style>

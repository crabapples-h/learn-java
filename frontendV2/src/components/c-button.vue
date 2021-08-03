<template>
  <a-button :size="size" :style="color" :icon="icon" :type="type"
            :loading="loading" @click="onclick">{{ text }}
  </a-button>
</template>

<script>
export default {
  name: 'c-button',

  props: {
    // colorExample:{
    //   color: {
    //     type: Object,
    //     default: () => {
    //       return {
    //         color: 'red',
    //         background: 'pink',
    //         border: '1px solid pink',
    //       }
    //     }
    //   }
    // }
    color: {
      type: Object,
    },
    click: {
      type: Function,
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
    type: {
      type: String,
      default: 'primary',
    },
    loading: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    onclick() {
      this.$emit("click")
    }
  }

}
</script>

<style scoped>
</style>

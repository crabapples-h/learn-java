<template>
  <div>
    welcome
  </div>
</template>

<script>

export default {
  name: "welcome",
  components: {
    data() {
      return {
        title: '管理',
        userInfo: {
          name: ''
        },
        menus: [],
      };
    },
    activated() {
    },
    mounted() {
    },
    methods: {}
  }
}
</script>

<style scoped lang="less">
@import "~@public/color.less";
</style>
<template>
  <div> hello</div>
</template>

<script>
export default {
  name: "welcome",
  components: {},
  data() {
    return {
      userInfo: {},
      menus: [],
    };
  },
  activated() {
    this.userInfo = this.$store.getters.USER_INFO
    console.log(this.userInfo)
  },
  mounted() {
  },
  methods: {}
}
</script>

<style scoped lang="less">
//@import "~@public/color.less";
</style>

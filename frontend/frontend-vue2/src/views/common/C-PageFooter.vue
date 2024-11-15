<template>
  <a-layout-footer :theme="theme">Ant Design ©2018 Created by Ant UED</a-layout-footer>
</template>

<script>

export default {
  name: "C-PageFooter",
  props: {
    theme: {
      type: String,
    },
  },
  data() {
    return {};
  },
  activated() {
  },
  mounted() {
  },
  methods: {}
}
</script>

<style scoped lang="less">
@import "~@public/color.less";

.ant-layout-footer {
  background: @primary-color !important;
  color: #fff;
}

</style>
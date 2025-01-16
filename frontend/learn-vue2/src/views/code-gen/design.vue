<template>
  <div>
    <a-button type="primary">121</a-button>
    <k-form-design title=""/>
  </div>
</template>
<script>
export default {
  name: "Form",
  components: {},
  computed: {},
  data: () => ({}),

  methods: {},
};
</script>
<style lang="less">
@import "~@public/color.less";
/deep/ * {
  @primary-color: red;
  //@primary-color: #2daef6;
}
</style>
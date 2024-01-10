<template>
  <div>
    <a-button @click="show(1)">按钮1</a-button>
    <a-button @click="show(2)">按钮2</a-button>
  </div>
</template>

<script>

export default {
  name: 'Home',
  components: {},
  props: {
    showTime: {
      type: Function
    }
  },
  data() {
    return {}
  },
  mounted() {

  },
  methods: {
    show(type) {
      this.$emit('showTime', type)
    }
  }
}
</script>

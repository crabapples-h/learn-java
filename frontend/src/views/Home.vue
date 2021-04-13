<template>
  <div class="home">
<!--    <HelloWorld msg="Welcome to Your Vue.js App"/>-->
<!--    <a-button type="primary">Button></a-button>-->
    <p>hello,world</p>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'Home',
  components: {
    HelloWorld
  },
  mounted() {
    this.$message.success(
            'hello,world',
            10,
    );
  }
}
</script>

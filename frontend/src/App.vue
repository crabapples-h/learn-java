<template>
  <div id="app">
    <keep-alive>
      <router-view/>
    </keep-alive>
  </div>
</template>
<script>

export default {
  name: 'App',
  components: {
  },
  mounted() {
  },
  methods: {}
}
</script>

<style>
#app {
  margin: 0;
  padding: 0;
}
</style>

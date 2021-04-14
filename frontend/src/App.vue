<template>
  <div id="app">
    <keep-alive>
      <router-view></router-view>
    </keep-alive>
  </div>
</template>

<script>
export default {
  name: 'App',
  mounted() {
    let token = sessionStorage.getItem('crabapples-token')
    let userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
    this.$store.state.token = token
    this.$store.state.userInfo = userInfo
    if (!(token && userInfo)) {
      this.$router.push('/login')
    }
  },
  methods: {}
}
</script>

<style>
#app {
  margin: 0;
  padding: 0;
}

.c-button-red {
  color: red;
}

</style>

<template>
  <div id="app">
    <keep-alive>
      <router-view/>
    </keep-alive>
  </div>
</template>
<script>
import storage from '@/store/storage'

export default {
  name: 'App',
  components: {},
  data() {
    return {}
  },
  created() {
    if (storage.checkLoginStatus()) {
      this.$store.dispatch('MENUS').then(e=>{
        console.log('dispatch-->',e)
      })
      this.$store.dispatch('PERMISSIONS')
      this.$store.dispatch('USER_INFO')
    }

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

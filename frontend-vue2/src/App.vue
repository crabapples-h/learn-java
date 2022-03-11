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
    this.initToken()
  },
  mounted() {
  },
  methods: {
    initToken() {
      const token = storage.getToken()
      if (token) {
        this.$store.dispatch('INIT_TOKEN', token)
        this.$store.dispatch('MENUS')
        this.$store.dispatch('PERMISSIONS')
        this.$store.dispatch('USER_INFO')
        this.$router.push('/manage/index')
      } else {
        console.log('token不存在:', token)
      }
    }
  }
}
</script>

<style>
#app {
  margin: 0;
  padding: 0;
}
</style>

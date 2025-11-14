<template>
  <div>
    <div>认证页面</div>
    <a-button @click="onclickButton"> 点击认证</a-button>
    <p>{{ decodeURIComponent(this.url) }}</p>
    <p>{{ decodeURIComponent(search) }}</p>
    <p>{{ token }}</p>
    <a>{{ tempUrl1 }}
    </a>
  </div>
</template>
<script>
export default {
  name: 'fas',
  data() {
    return {
      url: '',
      search: '',
      token: '',
      tempUrl: '',
      tempUrl1: '',
    }
  },
  methods: {
    onclickButton() {
      console.log(window.location)
      this.url = window.location.href
      this.search = window.location.search
      this.token = this.search.substring(this.search.indexOf("&tok") + 5, this.search.indexOf("&redir"))
      let redir = 'http://www.baidu.com'
      let redir1 = 'http://captive.apple.com/hotspot-detect.html'
      this.tempUrl = `http://192.168.30.1:2050/opennds_auth/
      ?tok=${this.token}&redir=${decodeURIComponent(redir)}&custom=`
      this.tempUrl1 =this.search.replace("?authaction=", "")
      // window.location.href = this.tempUrl1
    }
  }
}

</script>
<style scoped>


</style>

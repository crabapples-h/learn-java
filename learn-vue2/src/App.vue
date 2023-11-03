<template>
  <div id="app">
    <!--    <hello-world/>-->
    <!--    <qr-code/>-->
    <!--    <face-check-image/>-->
    <!--    <face-check-video/>-->
<!--    <pixi-learn/>-->
    <pixi-demo/>
    <!--    <table-demo/>-->
    <!--    <wechat-location/>-->
    <!--    <org-wechat/>-->
    <!--    <face-check-video-file/>-->
    <!--    <div id="nav">-->
    <!--      <router-link to="/">Home</router-link>-->
    <!--      |-->
    <!--      <router-link to="/about">About</router-link>-->
    <!--    </div>-->
    <!--    <router-view/>-->
    <!--    <vue-canvas-poster :widthPixels="0" :painting="painting" @success="success" @fail="fail"/>-->
  </div>
</template>
<script>
import HelloWorld from '@/hello-world.vue'
import QrCode from '@/qrcode.vue'
import FaceCheckImage from '@/face-check-image.vue'
import FaceCheckVideo from '@/face-check-video.vue'
import FaceCheckVideoFile from '@/face-check-image.vue'
import TableDemo from '@/table-demo.vue'
import WechatLocation from '@/WechatLocation.vue'
import OrgWechat from '@/org-wechat.vue'
import PixiLearn from '@/pixi-learn.vue'
import PixiDemo from '@/pixi-demo.vue'

export default {
  name: 'app',
  mixins: [],
  components: {
    HelloWorld,
    OrgWechat, WechatLocation,
    FaceCheckImage, FaceCheckVideo, FaceCheckVideoFile,
    QrCode,
    TableDemo,
    PixiLearn,PixiDemo
  },
  data() {
    return {
      painting: {},
      posterImg: '',
    }
  },
  mounted() {
  },
  methods: {}
}
</script>


<style>
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
}

#nav {
    padding: 30px;
}

#nav a {
    font-weight: bold;
    color: #2c3e50;
}

#nav a.router-link-exact-active {
    color: #42b983;
}
</style>

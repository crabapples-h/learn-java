<template>
  <div id="app">
    <!--    <hello-world />-->
    <!--    <org-wechat />-->
    <!--    <wechat-location />-->
    <!--    <face-check-image />-->
    <!--    <face-check-video />-->
    <!--    <face-check-video-file />-->
    <!--    <qr-code/>-->
    <!--    <qr-code-list/>-->
    <!--    <table-demo />-->
    <!--    <pixi-demo />-->

    <pixi-learn/>
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
import QrCode from '@/views/canvas-poster/qrcode.vue'
import QrCodeList from '@/views/canvas-poster/qrcode-list.vue'
import FaceCheckImage from '@/views/face-api/face-check-image.vue'
import FaceCheckVideo from '@/views/face-api/face-check-video.vue'
import FaceCheckVideoFile from '@/views/face-api/face-check-videoFile.vue'
import TableDemo from '@/table-demo.vue'
import WechatLocation from '@/views/wechat/WechatLocation.vue'
import OrgWechat from '@/views/wechat/org-wechat.vue'
import PixiLearn from '@/views/pixi/pixi-learn.vue'
import PixiDemo from '@/views/pixi/pixi-demo.vue'

export default {
  name: 'app',
  mixins: [],
  components: {
    HelloWorld,// 从url解析参数
    OrgWechat,// 企业微信获取定位
    WechatLocation,//微信获取定位
    FaceCheckImage,//本地上传图片检测人脸
    FaceCheckVideo,//摄像头检测人脸
    FaceCheckVideoFile,//本地视频检测人脸
    QrCode,//生成二维码并下载(使用vue-canvas-poster)
    QrCodeList,//生成二维码列表并下载(使用vue-canvas-poster)
    TableDemo,//antd复杂表头
    PixiDemo,//pixi加载数据并渲染(旋转)
    PixiLearn//pixi学习(多种使用方式)
  },
  // 文档 https://pixijs.huashengweilai.com/guide/start/7.rotation.html#%E6%97%8B%E8%BD%AC
// https://github.com/AlloyTeam/PhyTouch
// https://gsap.com/docs/v3/GSAP/Timeline/
// http://www.greensock.com/gsap-js/
// https://www.gsap.com/archived-docs/
  data() {
    return {
      painting: {},
      posterImg: ''
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
  //text-align: center;
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

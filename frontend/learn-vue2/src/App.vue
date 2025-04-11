<template>
  <div id="app">
<!--    <table-demo></table-demo>-->
<!--    <design></design>-->
    <!--    <a-b></a-b>-->
    <!--    <hello-world />-->
    <!--    <org-wechat />-->
    <!--    <wechat-location />-->
        <face-check-image />
    <!--        <face-check-video />-->
    <!--    <face-check-video-file />-->
    <!--    <qr-code/>-->
    <!--    <qr-code-list/>-->
    <!--    <table-demo />-->
    <!--    <pixi-demo />-->
    <!--    <pixi-learn />-->
    <!--    <pixi-base />-->
    <!--    <pixi-anima/>-->
    <!--    <any-touch-base/>-->
    <!--    <any-touch-advance/>-->
    <!--    <pixi-one-end />-->
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
// import PixiDemo from '@/views/pixi/pixi-demo.vue'
// import PixiLearn from '@/views/pixi/pixi-learn.vue'
import PixiBase from '@/views/pixi/pixi-base.vue'
import PixiAnima from '@/views/pixi/pixi-anima.vue'
import PixiOneEnd from '@/views/pixi/pixi-one-end.vue'
import AnyTouchBase from '@/views/any-touch/anyTouch-base.vue'
import AnyTouchAdvance from '@/views/any-touch/anyTouch-advance.vue'
import Design from '@/views/code-gen/design.vue'
import Render from '@/views/code-gen/render.vue'

export default {
  name: 'app',
  mixins: [],
  components: {
    Design,// 表单设计器
    Render,// 表单渲染器
    HelloWorld,// 从url解析参数
    OrgWechat,// 企业微信获取定位
    WechatLocation,//微信获取定位
    FaceCheckImage,//本地上传图片检测人脸
    FaceCheckVideo,//摄像头检测人脸
    FaceCheckVideoFile,//本地视频检测人脸
    QrCode,//生成二维码并下载(使用vue-canvas-poster)
    QrCodeList,//生成二维码列表并下载(使用vue-canvas-poster)
    TableDemo,//antd复杂表头
    // PixiDemo,//pixi加载数据并渲染(旋转)
    // PixiLearn,//pixi学习(多种使用方式)
    PixiBase,//pixi 基本图像绘制
    PixiAnima,//pixi 纹理和动画
    PixiOneEnd,//pixi一镜到底
    AnyTouchBase,//触摸基础演示
    AnyTouchAdvance,//触摸+时间线演示+pixi动画演示
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


<style lang="less">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  //text-align: center;
  //display: flex;
  //justify-content: center;
  //align-items: center;
  color: #2c3e50;
}

</style>

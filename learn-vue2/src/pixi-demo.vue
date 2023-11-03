<template>
  <div>
    <div ref="pixi"></div>
  </div>
</template>
<script>
// 文档 https://pixijs.huashengweilai.com/guide/start/7.rotation.html#%E6%97%8B%E8%BD%AC
// https://github.com/AlloyTeam/PhyTouch
// https://gsap.com/docs/v3/GSAP/Timeline/
// http://www.greensock.com/gsap-js/
// https://www.gsap.com/archived-docs/
import * as PIXI from 'pixi.js'
import { gsap } from 'gsap'
import { PixiPlugin } from 'gsap/PixiPlugin'

gsap.registerPlugin(PixiPlugin)

export default {
  data() {
    return {
      img: require('@/assets/logo.png'),//21 x 787
      app: null,
      deviceWeight: window.innerWidth,
      deviceHeight: window.innerHeight,
    }
  },
  created() {
    console.log('设备宽度:', window.innerWidth, '设备高度:', window.innerHeight)
  },
  async mounted() {
    await this.createStage()
    this.loadResource()
  },
  methods: {
    // 加载资源
    loadResource() {
      this.app.loader.onProgress.add(this.loadProgress)
      this.app.loader
        .add(this.img)
        .load(this.loadFinishCallBack)
    },
    // 创建舞台
    createStage() {
      this.app = new PIXI.Application({
        width: this.deviceWeight,
        height: this.deviceHeight,
      })
      this.$refs.pixi.appendChild(this.app.view)
    },
    // 资源加载完成回调
    loadFinishCallBack() {
      let a = (this.deviceWeight / 21)
      let b = this.deviceHeight / a
      console.log(a, b)
      let texture = PIXI.utils.TextureCache[this.img]
      // texture.frame = new PIXI.Rectangle(0, 0, 21, b)
      let sprite = new PIXI.Sprite(texture)
      // sprite.scale.x = a
      // sprite.scale.y =17.8
      sprite.position.set((this.deviceWeight - 200) / 2, (this.deviceHeight - 200) / 2)
      // sprite.rotation = 0.5
      sprite.anchor.x = 0.5
      sprite.anchor.y = 0.5
      // sprite.width = 51
      this.app.stage.addChild(sprite)
    },
    // 资源加载进度
    loadProgress(loader, resource) {
      console.log('加载进度:', loader.progress)
    },
  }
}
</script>
<style scoped>
* {
    padding: 0;
    margin: 0
}
</style>

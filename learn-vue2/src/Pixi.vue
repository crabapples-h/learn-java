<template>
  <div>
    <div ref="pixi"></div>
  </div>
</template>
<script>
// 文档 https://pixijs.huashengweilai.com/guide/start/7.rotation.html#%E6%97%8B%E8%BD%AC
import * as PIXI from 'pixi.js'

export default {
  data() {
    return {
      img: require('@/assets/bg.jpg'),
      pixi: null
    }
  },
  mounted() {
    // this.createPixi()
    this.loadImg()
  },
  methods: {
    loadImg() {
      // 创建一个舞台
      let app = new PIXI.Application({
        width: 750,           // default: 800 宽度
        height: 1448,        // default: 600 高度
        antialias: true,     // default: false 反锯齿
        transparent: true,  // default: false 透明度
        resolution: 1,      // default: 1 分辨率
      })
      app.stage.alpha = 1 // 透明度
      let imgs = [require('@/assets/bg.jpg')]
      this.$refs.pixi.appendChild(app.view)
      // console.log(app)
      // console.log(app.loader)
      // 监听加载函数，每加载一个文件就调用一次
      app.loader.onProgress.add((loader, resource) => {
        console.log(loader, resource)
      })
      app.loader
        .add(imgs[0])
        .load(() => {
          // 创建一个精灵
          let sprite = new PIXI.Sprite(app.loader.resources[imgs[0]].texture)
          sprite.position.set(10,10) //位置
          sprite.anchor.set(0,0) //锚点
          app.stage.addChild(sprite)

        })
    },
    createPixi() {
      console.log(this.img)
      let type = 'WebGL'
      if (!PIXI.utils.isWebGLSupported()) {
        type = 'canvas'
      }
      PIXI.utils.sayHello(type)
      this.pixi = new PIXI.Application({
        width: 750,
        height: 1448,
      })
      document.querySelector('#pixi').appendChild(this.pixi.view)
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

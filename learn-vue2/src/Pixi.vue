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
        transparent: false,  // default: false 透明度
        resolution: 1,      // default: 1 分辨率
      })
      app.stage.alpha = 1 // 透明度
      let bgi = [require('@/assets/img.jpg')]
      let images = []
      for (let i = 0; i < 70; i++) {
        images.push(require(`@/assets/images/${i + 1}.jpeg`))
      }
      this.$refs.pixi.appendChild(app.view)
      // console.log(app)
      // console.log(app.loader)
      // 监听加载函数，每加载一个文件就调用一次
      app.loader.onProgress.add((loader, resource) => {
        // console.log(loader, resource)
        // console.log(loader.progress)
      })
      let sprites = []
      app.loader
        .add(bgi)
        // .add(images)
        // .add(images)
        .load(() => {
          // 创建一个精灵
          let sprite = new PIXI.Sprite(app.loader.resources[bgi].texture)

          // sprite.width = app.view.width
          // sprite.height = app.view.height
          sprite.scale.set(30, 30) //缩放
          sprite.position.set(0, 0) //位置
          sprite.anchor.set(0, 0) //锚点
          app.stage.addChild(sprite)
          images.forEach(e => {
            let image = PIXI.Texture.from(e)
            // console.log('11', image)
            let rectangle = new PIXI.Rectangle(0, 0, 360, 480)
            let item = new PIXI.Texture(image, rectangle)
            sprites.push(item)
          })
         let animatedSprite =  new PIXI.AnimatedSprite(sprites)
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

<template>
  <div style="" class="parent" ref="parent">
    <div ref="pixi"></div>
  </div>
</template>


<script>

export default {
  data() {
    return {
      app: null,
      deviceWeight: window.innerWidth,
      deviceHeight: window.innerHeight,
      img: require('@/assets/logo.png')
    }
  },
  created() {
    console.log('weight:', window.innerWidth, 'height:', window.innerHeight)
    console.log('weight:', window.innerWidth / 2, 'height:', window.innerHeight / 2)
  },
  mounted() {
    this.init()
    setTimeout(() => {
      this.drawRect()// 绘制矩形
    }, 100)

  },
  methods: {
    // 初始化
    async init() {
      // 创建PIXI一个对象
      // 文档地址 https://pixijs.download/release/docs/app.ApplicationOptions.html
      const app = await new PIXI.Application()
      // 初始化PIXI对象
      await app.init({
        // width: this.deviceWeight, // 宽
        // height: this.deviceHeight,// 高
        resizeTo: window, //自动调整渲染器大小的元素
        antialias: true // 抗锯齿
        // backgroundColor: '#999' //背景色
      })
      // 将PIXI对象挂载到dom节点上
      this.app = app
      this.$refs.pixi.appendChild(app.canvas)
    },
    // 绘制矩形 x,y,width,height
    async drawRect() {
      const app = this.app
      // const graphics = new PIXI.Texture.from(this.img)
      let img = require('@/assets/logo.png')

      // const graphics = new PIXI.Texture.from('/logo.png')
      let graphics = new PIXI.Texture(img)
      // const graphics = new PIXI.Texture.from(img)
      let sprite = new PIXI.Sprite(graphics)
      sprite.position.set(100, 100)
      // 旋转动画
      // app.ticker.add((time) => {
      //   graphics.rotation += 0.1 * time.deltaTime
      // })

      app.stage.addChild(sprite)
      app.stage.addChild(new PIXI.Graphics()
        // .moveTo(200, 200)
        // .lineTo(600, 200)
        .moveTo(0, 200)
        .lineTo(600, 200)
        .stroke({ width: 1, color: '#f00' })
      )

      app.stage.addChild(new PIXI.Sprite().addChild(
        new PIXI.Graphics()
          .moveTo(200, 0)
          .lineTo(200, 400)
          .stroke({ width: 1, color: '#f00' })
      ))

    }

  }
}
</script>
<style scoped lang="less">
* {
  padding: 0;
  margin: 0
}

.parent {
  //border: 1px solid red;
  width: 100%;
  height: 100vh;
  display: flex;
  //justify-content: flex-start;
  align-items: center;
}
</style>

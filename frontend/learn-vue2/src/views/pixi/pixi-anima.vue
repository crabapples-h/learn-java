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
      this.drawSprite()// 绘制矩形
    }, 100)

  },
  methods: {
    // 初始化
    async init() {
      PIXI.sayHello()
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
    async drawSprite() {
      const app = this.app
      let logo = require('@/assets/logo.png')
      const texture = await PIXI.Assets.load(logo)
      let sprite = new PIXI.Sprite(texture)


      const container = new PIXI.Container()
      container.position.set(200, 200)
      container.pivot.set(100, 100)
      app.ticker.add((time) => {
        container.rotation += 0.01 * time.deltaTime
      })

      container.addChild(sprite)
      app.stage.addChild(container)
      app.stage.addChild(new PIXI.Graphics()
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

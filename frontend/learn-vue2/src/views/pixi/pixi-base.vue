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
      deviceHeight: window.innerHeight

    }
  },
  created() {
    console.log('weight:', window.innerWidth, 'height:', window.innerHeight)
    console.log('weight:', window.innerWidth / 2, 'height:', window.innerHeight / 2)
  },
  mounted() {
    this.init()
    setTimeout(() => {
      // this.drawRect()// 绘制矩形
      // this.drawCircle() // 绘制圆形
      // this.drawEllipse() // 绘制椭圆
      // this.drawLine()// 绘制线条
      // this.drawRoundRect()// 绘制圆角矩形
      // this.drawStar()// 绘制星形
      // this.drawPoly() // 绘制多边形
      this.drawArc() // 绘制圆弧
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
      console.log(this.app)
      const graphics = new PIXI.Graphics()
      graphics.rect(0, 0, 50, 50) // 绘制矩形 x,y,width,height
      graphics.scale.set(2, 2) // 设置缩放比例(位置和宽高全部会缩放)
      graphics.position.set(100, 100) // 设置图形偏移量(x,y)

      // 设置锚点,值为宽度和高度的一半
      // 如果绘制时设置了x,y坐标则需要加上坐标
      // 如果使用position设置偏移量则无需加上坐标
      graphics.pivot.set(50 / 2)
      // graphics.rotation = Math.PI * 4 // 图形旋转角度(弧度表示 0-2PI),基于锚点旋转
      // graphics.angle = 45 // 图形旋转角度(角度度表示 0-360),基于锚点旋转
      graphics.fill('#999', 1) // 填充颜色,透明度
      graphics.stroke({ width: 1, color: '#f00' }) // 设置边框，线条宽度，颜色

      // 旋转动画
      // app.ticker.add((time) => {
      //   graphics.rotation += 0.1 * time.deltaTime
      // })

      app.stage.addChild(graphics)
      app.stage.addChild(new PIXI.Graphics()
        // .moveTo(200, 200)
        // .lineTo(600, 200)
        .moveTo(0, 200)
        .lineTo(600, 200)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Graphics()
        .moveTo(400, 0)
        .lineTo(400, 400)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Sprite().addChild(
        new PIXI.Graphics()
          .moveTo(200, 0)
          .lineTo(200, 400)
          .stroke({ width: 1, color: '#f00' })
      ))

    },
    // 绘制圆形 x,y,半径
    async drawCircle() {
      const app = this.app
      const graphics = new PIXI.Graphics()
      graphics.circle(0, 0, 10) // 绘制圆形 x,y,半径
      graphics.scale.set(2, 2) // 设置缩放比例(位置和宽高全部会缩放)
      graphics.position.set(200, 100) // 设置图形偏移量(x,y)

      // 设置锚点,圆形的锚点默认为圆心(0,0)
      graphics.pivot.set(0)

      // graphics.rotation = Math.PI * 4 // 图形旋转角度(弧度表示 0-2PI),基于锚点旋转
      // graphics.angle = 45 // 图形旋转角度(角度度表示 0-360),基于锚点旋转
      graphics.fill('#999', 1) // 填充颜色,透明度

      console.log(graphics.pivot)
      graphics.stroke({ width: 1, color: '#0f0' }) // 设置边框，线条宽度，颜色

      app.ticker.add((time) => {
        graphics.rotation += 0.05 * time.deltaTime
      })

      app.stage.addChild(graphics)
      app.stage.addChild(new PIXI.Graphics()
        .moveTo(0, 200)
        .lineTo(600, 200)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Graphics()
        .moveTo(400, 0)
        .lineTo(400, 400)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Sprite().addChild(
        new PIXI.Graphics()
          .moveTo(200, 0)
          .lineTo(200, 400)
          .stroke({ width: 1, color: '#f00' })
      ))

    },
    // 绘制椭圆 x,y,width,height
    async drawEllipse() {
      const app = this.app
      const graphics = new PIXI.Graphics()
      graphics.ellipse(0, 0, 50, 30)// 绘制椭圆 x,y,width,height

      graphics.scale.set(2, 2) // 设置缩放比例(位置和宽高全部会缩放)
      graphics.position.set(350, 100) // 设置图形偏移量(x,y)

      // 设置锚点,椭圆的锚点默认为圆心(0,0)
      graphics.pivot.set(0)
      // graphics.rotation = Math.PI * 4 // 图形旋转角度(弧度表示 0-2PI),基于锚点旋转
      // graphics.angle = 45 // 图形旋转角度(角度度表示 0-360),基于锚点旋转
      graphics.fill('#999', 1) // 填充颜色,透明度

      graphics.stroke({ width: 1, color: 'pink' }) // 设置边框，线条宽度，颜色
      // graphics.lineStyle({ width: 1, color: '#f00' }) // 设置边框，线条宽度，颜色

      // 旋转动画
      app.ticker.add((time) => {
        graphics.rotation += 0.05 * time.deltaTime
      })

      app.stage.addChild(graphics)
      app.stage.addChild(new PIXI.Graphics()
        .moveTo(0, 200)
        .lineTo(600, 200)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Graphics()
        .moveTo(400, 0)
        .lineTo(400, 400)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Sprite().addChild(
        new PIXI.Graphics()
          .moveTo(200, 0)
          .lineTo(200, 400)
          .stroke({ width: 1, color: '#f00' })
      ))
    },
    // 绘制线条
    async drawLine() {
      const app = this.app
      const graphics = new PIXI.Graphics()

      graphics.moveTo(-50, 0)// 绘制线条，起点
      graphics.lineTo(50, 0)// 绘制线条，第二个点
      graphics.lineTo(0, 75)// 绘制线条，第三个点
      // graphics.lineTo(-50, 0)// 绘制线条，第四个点(如果要使边框线闭合,需要将最后一个点设置为起点)

      graphics.scale.set(2, 2) // 设置缩放比例(位置和宽高全部会缩放)
      graphics.position.set(550, 100) // 设置图形偏移量(x,y)

      // 设置锚点
      graphics.pivot.set(0, 50 / 2)

      graphics.rotation = Math.PI / 2 // 图形旋转角度(弧度表示 0-2PI),基于锚点旋转
      // graphics.angle = 60 // 图形旋转角度(角度度表示 0-360),基于锚点旋转
      graphics.fill('#999', 1) // 填充颜色,透明度

      graphics.stroke({ width: 1, color: 'BLUE' }) // 设置边框，线条宽度，颜色
      // graphics.lineStyle({ width: 1, color: '#f00' }) // 设置边框，线条宽度，颜色

      app.ticker.add((time) => {
        // graphics.rotation += 1 * time.deltaTime
      })

      app.stage.addChild(graphics)
      app.stage.addChild(new PIXI.Graphics()
        // .moveTo(200, 200)
        // .lineTo(600, 200)
        .moveTo(0, 200)
        .lineTo(600, 200)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Graphics()
        .moveTo(400, 0)
        .lineTo(400, 400)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Sprite().addChild(
        new PIXI.Graphics()
          .moveTo(200, 0)
          .lineTo(200, 400)
          .stroke({ width: 1, color: '#f00' })
      ))

    },
    // 绘制圆角矩形 left,top,width,height,圆角值
    async drawRoundRect() {
      const app = this.app
      const graphics = new PIXI.Graphics()

      graphics.roundRect(0, 0, 100, 100, 30) // 绘制圆角矩形 left,top,width,height,圆角值

      graphics.scale.set(0.5) // 设置缩放比例(位置和宽高全部会缩放)
      graphics.position.set(50, 200) // 设置图形偏移量(x,y)

      // 设置锚点,中心值为宽度和高度的一半
      // 如果绘制时设置了x,y坐标则需要加上坐标
      // 如果使用position设置偏移量则无需加上坐标
      graphics.pivot.set(100 / 2, 100 / 2)

      // graphics.rotation = Math.PI * 4 // 图形旋转角度(弧度表示 0-2PI),基于锚点旋转
      graphics.angle = 45 // 图形旋转角度(角度度表示 0-360),基于锚点旋转
      graphics.fill('#999', 1) // 填充颜色,透明度

      // app.ticker.add((time) => {
      //   graphics.rotation += 0.05 * time.deltaTime
      // })

      app.stage.addChild(graphics)
      app.stage.addChild(new PIXI.Graphics()
        // .moveTo(200, 200)
        // .lineTo(600, 200)
        .moveTo(0, 200)
        .lineTo(600, 200)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Graphics()
        .moveTo(400, 0)
        .lineTo(400, 400)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Sprite().addChild(
        new PIXI.Graphics()
          .moveTo(200, 0)
          .lineTo(200, 400)
          .stroke({ width: 1, color: '#f00' })
      ))

    },
    // 绘制星形 left,top,星形点数,外半径,内半径
    async drawStar() {
      const app = this.app
      const graphics = new PIXI.Graphics()
      graphics.star(0, 0, 5, 100, 40)// 绘制星形 x,y,星形点数,外半径,内半径
      graphics.scale.set(0.5, 0.5) // 设置缩放比例(位置和宽高全部会缩放)
      graphics.position.set(150, 200) // 设置图形偏移量(x,y)
      // 设置锚点
      // graphics.pivot.set(0)
      // graphics.rotation = Math.PI * 4 // 图形旋转角度(弧度表示 0-2PI),基于锚点旋转
      graphics.angle = 45 // 图形旋转角度(角度度表示 0-360),基于锚点旋转
      graphics.fill('#999', 1) // 填充颜色,透明度

      graphics.stroke({ width: 1, color: 'f00' }) // 设置边框，线条宽度，颜色

      app.stage.addChild(graphics)
      app.stage.addChild(new PIXI.Graphics()
        .moveTo(0, 200)
        .lineTo(600, 200)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Graphics()
        .moveTo(400, 0)
        .lineTo(400, 400)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Sprite().addChild(
        new PIXI.Graphics()
          .moveTo(200, 0)
          .lineTo(200, 400)
          .stroke({ width: 1, color: '#f00' })
      ))

    },
    // 绘制多边形[x1,y1,x2,y2...]
    async drawPoly() {
      const app = this.app
      const graphics = new PIXI.Graphics()
      graphics.poly([50, 50, 150, 50, 150, 100, 50, 150])// 绘制多边形[x1,y1,x2,y2...]

      graphics.scale.set(0.5, 0.5) // 设置缩放比例(位置和宽高全部会缩放)
      graphics.position.set(200, 200) // 设置图形偏移量(x,y)

      // 设置锚点
      // graphics.pivot.set(0)
      // graphics.rotation = Math.PI * 4 // 图形旋转角度(弧度表示 0-2PI),基于锚点旋转
      // graphics.angle = 45 // 图形旋转角度(角度度表示 0-360),基于锚点旋转
      graphics.fill('#999', 1) // 填充颜色,透明度

      app.ticker.add((time) => {
        graphics.rotation += 0.05 * time.deltaTime
      })

      app.stage.addChild(graphics)
      app.stage.addChild(new PIXI.Graphics()
        .moveTo(0, 200)
        .lineTo(600, 200)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Graphics()
        .moveTo(400, 0)
        .lineTo(400, 400)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Sprite().addChild(
        new PIXI.Graphics()
          .moveTo(200, 0)
          .lineTo(200, 400)
          .stroke({ width: 1, color: '#f00' })
      ))

    },
    // 绘制圆弧 x,y,半径,起始弧度,结束弧度,是否逆时针方向绘制
    async drawArc() {
      const app = this.app
      const graphics = new PIXI.Graphics()
      graphics.arc(0, 0, 50, 0, 1.5 * Math.PI, false) // 绘制圆弧 x,y,半径,起始弧度,结束弧度,是否逆时针方向绘制

      graphics.scale.set(1, 1) // 设置缩放比例(位置和宽高全部会缩放)
      graphics.position.set(200, 200) // 设置图形偏移量(x,y)

      graphics.stroke({ width: 1, color: '#f00' })

      app.ticker.add((time) => {
        graphics.rotation += 0.05 * time.deltaTime
      })

      app.stage.addChild(graphics)
      app.stage.addChild(new PIXI.Graphics()
        // .moveTo(200, 200)
        // .lineTo(600, 200)
        .moveTo(0, 200)
        .lineTo(600, 200)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Graphics()
        .moveTo(400, 0)
        .lineTo(400, 400)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Sprite().addChild(
        new PIXI.Graphics()
          .moveTo(200, 0)
          .lineTo(200, 400)
          .stroke({ width: 1, color: '#f00' })
      ))

    },
    async draw() {
      const app = this.app
      const graphics = new PIXI.Graphics()
      graphics.circle(0, 0, 50) // 绘制圆形 x,y,半径
      // graphics.rect(0, 0, 50, 50) // 绘制矩形 x,y,width,height
      // graphics.ellipse(0, 0, 50, 30)// 绘制椭圆 x,y,width,height

      // graphics.moveTo(60, 50)// 绘制线条，起点
      // graphics.lineTo(15, 80)// 绘制线条，第二个点
      // graphics.lineTo(100, 100)// 绘制线条，第三个点
      // graphics.lineTo(60, 50)// 绘制线条，第四个点

      // graphics.roundRect(50, 50, 100, 100, 5); // 绘制圆角矩形 left,top,width,height,圆角值
      // graphics.star(100, 100, 5, 100, 40);// 绘制星形 left,top,星形点数,外半径,内半径

      // graphics.poly([50, 50, 150, 50, 100, 100])// 绘制多边形[x1,y1,x2,y2...]

      graphics.scale.set(2, 2) // 设置缩放比例(位置和宽高全部会缩放)
      graphics.position.set(200, 200) // 设置图形偏移量(x,y)

      // 设置锚点,中心值为宽度和高度的一半
      // 如果绘制时设置了x,y坐标则需要加上坐标
      // 如果使用position设置偏移量则无需加上坐标
      graphics.pivot.set(50 * Math.PI / 25)

      // graphics.rotation = Math.PI * 4 // 图形旋转角度(弧度表示 0-2PI),基于锚点旋转
      // graphics.angle = 45 // 图形旋转角度(角度度表示 0-360),基于锚点旋转
      graphics.fill('#999', 1) // 填充颜色,透明度

      // graphics.stroke({ width: 1, color: 'pink' }) // 设置边框，线条宽度，颜色
      // graphics.lineStyle({ width: 1, color: '#f00' }) // 设置边框，线条宽度，颜色

      // graphics.stroke({  color: 0xFFFFFF })

      // graphics.rect(50, 50, 100, 100)
      // graphics.fill(0xde3249)
      // const texture = await PIXI.Assets.load('https://pixijs.com/assets/bunny.png')
      // const bunny = new PIXI.Sprite(texture)
      // bunny.position.set(200, 200)
      // // bunny.angle = 45 // 图形旋转角度(角度度表示 0-360),基于锚点旋转
      // bunny.pivot.set(26/2,37/2)// 设置锚点
      // // bunny.anchor.set(0.5)// 设置锚点
      //
      // app.stage.addChild(bunny)

      // app.ticker.add((time) => {
      //   // Just for fun, let's rotate mr rabbit a little.
      //   // * Delta is 1 if running at 100% performance *
      //   // * Creates frame-independent transformation *
      //   bunny.rotation += 0.1 * time.deltaTime
      // })
      app.ticker.add((time) => {
        graphics.rotation += 0.05 * time.deltaTime
      })

      app.stage.addChild(graphics)
      app.stage.addChild(new PIXI.Graphics()
        // .moveTo(200, 200)
        // .lineTo(600, 200)
        .moveTo(0, 200)
        .lineTo(600, 200)
        .stroke({ width: 1, color: '#f00' })
      )
      app.stage.addChild(new PIXI.Graphics()
        .moveTo(400, 0)
        .lineTo(400, 400)
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

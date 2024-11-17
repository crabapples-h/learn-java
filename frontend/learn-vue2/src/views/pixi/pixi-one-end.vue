<template>
  <div style="" class="parent" ref="parent">
    <div ref="pixi"></div>
  </div>
</template>


<script>
// 文档 https://pixijs.huashengweilai.com/guide/start/7.rotation.html#%E6%97%8B%E8%BD%AC
// https://github.com/AlloyTeam/PhyTouch
// https://gsap.com/docs/v3/GSAP/Timeline/
// http://www.greensock.com/gsap-js/
// https://www.gsap.com/archived-docs/
import { p1Images, p2Images, p3Images, p4Images, p5Images } from '@/views/pixi/resource'
// import * as PIXI from 'pixi.js'
// import { Application } from 'pixi.js'
// import  PIXI from 'pixi.js';
// gsap.registerPlugin(PixiPlugin)

export default {
  data() {
    return {
      img: require('@/assets/bg.jpg'),
      p1Images, p2Images, p3Images, p4Images, p5Images,
      logo: require('@/assets/logo.png'),
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
    this.loadResources()
  },
  methods: {
    // 1. 加载动画素材
    async loadResources() {
      // 创建PIXI一个对象
      const app = await new PIXI.Application()
      // 初始化PIXI对象
      await app.init({
        width: this.deviceWeight,
        height: this.deviceHeight
      })
      // 将PIXI对象挂载到dom节点上
      this.$refs.pixi.appendChild(app.canvas)
      // const a = new PIXI.Graphics().beginFill(0xFF0000).drawRect(0, 0, 100, 100).endFill()
      const graphics = new PIXI.Graphics()
      // graphics.circle(50,50, 50) // 绘制圆形 left,top,半径
      // graphics.rect(50, 50, 50, 50) // 绘制矩形 left,top,宽度,高度
      // graphics.ellipse(100, 100, 50, 30)// 绘制椭圆 left,top,宽度,高度

      // graphics.moveTo(60, 50)// 绘制线条，起点
      // graphics.lineTo(15, 80)// 绘制线条，第二个点
      // graphics.lineTo(100, 100)// 绘制线条，第三个点
      // graphics.lineTo(60, 50)// 绘制线条，第四个点

      // graphics.roundRect(50, 50, 100, 100, 5); // 绘制圆角矩形 left,top,宽度,高度,圆角值
      // graphics.star(100, 100, 5, 100, 40);// 绘制星形 left,top,星形点数,外半径,内半径

      graphics.poly([50, 50, 150, 50, 100, 100])// 绘制多边形[顶点x1,顶点y1,顶点x2,顶点y2...]
      graphics.fill(0x3500fa)

      // graphics.fill('#999', 1) // 填充颜色,透明度
      // graphics.stroke({ width: 4, color: '#f1f1f1' }) // 线条宽度，颜色

      // graphics.stroke({  color: 0xFFFFFF })

      // graphics.rect(50, 50, 100, 100)
      // graphics.fill(0xde3249)

      app.stage.addChild(graphics)
      // app.loader
      //   .add(this.p1Images)
      //   // .add(this.p2Images)
      //   // .add(this.p3Images)
      //   // .add(this.p4Images)
      //   // .add(this.p5Images)
      //   .onProgress.add((loader, resource) => {
      //   console.log('加载进度:', parseFloat(loader.progress.toFixed(2)))
      // })
      //   .load()
      //
      // this.app = app

    }
    // 2. 识别滑动
    // 3. 时间轴动画
    // 4. 序列帧动画
    // 5.
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

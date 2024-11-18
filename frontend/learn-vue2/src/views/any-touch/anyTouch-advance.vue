<template>
  <div style="height: 100vh;width: 100%;overflow: hidden" id="parent" ref="parent">
    <div id="wrapper" ref="wrapper"></div>
  </div>

</template>
<script>
import AnyTouch from "any-touch";
import gsap from "gsap";
import ScrollTrigger from "gsap";

gsap.registerPlugin(ScrollTrigger);

export default {
  name: "AnyTouchBase",
  data() {
    return {
      lastY: 0
    }
  },
  mounted() {
    this.test()
  },
  methods: {
    async test() {
      // https://github.com/any86/any-touch/blob/master/README.CN.md
      // 绘制一个矩形,并使其旋转
      const pixi = new PIXI.Application();
      await pixi.init({
        resizeTo: document.getElementById("parent"), //自动调整渲染器大小的元素
        antialias: true // 抗锯齿
      })
      const container = new PIXI.Container()
      const graphics = new PIXI.Graphics()
      graphics.rect(0, 0, 50, 50)
      graphics.scale.set(2, 2)
      graphics.position.set(100, 100)
      graphics.pivot.set(50 / 2)
      graphics.fill('#999', 1)
      graphics.stroke({width: 1, color: '#f00'})
      container.addChild(graphics)
      pixi.stage.addChild(container)
      this.$refs.wrapper.appendChild(pixi.canvas)
      container.position.set(200, 200)
      container.pivot.set(100, 100)
      pixi.ticker.add((time) => {
        container.rotation += 0.01 * time.deltaTime
      })

      // 创建一条时间线
      // 时间线控制的对象graphics为上面绘制的矩形
      // 时间线周期总长度duration为1
      // 初始化一些数据
      // height为pixi父容器的高度
      // count为圆周长
      // sign为每像素的偏移量
      let height = this.$refs.parent.clientHeight
      let count = Math.PI * 2
      let sign = 1 / count / height

      let t1 = new gsap.timeline()
      t1.to(graphics,
          {
            duration: 1,
            rotation: count
          }
      ).pause()

      // 监听拖拽事件
      // 当拖拽时，记录当前Y轴位
      // 如果当前Y轴位置大于总长度,则将当前位置设置为总长度
      // 如果当前Y轴位置小于0，则将当前位置设置为0
      // 将当前Y轴位置换算为百分比，作为时间线位置
      const touch = new AnyTouch(this.$refs.parent);
      // 拖拽事件
      touch.on('pan', (e) => {
        this.lastY += e.deltaY * -1
        this.lastY = Math.min(this.lastY, height)
        this.lastY = Math.max(this.lastY, e.deltaY * -1)
        let y = Math.max(0, Math.min(height, sign * this.lastY))
        console.log("height:", height, "百分比:", y * count, "实际高度:", this.lastY)
        t1.seek(y * count)
        // wrapper.style.transform = `translate(0px, ${y}px)`;
      });

      // // 按压事件
      // at.on('press', (e) => console.log('按压', e));
      // // 点击事件
      // at.on('tap', (e) => console.log('点击', e));
      // // 快速滑动事件
      // at.on('swipe', (e) => console.log('快速滑动', e));
      // // 缩放事件
      // at.on('pinch', (e) => console.log('缩放', e));
      // // 旋转事件
      // at.on('rotate', (e) => console.log('旋转', e));
    }
  }
}
</script>


<style scoped>

</style>
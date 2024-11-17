<template>
  <div style="" class="parent">
    <div ref="pixi"></div>
  </div>
</template>
<script>
// 文档 https://pixijs.huashengweilai.com/guide/start/7.rotation.html#%E6%97%8B%E8%BD%AC
// https://github.com/AlloyTeam/PhyTouch
// https://gsap.com/docs/v3/GSAP/Timeline/
// http://www.greensock.com/gsap-js/
// https://www.gsap.com/archived-docs/
import { gsap } from 'gsap'
import { PixiPlugin } from 'gsap/PixiPlugin'
import { p1Images, p2Images, p3Images, p4Images, p5Images } from '@/views/pixi/resource'

gsap.registerPlugin(PixiPlugin)

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
    loadResources() {
      // const app = new PIXI.Application({
      //   width: this.deviceWeight,
      //   height: this.deviceHeight
      // })
      // this.$refs.pixi.appendChild(app.view)
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

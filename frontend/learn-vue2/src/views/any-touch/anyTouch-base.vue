<template>
  <div style="height: 90vh;width: 100%;border: 1px solid red;overflow: hidden" id="parent" ref="parent">
    <p style="height: 1vh" id="wrapper">测试内容</p>
  </div>

</template>
<script>
import AnyTouch from "any-touch";

export default {
  name: "AnyTouchBase",
  data() {
    return {
      value: 0
    }
  },
  mounted() {
    this.test()
  },
  methods: {
    test() {
      const wrapper = document.getElementById("wrapper")
      const at = new AnyTouch(this.$refs.parent);
      // 拖拽事件
      at.on('pan', (e) => {
        // console.log('拖拽', e)
        let translate = wrapper.style.transform
        let start = 0
        if (translate) {
          translate = translate.replace('translate', '')
          start = parseInt(translate.split(',')[1].replace('px)', ''))
        }
        console.log('坐标', start, start + Math.abs(e.deltaY))
        document.getElementById("wrapper").style.transform =
            `translate(0px, ${Math.max(0, start + e.deltaY * -1)}px)`;
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
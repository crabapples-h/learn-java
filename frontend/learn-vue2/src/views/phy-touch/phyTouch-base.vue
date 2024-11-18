<template>
  <div style="height: 200vh;width: 100%;display: flex;flex-direction: column" id="parent">
    <p style="height: 1vh" v-for="(item,index) in 200">{{ index }}</p>
  </div>

</template>
<script>
import PhyTouch from "phy-touch";

export default {
  name: "PhyTouchBase",
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
      const _this = this
      const phyTouch = new PhyTouch({
        touch: "#parent",//反馈触摸的dom
        vertical: true,//不必需，默认是true代表监听竖直方向touch
        target: {y: 0}, //运动的对象
        property: "y",  //被运动的属性
        min: -200, //不必需,运动属性的最小值
        max: 200, //不必需,滚动属性的最大值
        // sensitivity: 1,//不必需,触摸区域的灵敏度，默认值为1，可以为负数
        // factor: 1,//不必需,表示触摸位移运动位移与被运动属性映射关系，默认值是1
        // moveFactor: 1,//不必需,表示touchmove位移与被运动属性映射关系，默认值是1
        step: 0.1,//用于校正到step的整数倍
        // bindSelf: false,
        // maxSpeed: 2, //不必需，触摸反馈的最大速度限制
        // value: 0,
        change: function (value) {
          console.log('touch change:', value)
          _this.value = value
          // target.style.transform = "translate(0," + value + "px)"
          // target.style.webkitTransform = "translate(0," + value + "px)"
        },
        touchStart: function (evt, value) {
          console.log('touch start:', value)
        },
        touchMove: function (evt, value) {
          console.log('touch move:', value)
        },
        touchEnd: function (evt, value) {
          console.log('touch end:', value)
        },
        tap: function (evt, value) {
          console.log('tap:', value)
        },
        pressMove: function (evt, value) {
          console.log('press move:', value)
        },
        animationEnd: function (value) {
          console.log('animation end:', value)
        } //运动结束
      })

    }
  }
}
</script>


<style scoped>

</style>
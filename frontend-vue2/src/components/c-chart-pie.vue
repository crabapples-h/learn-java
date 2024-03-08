<!--
  自定义组件，带确认提升的按钮
  color：按钮颜色
  click：点击触发的事件
  title：确认信息提示
  okText：确认按钮文字
  text：按钮文字
  cancelText：取消按钮文字
  size：按钮大小
  icon：提示信息icon
  buttonIcon：按钮icon
  type：按钮类型
  loading：loading状态
-->
<template>
  <div id="c-chart" style="width: 100%;height: 100%"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'c-chart-pie',
  props: {
    title: {
      type: Object,
      require: false,
      default: () => {
        return {
          text: '标题',
          subtext: '副标题',
          left: 'center'
        }
      }
    },
    toolbox: {
      type: Object,
      require: false,
      default: () => {
        return {
          show: true,
          orient: 'vertical'
        }
      }
    },
    tooltip: {
      type: Object,
      require: false,
      default: () => {
        return {
          trigger: 'item'
        }
      }
    },
    legend: {
      type: Object,
      require: false,
      default: () => {
        return {
          orient: 'vertical',
          left: 'left'
        }
      }
    },
    series: {
      type: Object,
      require: false,
      default: () => {
        return {
          name: '统计图名称',
          type: 'pie',
          radius: '50%',
          data: [
            {value: 1048, name: 'Search Engine'},
            {value: 735, name: 'Direct'},
            {value: 580, name: 'Email'},
            {value: 484, name: 'Union Ads'},
            {value: 300, name: 'Video Ads'}
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      }
    },
    aria: {
      type: Object,
      require: false,
      default: () => {
        return {
          enabled: true,
          decal: {
            show: true
          }
        }
      }
    },
    color: {
      type: Object,
      require: false,
      default: () => {
        return []
      }
    },
  },
  updated() {
    this.buildOption()
  },
  watch: {
    // option: {
    //   handler: function (val, oldVal) {
    //     if (this.myChart && val) {
    //       this.myChart.setOption(val);
    //     }
    //   },
    //   deep: true
    // }
  },
  data() {
    return {
      chartDom: null,
      myChart: null,
      option: {}
      // innerText: this.$slots.default[0].text
    }
  },
  created() {
    this.buildOption()
  },
  mounted() {
    this.chartDom = document.getElementById('c-chart');
    this.myChart = echarts.init(this.chartDom);
    this.myChart.setOption(this.option);
    console.log(this.option)
  },
  methods: {
    buildOption() {
      this.option = {
        title: this.title,
        toolbox: this.toolbox,
        tooltip: this.tooltip,
        legend: this.legend,
        series: this.series,
        aria: this.aria,
        color: this.color,
      }
    },
    confirm() {
      this.$emit("click")
    }
  }

}
</script>

<style scoped>
</style>

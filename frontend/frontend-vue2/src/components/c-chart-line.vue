<!--
    自定义组件，echarts折线图
-->
<template>
  <div id="c-chart" style="width: 100%;height: 100%"></div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'c-chart-line',
  props: {
    option: {
      type: Object,
      require: false,
      default: () => {
        return {
          toolbox: {
            show: true,
            orient: 'vertical'
          },
          xAxis: {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              data: [820, 932, 901, 934, 1290, 1330, 1320],
              type: 'line',
              smooth: true
            }
          ]
        }
      }
    },
  },
  watch: {
    option: {
      handler: function (val, oldVal) {
        if (this.myChart && val) {
          this.myChart.setOption(val);
        }
      },
      deep: true
    }
  },
  data() {
    return {
      chartDom: null,
      myChart: null,
      // innerText: this.$slots.default[0].text
    }
  },
  mounted() {
    this.chartDom = document.getElementById('c-chart');
    this.myChart = echarts.init(this.chartDom);
    this.myChart.setOption(this.option);
    console.log(this.option)
  },
  methods: {
    confirm() {
      this.$emit("click")
    }
  }

}
</script>

<style scoped>
</style>

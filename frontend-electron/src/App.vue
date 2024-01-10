<template>
  <div id="app">
    <!--    <div id="nav">-->
    <!--      <a-button type="primary">测试</a-button>-->
    <!--    </div>-->
    <!--    <router-view/>-->
    <time-select @showTime="showTime"/>
    <time1/>
  </div>
</template>
<script>
import TimeSelect from "@/views/TimeSelect.vue";
import Time1 from "@/views/Time1.vue";

export default {
  components: {
    TimeSelect,
    Time1,
  },
  mounted() {
  },
  methods: {
    showTime(type) {
      console.log("showTime", type);
    }
  }
}
</script>

<style lang="less">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>

<template>
  <div>
    <div style="display: flex;">
      <div style="flex: 1">
        <a-button type="primary" @click="axiosConnect(0)" :disabled="disable[0]">使用axios连接</a-button>
        <p v-for="item in messages[0]">{{ item }}</p>
      </div>
      <div style="flex: 1">
        <a-button type="primary" @click="eventSourceConnect(1)" :disabled="disable[1]">使用EventSource连接</a-button>
        <p v-for="item in messages[1]">{{ item }}</p>
      </div>
    </div>
  </div>

</template>

<script>
import {EventSourcePolyfill} from 'event-source-polyfill'
import system from '@/mixins/system'

export default {
  name: "SseExample",
  mixins: [system],
  data() {
    return {
      url: {
        connect: '/api/sse/connect',
        test: '/api/sse/send',
      },
      clientId: '',
      disable: [false, false],
      messages: [[], []],
      timer: null
    }
  },
  methods: {
    // 使用axios连接sse
    axiosConnect(index) {
      this.clientId = Math.floor(Math.random() * 10000000).toString()
      this.$http.get(`${this.url.connect}/${this.clientId}`)
      let url = `${this.url.connect}/${this.clientId}`;
      let connection = new EventSourcePolyfill(url, {
        headers: this.headers
      });
      console.log("url", url, connection);
      connection.addEventListener("open", (event) => {
        // this.disable[index] = true;
        console.log("连接成功", event);
      });
      connection.addEventListener("log", (event) => {
        console.log("收到消息", event.data);
        this.messages[index].push(event.data)
      });
      connection.addEventListener("error", (event) => {
        console.warn("连接断开", event);
        this.disable[index] = false;
        // sse.close();
      });
      this.testSend()
    },
    // 使用EventSource连接sse
    eventSourceConnect(index) {
      this.clientId = Math.floor(Math.random() * 10000000).toString()
      let url = `${this.url.connect}/${this.clientId}`;
      let connection = new EventSourcePolyfill(url, {
        headers: this.headers
      });
      console.log("url", url, connection);
      connection.addEventListener("open", (event) => {
        // this.disable[index] = true;
        console.log("连接成功", event);
      });
      connection.addEventListener("log", (event) => {
        console.log("收到消息", event.data);
        this.messages[index].push(event.data)
      });
      connection.addEventListener("error", (event) => {
        console.warn("连接断开", event);
        this.disable[index] = false;
        // sse.close();
      });
      this.testSend()
    },

    testSend() {
      setTimeout(() => {
        this.$http.post(`${this.url.test}/${this.clientId}`)
      }, 2000)
    }
  }
}

</script>

<style scoped>


</style>

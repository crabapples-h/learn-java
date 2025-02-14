<template>
  <div>
    <a-space>
      <a-button type="primary" @click="connect" :disabled="disable">开始连接</a-button>
    </a-space>
    <p v-for="item in messages">{{ item }}</p>
  </div>

</template>

<script>
import { EventSourcePolyfill } from 'event-source-polyfill'
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
      disable: false,
      messages: [],
      timer: null
    }
  },
  methods: {
    axiosConnect() {
      this.$http.get(`${this.url.connect}/${this.clientId}`)
      this.clientId = Math.floor(Math.random() * 10000000).toString()
      let url = `${this.url.connect}/${this.clientId}`;
      // console.log(NativeEventSource)
      // console.log(EventSourcePolyfill)
      let connection = new EventSourcePolyfill(url, {
        headers: this.headers
      });
      console.log("url", url, connection);

      connection.addEventListener("open", (event) => {
        // this.disable = true;
        console.log("连接成功", event);
      });
      connection.addEventListener("log", (event) => {
        console.log("收到消息", event.data);
        this.messages.push(event.data)
      });
      connection.addEventListener("error", (event) => {
        console.warn("连接断开", event);
        this.disable = false;
        // sse.close();
      });
      this.testSend()
    },
    connect() {
      this.clientId = Math.floor(Math.random() * 10000000).toString()
      let url = `${this.url.connect}/${this.clientId}`;
      // console.log(NativeEventSource)
      // console.log(EventSourcePolyfill)
      let connection = new EventSourcePolyfill(url, {
        headers: this.headers
      });
      console.log("url", url, connection);

      connection.addEventListener("open", (event) => {
        // this.disable = true;
        console.log("连接成功", event);
      });
      connection.addEventListener("log", (event) => {
        console.log("收到消息", event.data);
        this.messages.push(event.data)
      });
      connection.addEventListener("error", (event) => {
        console.warn("连接断开", event);
        this.disable = false;
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

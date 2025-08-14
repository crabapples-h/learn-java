<template>
  <div>
    <a-space>
      <a-button type="primary" @click="connect(url.connectV1)" :disabled="disable">开始连接V1</a-button>
      <a-button type="primary" @click="connect(url.connectV2)" :disabled="disable">开始连接V2</a-button>
    </a-space>
    <p v-for="item in messages">{{ item }}</p>
  </div>

</template>

<script>
import system from '@/mixins/system'

export default {
  name: "WebSocketExample",
  mixins: [system],
  data() {
    return {
      url: {
        connectV1: '/websocket/v1',
        connectV2: '/websocket/v2',
      },
      clientId: '',
      disable: false,
      messages: [],
      timer: null,
      server: null
    }
  },
  methods: {
    connect(prefixUrl) {
      this.clientId = Math.floor(Math.random() * 10000000).toString()
      let url = `${prefixUrl}/${this.clientId}`;
      console.log("url", url);
      const token = this.$store.getters.TOKEN
      // this.server = new WebSocket(url);
      this.server = new WebSocket(url, [this.$store.getters.TOKEN]);
      this.server.onopen = (event) => {
        console.log("连接成功", event)
        this.startTimer()
      }
      this.server.onmessage = (event) => {
        console.log("收到消息", event.data);
        this.messages.push(event.data)
      }
      this.server.onclose = (event) => {
        console.warn("连接关闭", event);
        this.stopTimer()
      }
      this.server.onerror = (event) => {
        console.warn("连接错误", event);
        this.stopTimer()
      }
    },
    startTimer() {
      this.timer = setInterval(() => {
        this.server.send("客户端发送消息:" + new Date().getSeconds())
      }, 3000)
      this.disable = true
    },
    stopTimer() {
      clearInterval(this.timer)
      this.timer = null
      this.server = null
      this.disable = false
    },
  }
}

</script>

<style scoped>


</style>

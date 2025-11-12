<template>
  <div>
    <div>
      <div style="display: flex;">
        <div style="flex: 1">
          <a-button type="primary" @click="connect(url.connectV1,0)" :disabled="disable[0]">开始连接V1</a-button>
          <p v-for="item in messages[0]">{{ item }}</p>
        </div>
        <div style="flex: 1">
          <a-button type="primary" @click="connect(url.connectV2,1)" :disabled="disable[1]">开始连接V2</a-button>
          <p v-for="item in messages[1]">{{ item }}</p>
        </div>
      </div>
    </div>
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
      clientId: ['', ''],
      disable: [false, false],
      messages: [[], []],
      timer: [null, null],
      server: [null, null]
    }
  },
  methods: {
    connect(prefixUrl, index) {
      this.clientId[index] = Math.floor(Math.random() * 10000000).toString()
      let url = `${prefixUrl}/${this.clientId[index]}`;
      console.log("url", url);
      const token = this.$store.getters.TOKEN
      // this.server = new WebSocket(url);
      this.server[index] = new WebSocket(url, [this.$store.getters.TOKEN]);
      this.server[index].onopen = (event) => {
        console.log("连接成功", event)
        this.startTimer(index)
      }
      this.server[index].onmessage = async (event) => {
        console.log("收到消息", event.data);
        this.messages[index].push(event.data)
        // 处理二进制数据
        // let decode = new TextDecoder()
        // this.messages[index].push(decode.decode(await event.data.arrayBuffer()))
      }
      this.server[index].onclose = (event) => {
        console.warn("连接关闭", event);
        this.stopTimer(index)
      }
      this.server[index].onerror = (event) => {
        console.warn("连接错误", event);
        this.stopTimer(index)
      }
    },
    startTimer(index) {
      this.timer[index] = setInterval(() => {
        this.server[index].send("客户端发送消息:当前秒数" + new Date().getSeconds())
      }, 3000)
      this.disable[index] = true
    },
    stopTimer(index) {
      clearInterval(this.timer[index])
      this.timer[index] = null
      this.server[index] = null
      this.disable[index] = false
    },
  }
}

</script>

<style scoped>


</style>

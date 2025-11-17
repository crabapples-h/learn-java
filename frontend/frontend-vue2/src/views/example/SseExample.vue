<template>
  <div>
    <div style="display: flex;">
      <div style="flex: 1">
        <a-button type="primary" @click="eventSourceConnect(0)" :disabled="disable[0]">使用EventSource连接</a-button>
        <p>无法携带请求头,后端需要跳过验证</p>
        <p v-for="item in messages[0]">{{ item }}</p>
      </div>
      <div style="flex: 1">
        <a-button type="primary" @click="polyfillConnect(1)" :disabled="disable[1]">使用EventSourcePolyfill连接
        </a-button>
        <p>可以自定义请求头</p>
        <p v-for="item in messages[1]">{{ item }}</p>
      </div>
      <div style="flex: 1">
        <a-button type="primary" @click="axiosConnect(2)" :disabled="disable[2]">使用axios连接</a-button>
        <p>在onDownloadProgress里监听返回的数据</p>
        <p>每次会返回之前的所有数据加上新的数据,并且每次返回的数据不一定完整,需要自行处理</p>
        <p v-for="item in messages[2]">{{ item }}</p>
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
        connectAuth: '/api/stream/sse/connect/auth',
        connectUnAuth: '/api/stream/sse/connect/unAuth',
        sendAuth: '/api/stream/sse/send/auth',
        sendUnAuth: '/api/stream/sse/send/unAuth',
        connect: '',
        send: '',
      },

      clientId: ['', '', ''],
      disable: [false, false, false],
      messages: [[], [], []],
    }
  },
  mounted() {
    this.url.connect = this.url.connectAuth
    this.url.send = this.url.sendAuth

    // this.url.connect = this.url.connectUnAuth
    // this.url.send = this.url.sendUnAuth
  },
  methods: {
    /*
     * 使用axios连接sse
     * 在onDownloadProgress里监听返回的数据
     * 每次会返回之前的所有数据加上新的数据,并且每次返回的数据不一定完整,需要自行处理
     */
    axiosConnect(index) {
      this.clientId[index] = Math.floor(Math.random() * 10000000).toString()
      let url = `${this.url.connect}/${this.clientId[index]}`;
      this.$http.get(url, {
        success: (result) => {
          console.log("success", result)
        },
        onDownloadProgress: ({event}) => {
          console.log("onDownloadProgress", event.currentTarget.response)
          this.messages[index].push(event.currentTarget.responseText)
        }
      }).then(result => {
        console.log("url", url, result);
      }).finally(() => {
      })
      this.testSend(index)
    },
    /*
      * 使用eventSourcePolyfill连接sse
      * 无法携带请求头,后端需要跳过验证
     */
    eventSourceConnect(index) {
      this.clientId[index] = Math.floor(Math.random() * 10000000).toString()
      let url = `${this.url.connect}/${this.clientId[index]}`;
      let connection = new EventSource(url, {
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
      this.testSend(index)
    },
    /*
     * 使用EventSourcePolyfill连接sse
     * 可以自定义请求头
     */
    polyfillConnect(index) {
      this.clientId[index] = Math.floor(Math.random() * 10000000).toString()
      let url = `${this.url.connect}/${this.clientId[index]}`;
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
      this.testSend(index)
    },

    testSend(index) {
      setTimeout(() => {
        this.$http.post(`${this.url.send}/${this.clientId[index]}`)
      }, 2000)
    }
  }
}

</script>

<style scoped>


</style>

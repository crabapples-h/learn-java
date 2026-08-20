<template>
  <div style="display: flex">
    <div style="flex: 1">
      <a-button type="primary" @click="connect(url.connectV1, 0)" :disabled="disable[0]">开始连接V1</a-button>
      <p v-for="item in messages[0]" :key="item">{{ item }}</p>
    </div>
    <div style="flex: 1">
      <a-button type="primary" @click="connect(url.connectV2, 1)" :disabled="disable[1]">开始连接V2</a-button>
      <p v-for="item in messages[1]" :key="item">{{ item }}</p>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useUserStore } from '@/store/modules/user'

const userStore = useUserStore()

const url = {
  connectV1: '/api/stream/websocket/v1',
  connectV2: '/api/stream/websocket/v2',
}

const clientId = ['', '']
const disable = ref([false, false])
const messages = reactive([[], []])
const timer = [null, null]
const server = [null, null]

const connect = (prefixUrl, index) => {
  clientId[index] = Math.floor(Math.random() * 10000000).toString()
  const u = `${prefixUrl}/${clientId[index]}`
  server[index] = new WebSocket(u, [userStore.TOKEN])
  server[index].onopen = () => {
    startTimer(index)
  }
  server[index].onmessage = event => {
    messages[index].push(event.data)
  }
  server[index].onclose = () => {
    stopTimer(index)
  }
  server[index].onerror = () => {
    stopTimer(index)
  }
}

const startTimer = index => {
  timer[index] = setInterval(() => {
    const msg = '客户端发送消息:当前秒数' + new Date().getSeconds()
    server[index]?.send(msg)
  }, 3000)
  disable.value[index] = true
}

const stopTimer = index => {
  clearInterval(timer[index])
  timer[index] = null
  server[index] = null
  disable.value[index] = false
}
</script>

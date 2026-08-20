<template>
  <div style="display: flex">
    <div style="flex: 1">
      <a-button type="primary" @click="eventSourceConnect(0)" :disabled="disable[0]">使用EventSource连接</a-button>
      <p>无法携带请求头,后端需要跳过验证</p>
      <p v-for="item in messages[0]" :key="item">{{ item }}</p>
    </div>
    <div style="flex: 1">
      <a-button type="primary" @click="polyfillConnect(1)" :disabled="disable[1]">使用fetchEventSource连接</a-button>
      <p>可以自定义请求头</p>
      <p v-for="item in messages[1]" :key="item">{{ item }}</p>
    </div>
    <div style="flex: 1">
      <a-button type="primary" @click="axiosConnect(2)" :disabled="disable[2]">使用axios连接</a-button>
      <p>在onDownloadProgress里监听返回的数据</p>
      <p>每次会返回之前的所有数据加上新的数据,并且每次返回的数据不一定完整,需要自行处理</p>
      <p v-for="item in messages[2]" :key="item">{{ item }}</p>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { fetchEventSource } from '@microsoft/fetch-event-source'
import request from '@/utils/request'
import storage from '@/store/storage'

const url = {
  connectAuth: '/api/stream/sse/connect/auth',
  connectUnAuth: '/api/stream/sse/connect/unAuth',
  sendAuth: '/api/stream/sse/send/auth',
  sendUnAuth: '/api/stream/sse/send/unAuth',
  connect: '/api/stream/sse/connect/auth',
  send: '/api/stream/sse/send/auth',
}

const clientId = ['', '', '']
const disable = ref([false, false, false])
const messages = reactive([[], [], []])
const controllers = [null, null, null]

const headers = () => ({ 'crabapples-token': storage.getToken() })

const randomId = () => Math.floor(Math.random() * 10000000).toString()

const testSend = index => {
  setTimeout(() => {
    request.post(`${url.send}/${clientId[index]}`)
  }, 2000)
}

// 使用axios连接sse：在onDownloadProgress里监听返回的数据
const axiosConnect = index => {
  clientId[index] = randomId()
  const u = `${url.connect}/${clientId[index]}`
  request.get(u, {
    onDownloadProgress: ({ event }) => {
      messages[index].push(event.currentTarget.responseText)
    },
  }).finally(() => {})
  testSend(index)
}

// 使用原生EventSource连接：无法携带请求头,后端需要跳过验证
const eventSourceConnect = index => {
  clientId[index] = randomId()
  const u = `${url.connect}/${clientId[index]}`
  const connection = new EventSource(u, { headers: headers() })
  connection.addEventListener('open', () => {})
  connection.addEventListener('log', event => {
    messages[index].push(event.data)
  })
  connection.addEventListener('error', () => {
    disable.value[index] = false
  })
  testSend(index)
}

// 使用fetchEventSource连接：可以自定义请求头（fetch 实现，@microsoft/fetch-event-source）
const polyfillConnect = index => {
  clientId[index] = randomId()
  const u = `${url.connect}/${clientId[index]}`
  const controller = new AbortController()
  controllers[index] = controller
  fetchEventSource(u, {
    headers: headers(),
    signal: controller.signal,
    onmessage: event => {
      messages[index].push(event.data)
    },
    onerror: err => {
      console.warn('SSE连接错误', err)
      disable.value[index] = false
      controller.abort()
      // 不抛出 → 停止自动重连
    },
  })
  testSend(index)
}
</script>

<template>
  <div style="display: flex; flex-wrap: wrap; gap: 16px">
    <div style="flex: 1; min-width: 280px">
      <a-button type="primary" @click="eventSourceConnect(0)" :disabled="disable[0]">使用EventSource连接</a-button>
      <p>无法携带请求头,后端需要跳过验证(走 unAuth)</p>
      <p v-for="item in messages[0]" :key="item">{{ item }}</p>
    </div>
    <div style="flex: 1; min-width: 280px">
      <a-button type="primary" @click="polyfillConnect(1)" :disabled="disable[1]">使用fetchEventSource连接</a-button>
      <p>微软官方 @microsoft/fetch-event-source,可自定义请求头</p>
      <p v-for="item in messages[1]" :key="item">{{ item }}</p>
    </div>
    <div style="flex: 1; min-width: 280px">
      <a-button type="primary" @click="sseJsConnect(2)" :disabled="disable[2]">使用sse.js连接</a-button>
      <p>sse.js 库(XHR实现),可自定义请求头</p>
      <p v-for="item in messages[2]" :key="item">{{ item }}</p>
    </div>
    <div style="flex: 1; min-width: 280px">
      <a-button type="primary" @click="axiosConnect(3)" :disabled="disable[3]">使用axios连接</a-button>
      <p>在onDownloadProgress里监听返回的数据</p>
      <p>每次会返回之前的所有数据加上新的数据,并且每次返回的数据不一定完整,需要自行处理</p>
      <p v-for="item in messages[3]" :key="item">{{ item }}</p>
    </div>
    <div style="flex: 1; min-width: 280px">
      <a-button type="primary" @click="webfluxConnect(4)" :disabled="disable[4]">WebFlux Flux&lt;ServerSentEvent&gt;</a-button>
      <p>后端响应式栈(Netty) Flux 流,定时推送 10 条</p>
      <p v-for="item in messages[4]" :key="item">{{ item }}</p>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { fetchEventSource } from '@microsoft/fetch-event-source'
import { SSE } from 'sse.js'
import request from '@/utils/request'
import storage from '@/store/storage'

const url = {
  connectAuth: '/api/stream/sse/connect/auth',
  connectUnAuth: '/api/stream/sse/connect/unAuth',
  sendAuth: '/api/stream/sse/send/auth',
  sendUnAuth: '/api/stream/sse/send/unAuth',
  // 原生 EventSource 无法自定义请求头，走无需认证的 unAuth 接口
  connect: '/api/stream/sse/connect/unAuth',
  send: '/api/stream/sse/send/unAuth',
  // WebFlux 响应式 SSE（webflux-sse-app，Netty 19097，经网关 /api/webflux-sse/** 路由）
  webfluxTimer: '/api/webflux-sse/sse/connect/timer',
}

const clientId = ['', '', '', '', '']
const disable = ref([false, false, false, false, false])
const messages = reactive([[], [], [], [], []])
const controllers = [null, null, null, null, null]

const headers = () => ({ 'crabapples-token': storage.getToken() })

const randomId = () => Math.floor(Math.random() * 10000000).toString()

const testSend = (index, auth = true) => {
  setTimeout(() => {
    request.post(`${auth ? url.sendAuth : url.send}/${clientId[index]}`)
  }, 2000)
}

// 使用原生EventSource连接：无法携带请求头,后端需要跳过验证（走 unAuth 接口）
const eventSourceConnect = index => {
  clientId[index] = randomId()
  const u = `${url.connect}/${clientId[index]}`
  const connection = new EventSource(u)
  connection.addEventListener('open', () => {})
  connection.addEventListener('log', event => {
    messages[index].push(event.data)
  })
  connection.addEventListener('error', () => {
    disable.value[index] = false
  })
  testSend(index, false)
}

// 使用fetchEventSource连接：可以自定义请求头（fetch 实现，@microsoft/fetch-event-source，走 auth 接口）
const polyfillConnect = index => {
  clientId[index] = randomId()
  const u = `${url.connectAuth}/${clientId[index]}`
  const controller = new AbortController()
  controllers[index] = controller
  fetchEventSource(u, {
    headers: headers(),
    signal: controller.signal,
    onmessage: event => {
      messages[index].push(event.data)
    },
    onerror: err => {
      console.warn('fetchEventSource连接错误', err)
      disable.value[index] = false
      controller.abort()
      // 不抛出 → 停止自动重连
    },
  })
  testSend(index, true)
}

// 使用sse.js连接：可以自定义请求头（XHR 实现，sse.js，走 auth 接口）
const sseJsConnect = index => {
  clientId[index] = randomId()
  const u = `${url.connectAuth}/${clientId[index]}`
  const source = new SSE(u, {
    headers: headers(),
    autoReconnect: false,
  })
  controllers[index] = source
  source.addEventListener('log', event => {
    messages[index].push(event.data)
  })
  source.addEventListener('error', () => {
    disable.value[index] = false
  })
  source.stream()
  testSend(index, true)
}

// 使用axios连接sse：在onDownloadProgress里监听返回的数据（走 auth 接口，axios 可带 token 头）
const axiosConnect = index => {
  clientId[index] = randomId()
  const u = `${url.connectAuth}/${clientId[index]}`
  request.get(u, {
    onDownloadProgress: ({ event }) => {
      messages[index].push(event.currentTarget.responseText)
    },
  }).finally(() => {})
  testSend(index, true)
}

// 连接 WebFlux 响应式 SSE（webflux-sse-app, Netty 19097）：后端 Flux.interval 定时推送 10 条
const webfluxConnect = index => {
  const u = `${url.webfluxTimer}/${randomId()}`
  const connection = new EventSource(u)
  connection.addEventListener('open', () => {})
  connection.addEventListener('log', event => {
    messages[index].push(event.data)
  })
  connection.addEventListener('error', () => {
    disable.value[index] = false
  })
}
</script>

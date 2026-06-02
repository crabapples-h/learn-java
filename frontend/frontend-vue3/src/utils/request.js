import axios from 'axios'
import { notification } from 'ant-design-vue'
import router from '@/router'
import storage from '@/store/storage'
import settings from '../../settings'

const instance = axios.create({
  timeout: 1000 * 120,
})

instance.interceptors.request.use(
  config => {
    config.headers['crabapples-token'] = storage.getToken()

    if (/get/i.test(config.method)) {
      config.params = config.params || {}
      config.params.temp = Date.now()
    }

    return config
  },
  error => Promise.reject(error),
)

instance.interceptors.response.use(
  response => {
    const data = response.data

    if (data?.status === 401) {
      storage.logout()
      router.push('/login')
    }

    if (data?.status && data.status !== 200) {
      notification.warning({
        key: data.message,
        message: '接口出现异常',
        description: data.message,
      })
    }

    return response.status === 200 ? Promise.resolve(data) : Promise.reject(data)
  },
  error => {
    const status = error.response?.status || '未知'
    notification.error({
      key: error.code,
      message: error.code || '请求异常',
      description: `服务器异常：${status}`,
    })
    return Promise.reject(error.response || error)
  },
)

export { settings }
export default instance

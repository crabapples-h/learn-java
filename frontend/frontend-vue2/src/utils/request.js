/**
 * TODO http工具
 *
 * @author Mr.He
 * 2021/4/15 14:41
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
import axios from 'axios'
import router from '@/router'
import notification from 'ant-design-vue/es/notification'
import storage from '@/store/storage'

import settings from '../../settings'

const isCrypt = settings.isCrypt
const instance = axios.create({timeout: 1000 * 120})

let isLoggingOut = false

function doLogout(message) {
    if (isLoggingOut) return
    isLoggingOut = true
    storage.logout()
    notification.warning({
        message: '登录已过期',
        description: message || '请重新登录',
    })
    setTimeout(() => {
        router.push('/login').finally(() => {
            isLoggingOut = false
        })
    }, 1500)
}

instance.interceptors.request.use(config => {
        config.headers['crabapples-token'] = storage.getToken()
        config.data = isCrypt && config.data ? encrypt(JSON.stringify(config.data)) : config.data
        if (/get/i.test(config.method)) {
            config.params = config.params || {}
            config.params.temp = Date.parse(new Date()) / 1000
        }
        return config
    },
    error => Promise.error(error)
)

// 响应拦截器
instance.interceptors.response.use(response => {
        let data = response.data
        if (response.data.status === 401) {
            doLogout('业务状态码401')
            return Promise.reject(data)
        }
        // 服务器状态码不是200
        if (response.data.status !== 200) {
            if (response.data) {
                console.log("接口出现异常", response)
                notification.warn({
                    key: response.data.message,
                    message: "接口出现异常",
                    description: response.data.message,
                })
            }
        }
        return response.status === 200 ? Promise.resolve(data) : Promise.reject(data)
    },
    // HTTP状态码不是200的情况
    error => {
        console.error('响应拦截:[error]--->', error)

        // error.response 可能为 undefined（网络超时、断网等）
        if (!error.response) {
            if (error.code === 'ECONNABORTED') {
                notification.error({message: '请求超时', description: '服务器响应超时，请稍后重试'})
            } else {
                notification.error({message: '网络异常', description: '无法连接到服务器，请检查网络'})
            }
            return Promise.reject(error)
        }

        // HTTP 401 未授权
        if (error.response.status === 401) {
            doLogout('未获授权')
            return Promise.reject(error.response)
        }

        notification.error({
            key: error.code,
            message: error.code,
            description: `服务器异常：${error.response.status}`,
        })
        return Promise.reject(error.response)
    }
)

export default instance

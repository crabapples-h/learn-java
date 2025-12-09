import axios from 'axios'
import notification from 'ant-design-vue/es/notification'
import {useTokenStoreApi, useTokenStoreFunc} from '@/store/storage'
import {useRouter} from "vue-router";

const instance = axios.create({timeout: 1000 * 12});

instance.interceptors.request.use(
    config => {
        // 登录流程控制中，根据本地是否存在token判断用户的登录情况
        // 但是即使token存在，也有可能token是过期的，所以在每次的请求头中携带token
        // 后台根据携带的token判断用户的登录情况，并返回给我们对应的状态码
        // 而后我们可以在响应拦截器中，根据状态码进行一些统一的操作。
        const token = useTokenStoreApi().token;
        if (token === null) {
        }
        config.headers['crabapples-token'] = token;
        if (/get/i.test(config.method)) {
            config.params = config.params || {}
            config.params.temp = Date.parse((new Date().toDateString())) / 1000
        }
        return config;
    },
    error => Promise.reject(error)
);

// 响应拦截器
instance.interceptors.response.use(
    response => {
        let data = response.data
        if (response.data.status === 401) {
            useRouter().push('/login').then(r => {
            })
        }
        return response.status === 200 ? Promise.resolve(data) : Promise.reject(data)
    },
    // 服务器状态码不是200的情况
    error => {
        notification.error({message: '服务器异常'});
        return Promise.reject(error.response)
    }
);

export default instance

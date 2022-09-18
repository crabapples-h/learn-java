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
import router from '@/router';
import notification from 'ant-design-vue/es/notification'
import storage from '@/store/storage'

import settings from '../../settings'

const isCrypt = settings.isCrypt;
const isDebug = settings.isDebug;
const instance = axios.create({timeout: 1000 * 12});

instance.interceptors.request.use(
    config => {
        config.headers['crabapples-token'] = storage.getToken();
        config.data = isCrypt && config.data ? encrypt(JSON.stringify(config.data)) : config.data
        if (/get/i.test(config.method)) {
            config.params = config.params || {}
            config.params.temp = Date.parse(new Date()) / 1000
        }
        return config;
    },
    error => Promise.error(error)
);

// 响应拦截器
instance.interceptors.response.use(
    response => {
        if (isDebug)
            console.info('响应拦截:[success]--->', response);
        let data = response.data
        if (response.data.status === 401) {
            notification.error({message: response.data.message});
            storage.logout()
            router.push('/login')
        }
        return response.status === 200 ? Promise.resolve(data) : Promise.reject(data)
    },
    // 服务器状态码不是200的情况
    error => {
        if (!isDebug)
            console.error('响应拦截:[error]--->', error);
        notification.error({message: '服务器异常'});
        return Promise.reject(error.response)
    }
);

export default instance

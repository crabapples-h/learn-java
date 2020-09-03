import axios from 'axios'
import router from '@/router';
import message from 'ant-design-vue/es/message'
import notification from 'ant-design-vue/es/notification'

const instance = axios.create({timeout: 1000 * 12});
instance.interceptors.request.use(
    config => {
        // 登录流程控制中，根据本地是否存在token判断用户的登录情况
        // 但是即使token存在，也有可能token是过期的，所以在每次的请求头中携带token
        // 后台根据携带的token判断用户的登录情况，并返回给我们对应的状态码
        // 而后我们可以在响应拦截器中，根据状态码进行一些统一的操作。
        const token = sessionStorage.getItem('crabapples-token');
        if (token === null) {
            router.push('/login')
        }
        config.headers['crabapples-token'] = token;
        // console.log('请求拦截')
        // console.log(config)
        return config;
    },
    error => Promise.error(error)
);

// 响应拦截器
instance.interceptors.response.use(
    response => {
        console.warn('响应拦截->success', response);
        if (response.data.status === 401) {
            router.push('/login')
        }
        return response.status === 200 ? Promise.resolve(response.data) : Promise.reject(response.data)
    },
    // 服务器状态码不是200的情况
    error => {
        console.warn('响应拦截->error', error);
        notification.error({message: '服务器异常'});
        return Promise.reject(error.response)
    }
);

export function login(data) {
    instance.post('/api/loginCheck', data).then(result => {
        if (result.status !== 200) {
            message.error(result.message);
            return
        }
        message.success(result.message);
        sessionStorage.setItem('crabapples-token', result.data);
        router.push('/')
    }).catch(exception => {
        notification.error(exception)
    })
}

export function logout(data) {
    instance.post('/api/logout', data).then(response => {
        const result = response.data;
        console.log('通过api获取到的数据:', result);
        sessionStorage.removeItem('crabapples-token')
    }).catch(exception => {
        notification.error(exception)
    })
}

export function get(url) {
    return instance.get(url).then(response => {
        return Promise.resolve(response);
    }).catch(exception => {
        return Promise.reject(exception);
    })
}

export function post(url, data) {
    return instance.post(url, data).then(response => {
        return Promise.resolve(response);
    }).catch(exception => {
        console.error('系统错误', exception);
        return Promise.reject(exception);
    })
}


export default {instance, login, logout, get, post}

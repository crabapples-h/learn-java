const request = axios.create({timeout: 3000})
request.interceptors.request.use(
    config => {
        config.headers['crabapples-token'] = sessionStorage.getItem('token');
        if (/get/i.test(config.method)) {
            config.params = config.params || {}
            config.params.temp = Date.parse(new Date()) / 1000
        }
        return config;
    },
    error => Promise.error(error)
);
request.interceptors.response.use(
    response => {
        console.info('api返回的数据-->', response.data)
        if (response.status === 200) {
            if (response.data.status === 401) {
                setTimeout(() => {
                    window.location.href = '/server/login'
                }, 3000)
                return
            }
        }
        return response
    },
    error => {
        return Promise.reject(error)
    }
)
axios = request
const headers = {
    'crabapples-token': sessionStorage.getItem('token'),
}

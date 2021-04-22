import instance from "@/utils/AxiosUtils";

const commonApi = {
    login(data) {
        return instance({
            url: '/api/sys/login',
            method: 'post',
            data: data
        })
    },
    getUserInfo() {
        return instance({
            url: '/api/user/info',
            method: 'get',
        })
    },
    logout() {
        sessionStorage.clear()
        localStorage.clear()
        return instance({
            url: '/api/sys/logout',
            method: 'get',
        })
    }
}
export default commonApi
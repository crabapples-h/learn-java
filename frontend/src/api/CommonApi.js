import instance from "@/utils/AxiosUtils";

const commonApi = {
    login(data) {
        return instance({
            url: '/api/sys/login',
            method: 'post',
            data: data
        })
    },
    logout() {
        sessionStorage.clear()
        localStorage.clear()
        return instance({
            url: '/api/sys/logout',
            method: 'get',
        })
    },
    getUserInfo() {
        return instance({
            url: '/api/user/info',
            method: 'get',
        })
    },
    getUserMenus() {
        return instance({
            url: '/api/sys/user/menus',
            method: 'get',
        })
    },
    getUserPermissions() {
        return instance({
            url: '/api/sys/user/permissions',
            method: 'get',
        })
    },
}
export default commonApi
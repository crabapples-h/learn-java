import instance from "@/utils/AxiosUtils";

export function login(data) {
    return instance({
        url: '/api/login',
        method: 'post',
        data: data
    })
}


export function logout() {
    return instance({
        url: '/api/logout',
        method: 'get',
    })
}
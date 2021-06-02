/**
 * TODO sessionStorage工具
 *
 * @author Mr.He
 * 2021/4/22 18:47
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */

function setToken(token) {
    sessionStorage.setItem('token', token)
}

function getToken() {
    return sessionStorage.getItem('token')
}

function setUserInfo(userInfo) {
    userInfo = JSON.stringify(userInfo)
    sessionStorage.setItem('userInfo', userInfo)
}

function getUserInfo() {
    let userInfo = sessionStorage.getItem('userInfo')
    return JSON.parse(userInfo)
}

function setRouterMap(routerMap) {
    routerMap = JSON.stringify(routerMap)
    console.log('setRouterMap','routerMap')
    sessionStorage.setItem('routerMap', routerMap)
}

function getRouterMap() {
    let routerMap = sessionStorage.getItem('routerMap')
    return JSON.parse(routerMap)
}

function setPermissions(permissions) {
    permissions = JSON.stringify(permissions)
    sessionStorage.setItem('permissions', permissions)
}

function getPermissions() {
    let permissions = sessionStorage.getItem('permissions')
    return JSON.parse(permissions)
}

export {
    setToken,
    getToken,
    setUserInfo,
    getUserInfo,
    setRouterMap,
    getRouterMap,
    setPermissions,
    getPermissions,
}

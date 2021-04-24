import instance from "@/utils/AxiosUtils";
import {setPermissions, setRouterMap} from "@/utils/sessionUtils"
import {SysApis} from "@/api/Apis"
const commonApi = {
    login(data) {
        return instance({
            url: SysApis.login,
            method: 'post',
            data: data
        })
    },
    logout() {
        sessionStorage.clear()
        localStorage.clear()
        return instance({
            url: SysApis.logout,
            method: 'get',
        })
    },
    getUserInfo() {
        return instance({
            url: SysApis.userInfo,
            method: 'get',
        })
    },
    getUserMenus() {
        return instance({
            url: SysApis.menus,
            method: 'get',
        })
    },
    getUserPermissions() {
        return instance({
            url: SysApis.permissions,
            method: 'get',
        })
    },
    refreshSysData() {
        this.getUserPermissions().then(res => {
            if (res.status === 200)
                setPermissions(res.data)
        })
        this.getUserMenus().then(res => {
            if (res.status === 200)
                setRouterMap(res.data)
        })
    }
}
export default commonApi
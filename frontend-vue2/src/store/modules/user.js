/**
 * TODO
 *
 * @author Mr.He
 * 2021/8/11 1:15
 * e-mail hequan3@asiainfo.com
 */
import storage from "@/store/storage";
import commonApi from "@/api/CommonApi";
import router, {initRouter} from "@/router";
import message from "ant-design-vue/es/message"
import notification from 'ant-design-vue/es/notification'

const user = {
    state: {
        tokenKey: '',
        token: '',
        info: '',
    },
    //commit调用mutations方法，实时更新
    mutations: {
        TOKEN: (state, payload) => {
            state.token = payload
            storage.setToken(payload)
        },
        USER_INFO: (state, payload) => {
            state.info = payload
            storage.setUserInfo(payload)
        },
        INIT_ROUTER: (state, payload) => {
            initRouter()
        },
    },
    // dispatch调用actions方法，非实时更新，可以包含异步操作
    actions: {
        LOGIN(object, data) {
            return commonApi.login(data).then(({status, data, message}) => {
                if (status !== 200) {
                    notification.error({message: message});
                    return Promise.reject(message)
                }
                object.commit('TOKEN', data)
                return Promise.resolve(message)
            }).catch(function (error) {
                console.error('出现错误:', error);
                return Promise.reject(message)
            })
        },
        INIT_TOKEN(object, data) {
            object.commit('TOKEN', data)
        },
        USER_INFO(object, data) {
            router.replace('/loading')
            commonApi.getUserInfo().then(({status, data, message}) => {
                if (status !== 200) {
                    this.$message.error(message);
                    return;
                }
                if (data !== null) {
                    object.commit('USER_INFO', data)
                }
            }).catch(function (error) {
                console.error('出现错误:', error);
            })
        },
    }
}
export default user

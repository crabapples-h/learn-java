/**
 * TODO
 *
 * @author Mr.He
 * 2021/8/11 1:15
 * e-mail hequan3@asiainfo.com
 */
import storage from "@/store/storage";
import commonApi from "@/api/CommonApi";
import router from "@/router";
import message from "ant-design-vue/es/message"
import notification from 'ant-design-vue/es/notification'

const user = {
    state: {
        tokenKey: '',
        token: '',
        info: '',
    },
    mutations: {
        TOKEN: (state, payload) => {
            state.token = payload
            storage.setToken(payload)
        },
        USER_INFO: (state, payload) => {
            state.info = payload
            storage.setUserInfo(payload)
        },
    },
    actions: {
        LOGIN(object, data) {
            return commonApi.login(data).then(({status, data, message}) => {
                if (status !== 200) {
                    notification.error({message: message});
                    return Promise.reject(message)
                }
                object.commit('TOKEN', data)
                router.replace('/manage/index')
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

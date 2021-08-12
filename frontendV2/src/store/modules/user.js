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
            commonApi.login(data).then(({status, data, message}) => {
                if (status !== 200) {
                    this.$message.error(message);
                    return
                }
                object.commit('TOKEN', data)
                storage.login()
                router.replace('/manage/index')
            }).catch(function (error) {
                console.error('出现错误:', error);
            })
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

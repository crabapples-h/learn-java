import Vue from 'vue'
import Vuex from "vuex";
import getters from "@/store/getters";
import storage from "@/store/storage";

Vue.use(Vuex)
const store = new Vuex.Store({
    state: {
        user: {
            tokenKey: '',
            token: '',
            info: '',
            menus: '',
            permissions: '',
            routers: '',
        },
        sys: {
            isDebug: true
        }
    },
    mutations: {
        setToken: (state, payload) => {
            state.user.token = payload
            storage.setToken(payload)
        },
        setUserInfo: (state, payload) => {
            state.user.info = payload
            storage.setUserInfo(payload)
        },
        setUserMenus: (state, payload) => {
            state.user.menus = payload
            storage.setUserMenus(payload)
        },
        setUserPermissions: (state, payload) => {
            state.user.permissions = payload
            storage.setPermissions(payload)
        },
        setRouters: (state, payload) => {
            state.user.routers = payload
            storage.setRouters(payload)
        },
    },
    actions: {},
    getters
})

export default store

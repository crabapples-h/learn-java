import Vue from 'vue'
import Vuex from 'vuex'
import getters from '@/store/getters'
import user from '@/store/modules/user'
import sys from '@/store/modules/sys'
import permissions from '@/store/modules/permissions'
import roles from '@/store/modules/roles'

Vue.use(Vuex)
const store = new Vuex.Store({
    modules: {
        user,
        sys,
        permissions,
        roles,
    },
    state: {},
    mutations: {},
    actions: {},
    getters,
})

export default store

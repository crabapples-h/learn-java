import commonApi from "@/api/CommonApi";
import storage from "@/store/storage";

const sys = {
  state: {
    loadFinish: false,
    serverAddress: '',
    filePreviewAddress:''
  },
  mutations: {
    LOAD_FINISH: (state, payload) => {
      state.loadFinish = payload
    },
    SERVER_ADDRESS: (state, payload) => {
      state.menus = payload
      storage.setServerAddress(payload)
    },
    FILE_PREVIEW_ADDRESS: (state, payload) => {
      state.menus = payload
      storage.setFilePreviewAddress(payload)
    },
  },
  actions: {
    LOAD_FINISH(object, data) {
      object.commit('LOAD_FINISH', data)
    },
    SERVER_ADDRESS(object, data) {
      commonApi.serverAddress().then(({status, data}) => {
        object.commit('SERVER_ADDRESS', data)
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
    },
    FILE_PREVIEW_ADDRESS(object, data) {
      commonApi.filePreviewAddress().then(({status, data}) => {
        object.commit('FILE_PREVIEW_ADDRESS', data)
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
    },
  },
}
export default sys

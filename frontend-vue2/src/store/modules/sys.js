const sys = {
  state: {
    sys: {
      isDebug: true,
      loadFinish: false,
    }
  },
  mutations: {
    LOAD_FINISH: (state, payload) => {
      state.sys.loadFinish = payload
    },
  },
  actions: {
    LOAD_FINISH(object, data) {
      object.commit('LOAD_FINISH', data)
    }
  },
}
export default sys

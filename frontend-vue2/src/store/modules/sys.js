const sys = {
  state: {
    loadFinish: false,
  },
  mutations: {
    LOAD_FINISH: (state, payload) => {
      state.loadFinish = payload
    },
  },
  actions: {
    LOAD_FINISH(object, data) {
      object.commit('LOAD_FINISH', data)
    }
  },
}
export default sys

const getters = {
    token(state) {
        return state.user.token
    },
    userInfo: state => state.user.info,
    userMenus: state => state.user.menus,
    routers: state => state.user.routers,
}
export default getters
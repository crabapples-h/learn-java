const getters = {
    token: state => state.user.token,
    userInfo: state => state.user.info,
    userMenus: state => state.user.menus,
    routers: state => state.user.routers,
}
export default getters

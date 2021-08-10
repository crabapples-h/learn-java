import user from "@/store/modules/user";

const getters = {
    TOKEN(state) {
        return state.user.token
    },
    USER_INFO: state => state.user.info,
    MENUS(state) {
        console.log('state------222222222222222222222222>', state.permissions.menus)
        return state.permissions.menus ? state.permissions.menus : []
    },
    ROUTERS: state => state.user.routers,
}
export default getters

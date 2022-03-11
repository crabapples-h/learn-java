/**
 * TODO
 *
 * @author Mr.He
 * 2021/8/12 23:54
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
const getters = {
    TOKEN(state) {
        return state.user.token
    },
    USER_INFO: state => state.user.info,
    MENUS(state) {
        return [...state.permissions.menus]
    },
    ROUTERS: state => state.user.routers,
}
export default getters

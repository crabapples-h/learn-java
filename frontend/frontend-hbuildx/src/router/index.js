Vue.use(httpVueLoader);
const routes = [{
	path: '/demo',
	component: httpVueLoader('pages/demo.vue')
}]
const router = new VueRouter({
	routes
})
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}

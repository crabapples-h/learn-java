Vue.use(httpVueLoader);
const routes = [
	//住户
	{path: '/index',component: httpVueLoader('index.vue')},
	{path: '/family/index',component: httpVueLoader('family/index.vue')},
	{path: '/family/base-query',component: httpVueLoader('family/base-query.vue')},
	{path: '/family/custom-query',component: httpVueLoader('family/custom-query.vue')},
	{path: '/family/all-query',component: httpVueLoader('family/all-query.vue')},
	{path: '/family/add',component: httpVueLoader('family/add.vue')},
	{path: '/family/list',component: httpVueLoader('family/list.vue')},
	//户信息首页
	{path: '/family/info/:id',component: httpVueLoader('family/info.vue')},
	//户信息-基本信息
	{path: '/family/module/info-base/:id',component: httpVueLoader('family/module/info-base.vue')},
	{path: '/family/module/info-black/:id',component: httpVueLoader('family/module/info-black.vue')},
	{path: '/family/module/add-black-white/:id',component: httpVueLoader('family/module/add-black-white.vue')},
	{path: '/family/module/info-house/:id',component: httpVueLoader('family/module/info-house.vue')},
	{path: '/family/module/info-card/:id',component: httpVueLoader('family/module/info-card.vue')},
	{path: '/family/module/info-money/:id',component: httpVueLoader('family/module/info-money.vue')},
	{path: '/family/module/info-policy/:id',component: httpVueLoader('family/module/info-policy.vue')},
	{path: '/family/module/info-car/:id',component: httpVueLoader('family/module/info-car.vue')},
	{path: '/family/module/info-people-list/:id',component: httpVueLoader('family/module/info-people-list.vue')},

	//居民
	{path: '/index',component: httpVueLoader('index.vue')},
	{path: '/people/index',component: httpVueLoader('people/index.vue')},
	{path: '/people/base-query',component: httpVueLoader('people/base-query.vue')},
	{path: '/people/custom-query',component: httpVueLoader('people/custom-query.vue')},
	{path: '/people/add/:id',component: httpVueLoader('people/add.vue')},
	{path: '/people/list',component: httpVueLoader('people/list.vue')},
	{path: '/people/info/:id',component: httpVueLoader('people/info.vue')},
	{path: '/people/info-base/:id',component: httpVueLoader('people/module/base.vue')},
	{path: '/people/info-affairs/:id',component: httpVueLoader('people/module/affairs.vue')},
	{path: '/people/info-edu/:id',component: httpVueLoader('people/module/edu.vue')},
	{path: '/people/info-hospital/:id',component: httpVueLoader('people/module/hospital.vue')},
	{path: '/people/info-work/:id',component: httpVueLoader('people/module/work.vue')},
	{path: '/people/visit/list/:id',component: httpVueLoader('people/visit/list.vue')},
	{path: '/people/visit/info/:id',component: httpVueLoader('people/visit/info.vue')},
	{path: '/people/visit/add/:id',component: httpVueLoader('people/visit/add.vue')},
	//民政
	{path: '/affairs/index',component: httpVueLoader('affairs/index.vue')},
	{path: '/affairs/base-query',component: httpVueLoader('affairs/base-query.vue')},
	{path: '/affairs/custom-query',component: httpVueLoader('affairs/custom-query.vue')},
	{path: '/affairs/list',component: httpVueLoader('affairs/list.vue')},
	{path: '/affairs/info/:id',component: httpVueLoader('affairs/info.vue')},
	//医疗
	{path: '/hospital/index',component: httpVueLoader('hospital/index.vue')},
	{path: '/hospital/base-query',component: httpVueLoader('hospital/base-query.vue')},
	{path: '/hospital/custom-query',component: httpVueLoader('hospital/custom-query.vue')},
	{path: '/hospital/list',component: httpVueLoader('hospital/list.vue')},
	{path: '/hospital/info/:id',component: httpVueLoader('hospital/info.vue')},
	//就业
	{path: '/work/index',component: httpVueLoader('work/index.vue')},
	{path: '/work/base-query',component: httpVueLoader('work/base-query.vue')},
	{path: '/work/custom-query',component: httpVueLoader('work/custom-query.vue')},
	{path: '/work/list',component: httpVueLoader('work/list.vue')},
	{path: '/work/info/:id',component: httpVueLoader('work/info.vue')},
	//教育
	{path: '/edu/index',component: httpVueLoader('edu/index.vue')},
	{path: '/edu/base-query',component: httpVueLoader('edu/base-query.vue')},
	{path: '/edu/custom-query',component: httpVueLoader('edu/custom-query.vue')},
	{path: '/edu/list',component: httpVueLoader('edu/list.vue')},
	{path: '/edu/info/:id',component: httpVueLoader('edu/info.vue')},
	{path: '/edu/help-list/:id',component: httpVueLoader('edu/help-list.vue')},
	{path: '/edu/help-info/:id',component: httpVueLoader('edu/help-info.vue')},
	{path: '/edu/help-info',component: httpVueLoader('edu/help-info.vue')},

	//黑名单
	{path: '/black/index',component: httpVueLoader('black/index.vue')},
	{path: '/black/base-query',component: httpVueLoader('black/base-query.vue')},
	{path: '/black/custom-query',component: httpVueLoader('black/custom-query.vue')},
	{path: '/black/list',component: httpVueLoader('black/list.vue')},
	{path: '/black/info/:id',component: httpVueLoader('black/info.vue')},
]
const router = new VueRouter({
	routes
})

//获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}
mui.plusReady(function() {
	console.log('plusReady初始化');
const app = new Vue({
	el: '#app',
	router,
	components: {},
	data() {
		return {}
	},
	activated() {
	},
	mounted() {
		let sfz = JSON.parse(localStorage.getItem("pp")).sfz
		let url = UTIL.url(`/member/getIdByIdCard.do?idCard=${sfz}`)
		axios.get(url).then(response => {
			console.info('this.response--->',response.data.content)
			let id =  response.data.content.member.id
			let familyId =  response.data.content.houseHolder.id
			this.$router.replace({
				path: `/family/info/${familyId}`,
				query: {
					userId: id
				}
			})
			// this.$router.push({path: '/people/info/124'})
		})
	},
	methods: {
		back() {
			window.history.back()
			document.querySelector('#infortype').innerText = '家庭档案'
		}
	}
})
});

if(isDevice){
	mui.plusReady()
}
initVue()
function initVue(){
	const app = new Vue({
		el: '#app',
		router,
		components: {},
		data() {
			return {}
		},
		activated() {},
		mounted() {
			console.log(window)
			if (window.plus) {
				console.log(window)
				// 在这里调用5+ API 
			} else { // 兼容老版本的plusready事件 
				document.addEventListener('plusready', function() {
					// 在这里调用5+ API 
				}, false);
			}
			let sfz = JSON.parse(localStorage.getItem("pp")).sfz
			let url = UTIL.url(`/member/getIdByIdCard.do?idCard=${sfz}`)
			axios.get(url).then(response => {
				console.info('this.response--->', response.data.content)
				let id = response.data.content.member.id
				let familyId = response.data.content.houseHolder.id
				this.$router.replace({
					path: `/family/info/${familyId}`,
					query: {
						userId: id
					}
				})
				// this.$router.push({path: '/people/info/124'})
			})
		},
		methods: {
			back() {
				window.history.back()
				document.querySelector('#infortype').innerText = '家庭档案'
			}
		}
	})
}
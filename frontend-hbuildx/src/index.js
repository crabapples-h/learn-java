const app = new Vue({
	el: '#app',
	router,
	components: {},
	data() {
		return {}
	},
	activated() {},
	mounted() {
		this.$router.push({
			path: `/demo`,
			query: {}
		})
	},
	methods: {}
});

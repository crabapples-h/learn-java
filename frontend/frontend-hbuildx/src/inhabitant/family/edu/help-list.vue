<template>
	<div data="教育资助记录">
		<template data="教育资助记录">
			<p>教育资助记录</p>
			<router-link :to=`/edu/help-info/${item.id}` tag="span" v-for="(item,index) in dataList">
				<div class="row-item">
					<div>
						<input :value=`${item.jyYears}资助记录` class="mui-btn " style="width: 100%;" readonly />
					</div>
					<br>
				</div>
			</router-link>
		</template>
	</div>
</template>

<script>
	module.exports = {
		data() {
			return {
				options: {},
				dataList: [],
				isDie: false,
				id:''
			}
		},
		activated() {
			this.options = options
			this.id = this.$route.params.id
			let url = UTIL.url(`/member/getJyzzByMid.do?mid=${this.id}`)
			axios.get(url).then(response => {
				this.dataList = response.data
			})
			this.checkDie()
		},
		methods: {
			checkDie(){
				let url = UTIL.url(`/member/isFuckingDeadMan.do?id=${this.id}`)
				axios.get(url).then(response => {
					this.isDie = response.data.content === 1
				})
			},
			saveData() {
				this.$emit('submit', this.datainfo)
			},
			delLog(index) {
				this.datainfo.jyzz.splice(index, 1)
			},
			addHelp() {
				let mid = this.$route.query.mid
				let cid = this.$route.query.cid
				this.$router.push({
					path: `/edu/help-info`,
					query: {
						mid: mid,
						cid: cid
					}
				})
			},
		}
	}
</script>

<style scoped>
	.saveData {
		position: fixed;
		top: 5.20rem;
		z-index: 999;
		right: 10px;
		background-color: none;
	}

	.deleteData {
		position: fixed;
		top: 5.20rem;
		z-index: 999;
		left: 10px;
		background-color: none;
	}
</style>

<template>
	<div class="option" style="margin-top: 6rem;">
		<ul class="mui-table-view">
			<router-link :to=`/people/visit/info/${item.id}` tag="span" v-for="item in dataList">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">{{item.Xfjls}}</a>
				</li>
			</router-link>
			<router-link :to=`/people/visit/add/${$route.params.id}` tag="span" v-if="!isDie">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">添加信访记录</a>
				</li>
			</router-link>
		</ul>
	</div>
	
</template>
<script>
	module.exports = {
		components: {
			'visit': 'url:../../../components/visit.vue',
		},
		data() {
			return {
				dataList: [],
				isDie: false,
				id:''
			}
		},
		activated() {
			this.id = this.$route.params.id
			let url = UTIL.url(`/member/getXfjlByMid.do?mid=${this.id}`)
			axios.get(url).then(response => {
				this.dataList = response.data
				console.log('this.dataList-->visit-->', this.dataList)
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
		}
	}
</script>
<style scoped>
	.option ul li {
		line-height: 3rem;
	}

	.mui-table-view-cell {
		border: 1px solid #f1f1f1;
	}
</style>

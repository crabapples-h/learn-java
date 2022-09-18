<template>
	<ul className="mui-table-view" style="background-color: #fff;">
			&nbsp;&nbsp;<span>人数:{{dataList.length}}</span>
		<router-link :to=`/edu/info/${item.id}` tag="span" :key="item.houseHolderId" v-for="item in dataList">
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right">
					<p class=" mui-table-view-row">
						<span> {{item.name}}</span>
						<span style="color:red" v-if="item.isDie==='是'">(已故)</span>
					</p>
					<p class=" mui-table-view-row">{{item.idCard}}</p>
				</a>
			</li>
		</router-link>
		<van-loading type="spinner" v-if="spinner" class="loading" color="#1989fa"></van-loading>
	</ul>
</template>
<script>
	module.exports = {
		data() {
			return {
				spinner: true,
				dataList: [],
			}
		},
		activated(){
			this.spinner = true
			const _this = this
			let param = JSON.parse(localStorage.getItem('eduQuery'))
			let url = UTIL.url('member/queryEdu4App.do')
			axios.post(url, param).then(response => {
				if (response.status === 200) {
					this.dataList = response.data
				}
				this.spinner = false
			})
		},
		mounted() {
			
		},
		methods: {}
	}
</script>
<style scoped>
	.option ul li {
		color: #8c8c8c;
	}

	.mui-table-view-row {
		padding: 0;
		margin: 0;
		line-height: 24px;
	}

	.mui-table-view-cell {
		border-bottom: 1px solid #cbcbcb;
	}
</style>

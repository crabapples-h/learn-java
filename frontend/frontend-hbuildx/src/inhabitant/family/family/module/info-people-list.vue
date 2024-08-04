<!-- 家庭成员 -->
<template>
	<div class="option" style="margin-top: 6rem;">
		<ul class="mui-table-view">
			<router-link :to=`/people/info/${item.id}` tag="span" v-for="item in dataList">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">
						<span>{{item.pName}}</span>
						<span style="color:red"v-if="item.isDie==='是'">(已故)</span>
						<span>({{item.sex}})</span>
						<span>({{item.age}}岁)</span>
						<p>({{item.icard}})</p>
					</a>
				</li>
			</router-link>
		</ul>
	</div>
</template>
<script>
	module.exports = {
		data() {
			return {
				dataList: []
			}
		},
		activated() {
			axios.get(UTIL.url(`member/getByPid.do?pid=${this.$route.params.id}`)).then(({
				data
			}) => {
				this.dataList = data
				console.log('this.dataList-->',this.dataList)
			})
		},
		methods: {}
	}
</script>
<style scoped>
	.mui-table-view-cell {
		border: 1px solid #f1f1f1;
	}
</style>

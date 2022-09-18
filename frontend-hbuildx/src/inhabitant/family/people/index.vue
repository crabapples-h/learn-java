<template>
	<ul class="mui-table-view">
		<router-link to="/people/base-query" tag="span">
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right">姓名/身份证查询</a>
			</li>
		</router-link>
		<router-link to="/people/custom-query" tag="span">
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right">条件查询</a>
			</li>
		</router-link>
		<li class="mui-table-view-cell" @click="queryAll()">
			<a class="mui-navigate-right">查询所有</a>
		</li>
	</ul>
</template>
<script>
	module.exports = {
		data() {
			return {};
		},
		activated() {
			document.querySelector('#infortype').innerText = '居民档案-居民'
		},
		methods: {
			queryAll() {
				let param = {}
				localStorage.removeItem('peopleQuery')
				localStorage.setItem('peopleQuery', JSON.stringify(param))
				this.$router.push('/people/list')
			}
		}
	}
</script>
<style scoped>
</style>

<template>
	<div class="option" style="margin-top: 6rem;">
		<div class="mui-content-padded" style="margin: 5px;">
			<h5>输入姓名或身份证号查找居民信息：</h5>
			<div class="mui-input-row mui-search">
				<div>
					<input type="search" class="mui-input-clear" v-model="name">
				</div>
			</div>
			<div class="mui-button-row">
				<button type="button" style="width: 12rem;" class="mui-btn mui-btn-primary"
					@click="submit()">查找</button>
			</div>
		</div>
	</div>
</template>
<script>
	module.exports = {
		data() {
			return {
				name: ''
			}
		},
		mounted() {},
		methods: {
			submit() {
				console.log()
				let param = {
					name: this.name
				}
				localStorage.setItem('hospitalQuery', JSON.stringify(param))
				this.$router.push('/hospital/list')
			}
		}
	}
</script>
<style scoped>

</style>

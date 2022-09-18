<template>
	<div class="option" style="margin-top: 6rem;">
		<template data="村">
			<div style="padding: 10px 1.25rem">
				<span>村</span>
				<select class="mui-btn" v-model="params.cun">
					<option value=""> --选择村--</option>
					<option :value="item" v-for="item in options.cuns">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="拉入黑名单原因">
			<div style="padding: 10px 1.25rem">
				<span>拉入黑名单原因</span>
				<select class="mui-btn" v-model="params.lhyy">
					<option value=""> --选择拉入黑名单原因--</option>
					<option :value="item" v-for="item in options.lhyys">{{item}}</option>
				</select>
			</div>
		</template>
		<div class='mui-button-row'>
			<button type="button" style="width: 12rem" class="mui-btn mui-btn-primary" @click="submit">查找</button>
		</div>
		<br />
	</div>
</template>
<script>
	module.exports = {
		data() {
			return {
				options: {},
				params: {
					cun: '',
					lhyy: '',
				},
			}
		},
		mounted() {
			this.options = options
		},
		methods: {
			submit() {
				let param = {
					isLowIncome: this.params.cun,
					isPoor: this.params.lhyy,
				}
				localStorage.setItem('blackQuery', JSON.stringify(param))
				this.$router.push('/black/list')
			}
		}
	}
</script>
<style scoped>
	.mui-btn-block {
		height: 25px;
	}
</style>

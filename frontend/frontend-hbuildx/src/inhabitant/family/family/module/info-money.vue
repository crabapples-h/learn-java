<!-- 收入信息 -->
<template>
	<div class="option" style="margin-top: 6rem;">
		<van-cell center title="家庭总收入" :value="dataInfo.jtzsr"></van-cell>
		<van-cell center title="家庭人均收入（元/年/人）" :value="dataInfo.rjsr"></van-cell>
		<van-cell center title="低保月保障金额（元/月）" :value="dataInfo.ybz"></van-cell>
		<van-cell center title="种养殖产业流转土地总收入（元/年）" :value="dataInfo.nsr+dataInfo.yzxssr"></van-cell>
		<van-cell center title="种植年收入（元/年）" :value="dataInfo.nsr"></van-cell>
		<van-cell center title="养殖年收入（元/年）" :value="dataInfo.yzxssr"></van-cell>
		<van-cell center title="家庭成员务工收入（元/年）" :value="dataInfo.wgsrz"></van-cell>
		<van-cell center title="家庭成员行政工作收入（元/年）" :value="dataInfo.mzsrz"></van-cell>
	</div>

</template>
<script>
	module.exports = {
		data() {
			return {
				dataInfo: {},
			}
		},
		activated() {
			let id = this.$route.params.id
			axios.get(UTIL.url(`gethuSr.do?id=${id}`)).then(response => {
				this.dataInfo = response.data
			})
		},
		methods: {
		}
	}
</script>
<style scoped>
</style>

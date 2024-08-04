<!-- 享受政策 -->
<template>
	<div class="option" style="margin-top: 6rem;">
		<van-cell center title="享受政策" :value="dataInfo.enjoyPol"></van-cell>
		<template v-if="dataInfo.enjoyPol === '危房改造政策'">
			<van-cell center title="危房改造政策" :value="dataInfo.houType"></van-cell>
			<van-cell center title="享受年度" :value="dataInfo.hyear"></van-cell>
			<van-cell center title="危房等级" :value="dataInfo.dangLevel"></van-cell>
			<van-cell center title="补助金额" :value="dataInfo.subMoney"></van-cell>
		</template>
		<template v-if="dataInfo.enjoyPol === '易地扶贫搬迁政策'">
			<van-cell center title="易地扶贫搬迁政策" :value="dataInfo.isTransfer"></van-cell>
		</template>
		<template v-if="dataInfo.enjoyPol === '透风漏雨整治'">
			<van-cell center title="透风漏雨整治" :value="dataInfo.draftyLeak"></van-cell>
		</template>
	</div>
</template>
<script>
	module.exports = {
		data() {
			return {
				dataInfo: {},
				areas: [],
				zus: [],
				options: {}
			}
		},
		activated() {
			this.options = options
			axios.get(UTIL.url(`getPeoInfoById.do?id=${this.$route.params.id}`)).then(response => {
				if (response.data.state === 1) {
					this.dataInfo = response.data.content
					this.dataInfo.nsr = (this.dataInfo.jtyrjsr * 12).toFixed(2)
				}
			})
		},
		methods: {}
	}
</script>
<style scoped>

</style>

<!-- 黑名单 -->
<template>
	<div class="option" style="margin-top: 6rem;">
		<van-cell center title="黑名单状态" :value="familyInfo.blacklist"></van-cell>
		<van-cell center title="加入黑名单原因" :value="familyInfo.blacklistReason"></van-cell>
	</div>
</template>
<script>
	module.exports = {
		data() {
			return {
				familyInfo: {}
			}
		},
		activated() {
			axios.get(UTIL.url(`getPeoInfoById.do?id=${this.$route.params.id}`)).then(response => {
				if (response.data.state === 1) {
					this.familyInfo = response.data.content
				}
			})
		},
		methods: {}
	}
</script>
<style scoped>
</style>

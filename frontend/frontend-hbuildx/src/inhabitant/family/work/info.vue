<template>
	<div class="option" style="margin-top: 0rem;">
		<div style="padding: 10px">
			<employ :datainfo="dataInfo" @submit="saveEmploy"></employ>
		</div>
	</div>
</template>
<script>
	module.exports = {
		components: {
			'employ': 'url:../components/employ.vue',
		},
		data() {
			return {
				dataInfo: {},
			}
		},
		activated() {
			let url = UTIL.url(`/member/getEmployByMid.do?mid=${this.$route.params.id}`)
			axios.get(url).then(response => {
				this.dataInfo = response.data.content
			})
		},
		methods: {
			saveEmploy(data) {
				console.log(data)
				let url = UTIL.url(`/member/updateEmployById.do`)
				axios.post(url, data).then(response => {
					alert('保存成功')
					window.history.back(-1)
				})
			},
		}
	}
</script>
<style scoped>
	.option ul li {
		line-height: 3rem;
	}
</style>

<template>
	<div class="option" style="margin-top: 0rem;">
		<div style="padding: 10px">
			<visit :datainfo="dataInfo" @submit="saveVisit"></visit>
		</div>
	</div>
</template>
<script>
	module.exports = {
		components: {
			'visit': 'url:../../components/visit.vue',
		},
		data() {
			return {
				dataInfo: {},
			}
		},
		activated() {
			let id = this.$route.params.id
			let url = UTIL.url(`member/getXfjlById.do?id=${id}`)
			axios.get(url).then(response => {
				this.dataInfo = response.data.content
			})
		},
		methods: {
			saveVisit(data) {
				let url = UTIL.url(`/member/updateXfjlById.do`)
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

<template>
	<div class="option" style="margin-top: 0rem;">
		<div style="padding: 10px">
			<edu-help-info :datainfo="dataInfo" @submit="saveHelp" @remove="delHelp"></edu-help-info>
		</div>
	</div>
</template>
<script>
	module.exports = {
		components: {
			'edu-help-info': 'url:../components/edu-help-info.vue',
		},
		data() {
			return {
				dataInfo: {},
			}
		},
		activated() {
			this.dataInfo ={}
			let id = this.$route.params.id
			if (id) {
				let url = UTIL.url(`/member/getJyzzById.do?id=${id}`)
				axios.get(url).then(response => {
					this.dataInfo = response.data.content
				})
			}
		},
		methods: {
			saveHelp(data) {
				let url
				let id = this.$route.params.id
				let cid = this.$route.query.cid
				let mid = this.$route.query.mid
				if (!!id) {
					url = UTIL.url(`member/updateJyzzById.do`)
				} else {
					url = UTIL.url(`member/addJyzz.do`)
					data.mid = mid
					data.cid = cid
				}
				axios.post(url, data).then(response => {
					alert('保存完成')
					window.history.back(-1)
				})
			},
			delHelp(data) {
				if (confirm('确定要删除吗') == true) {
					let id = this.$route.params.id
					let url = UTIL.url(`/member/deleteJyzzById.do?id=${id}`)
					axios.post(url).then(response => {
						alert('删除成功')
						window.history.back(-1)
					})
				} else {
					return false;
				}
			},
		},
	}
</script>
<style scoped>
	.option ul li {
		line-height: 3rem;
	}
</style>

<template>
	<div class="option" style="margin-top: 0rem;">
		<div style="padding: 10px">
			<edu :datainfo="dataInfo" @submit="saveEdu" :isdie="isDie"></edu>
		</div>
	</div>
</template>
<script>
	module.exports = {
		components: {
			'edu': 'url:../../components/edu.vue',
		},
		data() {
			return {
				dataInfo: {},
				isDie: false,
			}
		},
		activated() {
			let mid = this.$route.params.id
			let url = UTIL.url(`/member/getEduByMid.do?mid=${mid}`)
			axios.get(url).then(response => {
				this.dataInfo = response.data.content
				this.isDie = this.dataInfo.isDie === '是'
				console.log('this.dataInfo-->edu-->', this.dataInfo)
			})
		},
		methods: {
			saveEdu(data) {
				if (this.isDie) {
					alert('已故人员不允许修改数据')
					return
				}
				let url = UTIL.url(`/member/updateEduById.do`)
				axios.post(url, data).then(response => {
					alert('保存成功')
					window.history.back(-1)
				})
			},
		},
	}
</script>
<style scoped>
	.option ul li {
		line-height: 3rem;
	}
</style>

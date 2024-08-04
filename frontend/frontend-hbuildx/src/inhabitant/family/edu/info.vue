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
			'edu': 'url:../components/edu.vue',
		},
		data() {
			return {
				dataInfo: {},
				id:'',
				isDie:false
			}
		},
		activated() {
			this.id = this.$route.params.id
			let url = UTIL.url(`/member/getEduByMid.do?mid=${this.id}`)
			axios.get(url).then(response => {
				this.dataInfo = response.data.content
				console.log('this.dataInfo-->edu-->',this.dataInfo)
			})
			this.checkDie()
		},
		methods: {
			checkDie(){
				let url = UTIL.url(`/member/isFuckingDeadMan.do?id=${this.id}`)
				axios.get(url).then(response => {
					this.isDie = response.data.content === 1
				})
			},
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

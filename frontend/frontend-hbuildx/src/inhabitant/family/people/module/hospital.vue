<template>
	<div class="option" style="margin-top: 0rem;">
		<div style="padding: 10px">
			<health :datainfo="dataInfo" @submit="saveHealth" :isdie="isDie"></health>
		</div>
	</div>
</template>
<script>
	module.exports = {
		components: {
			'health': 'url:../../components/healthMed.vue',
		},
		data() {
			return {
				dataInfo: {},
				id: '',
				isDie: false,
			}
		},
		activated() {
			this.id = this.$route.params.id
			let url = UTIL.url(`member/getHealthMedByMid.do?mid=${this.id}`)
			axios.get(url).then(response => {
				this.dataInfo = response.data.content
				console.log('this.dataInfo-->helath-->', this.dataInfo)
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
			saveHealth(data) {
				if (this.isDie) {
					alert('已故人员不允许修改数据')
					return
				}
				let url = UTIL.url(`member/updateHealthMedById.do`)
				axios.post(url, data).then(response => {
					alert('保存成功')
					window.history.back(-1)
				})
			},
		}
	}
</script>
<style scoped>
	.option {
		line-height: 2rem;
	}
</style>

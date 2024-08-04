<template>
	<div class="option" style="margin-top: 0rem;">
		<div style="padding: 10px">
			<employ :datainfo="dataInfo" @submit="saveWork" :isdie="isDie"></employ>
		</div>
	</div>
</template>
<script>
	module.exports = {
		components: {
			'employ': 'url:../../components/employ.vue',
		},
		data() {
			return {
				dataInfo: {},
				isDie: false,
				id:''
			}
		},
		activated() {
			this.id = this.$route.params.id
			let url = UTIL.url(`/member/getEmployByMid.do?mid=${this.id}`)
			axios.get(url).then(response => {
				this.dataInfo = response.data.content
				console.log('this.dataInfo-->work>', this.dataInfo)
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
			saveWork(data) {
				if (this.isDie) {
					alert('已故人员不允许修改数据')
					return
				}
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

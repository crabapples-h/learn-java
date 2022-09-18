<template>
	<div class="option" style="margin-top: 0rem;">
		<div style="padding: 10px">
			<black :datainfo="dataInfo" @remove="removeBlack" :isdie="isDie"></black>
		</div>
	</div>
</template>
<script>
	module.exports = {
		components: {
			'black': 'url:../components/black.vue',
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
			let url = UTIL.url(`family/getBlacklistDetail.do?id=${this.id}`)
			axios.get(url).then(response => {
				this.dataInfo = response.data.content
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
			removeBlack(id) {
				if (this.isDie) {
					alert('已故人员不允许修改数据')
					return
				}
				if (confirm('确定要移除黑名单吗') == true) {
					let data = {
						bl: '否',
						id: id
					}
					let url = UTIL.url(`changeBlackListStatus4App.do`)
					axios.post(url, data).then(response => {
						alert('成功')
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

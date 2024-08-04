<template>
	<div class="option" style="margin-top: 0rem;">
		<div style="padding: 10px">
			<affairs :datainfo="familyInfo" @submit="saveAffairs" :isdie="isDie"></affairs>
			<div @click="toFamily"
				style="text-align: center; color: #0062CC; border: #0062CC 0.0625rem solid; background-color: #FFFFFF;">
				<span>查看户信息</span>
			</div>
		</div>
	</div>
</template>
<script>
	module.exports = {
		components: {
			'affairs': 'url:../components/affairs.vue',
		},
		data() {
			return {
				familyInfo: {
					id: '',
					pid: '',
					isDie: false,
				},

			}
		},
		activated() {
			this.id = this.$route.params.id
			let url = UTIL.url(`member/getCivilByMid.do?mid=${this.id}`)
			axios.get(url).then(response => {
				this.familyInfo = response.data.content
				this.isDie = this.familyInfo.IsDie === '是'
				console.log('this.familyInfo-->', this.familyInfo)
			})
		},
		methods: {
			toFamily() {
				this.$router.push({
					path: `/family/info/${this.familyInfo.pid}`,
					query: {
						userId: this.id
					}
				})
			},
			saveAffairs(data) {
				if (this.isDie) {
					alert('已故人员不允许修改数据')
					return
				}
				let url = UTIL.url(`member/updateCivilById.do`)
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

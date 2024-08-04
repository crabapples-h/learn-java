<template>
	<div class="option" style="margin-top: 0rem;">
		<div style="padding: 10px">
			<affairs :datainfo="familyInfo" @submit="saveAffairs" :isdie="isDie"></affairs>
		</div>
	</div>
</template>
<script>
	module.exports = {
		components: {
			'affairs': 'url:../../components/affairs.vue',
		},
		data() {
			return {
				familyInfo: {},
				id: this.$route.params.id,
				isDie:false,
			}
		},
		activated() {
			let url = UTIL.url(`member/getCivilByMid.do?mid=${this.id}`)
			axios.get(url).then(response => {
				this.familyInfo = response.data.content
				this.isDie = this.familyInfo.IsDie==='是'
			})
		},
		methods: {
			saveAffairs(data) {
				if(this.isDie){
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

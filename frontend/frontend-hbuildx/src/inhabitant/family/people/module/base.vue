<template>
	<div class="option" style="margin-top: 0rem;">
		<div style="padding: 10px">
			<base-info :datainfo="dataInfo" @submit="saveBase" :isdie="isDie"></base-info>
		</div>
	</div>
</template>
<script>
	module.exports = {
		components: {
			'base-info': 'url:../../components/base.vue',
		},
		data() {
			return {
				dataInfo: {},
				isDie:false,
			}
		},
		activated() {
			let id = this.$route.params.id
			let url = UTIL.url(`member/getMemberById.do?id=${id}`)
			axios.get(url).then(response => {
				this.dataInfo = response.data.content
				this.isDie = this.dataInfo.IsDie==='是'
				console.log('this.dataInfo-->',this.dataInfo)
			})
		},
		methods: {
			saveBase(data) {
				if(this.isDie){
					alert('已故人员不允许修改数据')
					return
				}
				let url = UTIL.url(`/member/save_or_update.do`)
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

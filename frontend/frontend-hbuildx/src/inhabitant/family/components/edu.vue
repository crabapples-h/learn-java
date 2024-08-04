<template>
	<div data="家庭成员教育信息">
		<template v-if="datainfo != null">
			<van-cell title="是否为为在校生" :value="datainfo.isStudent"></van-cell>
			<van-cell title="就读学校" :value="datainfo.School"></van-cell>
			<van-cell title="就读年级班级" :value="datainfo.GradeClass"></van-cell>
			<van-cell title="班主任姓名" :value="datainfo.TeaName"></van-cell>
			<van-cell title="班主任电话" :value="datainfo.TeaTel"></van-cell>
			<van-cell title="辍学时间" :value="datainfo.DropTime"></van-cell>
			<van-cell title="复学时间" :value="datainfo.Fxsj"></van-cell>
			<van-cell title="其它资助备注" :value="datainfo.OtherBz"></van-cell>
			<template data="资助记录">
				<div class="row-item">
					<div>
						<input value="资助记录" class="mui-btn " style="width: 100%;" readonly
							@click="toEduList(datainfo.id)" />
					</div>
				</div>
			</template>
		</template>
	</div>
</template>

<script>
	module.exports = {
		components: {
			'edu-help-list': 'url:../components/edu-help-list.vue',
		},
		data() {
			return {
				options: {}
			}
		},
		props: {
			datainfo: {
				type: Object,
				default: () => null
			},
			showButton: {
				type: Boolean,
				default: true
			},
			isdie: {
				type: Boolean,
				default: false
			},
			submit: {
				type: Function,
			},
			delete: {
				type: Function,
			},
		},
		mounted() {
			this.options = options
		},
		methods: {
			toEduList(id) {
				this.$router.push({
					path: `/edu/help-list/${id}`,
					query: {
						mid: this.datainfo.id,
						cid: this.datainfo.id
					}
				})
			},
			saveData() {
				this.$emit('submit', this.datainfo)
			},
		}
	}
</script>

<style scoped>
	.saveData {
		position: fixed;
		top: 5.20rem;
		z-index: 999;
		right: 10px;
		background-color: none;
	}

	.deleteData {
		position: fixed;
		top: 5.20rem;
		z-index: 999;
		left: 10px;
		background-color: none;
	}
</style>

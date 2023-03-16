<template>
	<div data="家庭成员民政信息">
		<template v-if="datainfo != null">
			<van-cell title="姓名" value-class="red" :value="datainfo.Name+'(已故)'" v-if="isdie"></van-cell>
			<van-cell title="姓名" :value="datainfo.Name" v-else></van-cell>
			<van-cell title="性别" :value="datainfo.gender"></van-cell>
			<van-cell title="身份证号" :value="datainfo.idCard"></van-cell>
			<van-cell title="就业单位" :value="datainfo.enterprise"></van-cell>
			<van-cell title="就业地域" :value="datainfo.area"></van-cell>
			<van-cell title="联系电话" :value="datainfo.phone"></van-cell>
			<van-cell title="就业详细地址" :value="datainfo.workLocation"></van-cell>
			<van-cell title="外出就业开始时间" :value="datainfo.WorkingTime"></van-cell>
			<van-cell title="就业时长" :value="datainfo.duration"></van-cell>
			<van-cell title="月收入" :value="datainfo.srje"></van-cell>
			<van-cell title="就业现状" :value="datainfo.jyxz"></van-cell>
			<van-cell title="涉及产业" :value="datainfo.cymc"></van-cell>
			<van-cell title="是否参加就业培训" :value="datainfo.isJypx"></van-cell>
			<van-cell title="培训工种" :value="datainfo.pxgz"></van-cell>
			<van-cell title="培训时间" :value="datainfo.pxsj"></van-cell>
		</template>
	</div>
</template>

<script>
	module.exports = {
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
			saveData() {
				this.$emit('submit', this.datainfo)
			}
		}
	}
</script>

<style scoped>
	.red{
		color: red;
	}
</style>

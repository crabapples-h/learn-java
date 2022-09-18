<template>
	<div data="民政信息">
		<template v-if="datainfo != null">
			<van-cell title="姓名" value-class="red" :value="datainfo.pName+'(已故)'" v-if="isdie"></van-cell>
			<van-cell title="姓名" :value="datainfo.pName" v-else></van-cell>
			<van-cell title="身份证号" :value="datainfo.ICard"></van-cell>
			<van-cell title="性别" :value="datainfo.Sex"></van-cell>
			<van-cell title="是否属于低保对象" :value="datainfo.IsLowIncome"></van-cell>
			<van-cell title="低保金额（每年）" :value="datainfo.dbje"></van-cell>
			<van-cell title="是否是特困供养人员" :value="datainfo.isTkgyrydx"></van-cell>
			<van-cell title="特困供养金额" :value="datainfo.tkgyj"></van-cell>
			<van-cell title="是否享受高龄津贴" :value="datainfo.IsEnjoyAdAge"></van-cell>
			<van-cell title="高龄津贴金额（每年）" :value="datainfo.AdAgeMon"></van-cell>
			<van-cell title="是否是残疾人" :value="datainfo.IsDisabled"></van-cell>
			<van-cell title="残疾人证件号" :value="datainfo.DisabilityNum"></van-cell>
			<van-cell title="是否享受残疾补助" :value="datainfo.IsEnjoyDis"></van-cell>
			<van-cell title="残疾补助金额" :value="datainfo.DisMon"></van-cell>
			<van-cell title="是否退役军人" :value="datainfo.Veterans"></van-cell>
			<van-cell title="是否享受退役军人优待" :value="datainfo.IsEnjoyMat"></van-cell>
			<van-cell title="是否办理领取养老保险手续" :value="datainfo.IsBllqYlbx"></van-cell>
			<van-cell title="领取金额" :value="datainfo.Ylbxje"></van-cell>
			<van-cell title="是否留守儿童" :value="datainfo.IsLset"></van-cell>
			<van-cell title="是否孤儿" :value="datainfo.IsGe"></van-cell>
			<van-cell title="孤儿补贴资金" :value="datainfo.Gebt"></van-cell>
			<van-cell title="留守儿童慰问物资记录" :value="datainfo.lsetwwwzjlbz"></van-cell>
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
			},
			deleteData() {
				if (confirm('确定要删除吗') == true) {
					this.$emit('delete', this.datainfo)
				} else {
					return false;
				}
			},
		}
	}
</script>

<style>
	.red {
		color: red;
	}
</style>

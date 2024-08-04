<template>
	<div data="医疗信息">
		<template v-if="datainfo != null">
			<van-cell center title="姓名" value-class="red" :value="datainfo.Name+'(已故)'" v-if="isdie"></van-cell>
			<van-cell center title="姓名" :value="datainfo.Name" v-else></van-cell>
			<van-cell center title="性别" :value="datainfo.gender"></van-cell>
			<van-cell center title="身份证号" :value="datainfo.idCard"></van-cell>
			<van-cell center title="健康状况" :value="datainfo.Health"></van-cell>
			<van-cell center title="是否患大病" :value="datainfo.hasZdjb"></van-cell>
			<van-cell center title="大病种类" :value="datainfo.jblx"></van-cell>
			<van-cell center title="是否残疾" :value="datainfo.isCjry"></van-cell>
			<van-cell center title="残疾类型" :value="datainfo.cjlx"></van-cell>
			<van-cell center title="是否家庭医生签约服务" :value="datainfo.IsDoctor"></van-cell>
			<van-cell center title="家庭医生团队" :value="datainfo.teamName"></van-cell>
			<van-cell center title="责任医生" :value="datainfo.Ysmc"></van-cell>
			<template data="计生四项制度记录">
				<van-cell>
					<span>计生四项制度记录</span>
				</van-cell>
				<template v-if="datainfo.jssx && datainfo.jssx.length">
					<van-cell v-for="(item,index) in datainfo.jssx">
						<van-cell :border="false" center title="时间" :value="datainfo.jssx[index]['Time']"></van-cell>
						<van-cell :border="false" center title="制度" :value="datainfo.jssx[index]['Zhidu']"></van-cell>
						<van-cell :border="false" center title="金额" :value="datainfo.jssx[index]['Je']">
						</van-cell>
					</van-cell>
				</template>
			</template>
			<van-cell center title="是否享受救助" :value="datainfo.IsXsjz"></van-cell>
			<van-cell center title="临时救助名称" :value="datainfo.Lsjz"></van-cell>
			<van-cell center title="医疗救助名称" :value="datainfo.Yljz"></van-cell>
			<van-cell center title="是否享受残疾补助" :value="datainfo.IsXxCjbz"></van-cell>
			<van-cell center title="享受残疾补助金额" :value="datainfo.Cjbzje"></van-cell>
			<van-cell center title="是否享受计生协会的帮扶" :value="datainfo.IsXxjsxh" v-if="false"></van-cell>
			<van-cell center title="时间" :value="datainfo.bySj" v-if="false"></van-cell>
			<van-cell center title="帮扶项目名称" :value="datainfo.Xm" v-if="false"></van-cell>
			<van-cell center title="帮扶资金" :value="datainfo.Je" v-if="false"></van-cell>
			<van-cell center title="是否采取长效避孕措施" :value="datainfo.isBy" v-if="false"></van-cell>
			<van-cell center title="避孕方法" :value="datainfo.byZl" v-if="false"></van-cell>
			<van-cell center title="是否购买医疗保险或参加合作医疗" :value="datainfo.IsGmylbx"></van-cell>
			<van-cell center title="保险编号" :value="datainfo.IsXsjz"></van-cell>
			<template data="医疗保险或合作医疗记录">
				<van-cell>
					<span>医疗保险或合作医疗记录</span>
				</van-cell>
				<template v-if="datainfo.yljl && datainfo.yljl.length">
					<van-cell v-for="(item,index) in datainfo.yljl">
						<van-cell :border="false" center title="开始时间" :value="datainfo.yljl[index]['startDate']">
						</van-cell>
						<van-cell :border="false" center title="结束时间" :value="datainfo.yljl[index]['endDate']">
						</van-cell>
						<van-cell :border="false" center title="金额" :value="datainfo.yljl[index]['money']"></van-cell>
						<van-cell :border="false" center title="类型" :value="datainfo.yljl[index]['type']"></van-cell>
					</van-cell>
				</template>
			</template>
			<van-cell center title="保险类型" :value="datainfo.IsXsjz" v-if="false"></van-cell>
			<van-cell center title="医保地点" :value="datainfo.IsXsjz" v-if="false"></van-cell>
		</template>
	</div>
</template>

<script>
	module.exports = {
		components: {},
		data() {
			return {
				options: {},
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
		methods: {}
	}
</script>

<style scoped>
	.red {
		color: red;
	}
</style>

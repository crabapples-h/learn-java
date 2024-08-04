<!-- 基础信息 -->
<template>
	<div class="option" style="margin-top: 6rem;"  id="parent">
		<van-cell title="姓名" :value="dataInfo.pName"></van-cell>
		<van-cell title="性别" :value="dataInfo.sex"></van-cell>
		<van-cell title="民族" :value="dataInfo.nation"></van-cell>
		<van-cell title="政治面貌" :value="dataInfo.political"></van-cell>
		<van-cell title="学历" :value="dataInfo.education"></van-cell>
		<template data="hidden" v-if="show.hidden">
			<van-cell title="是否有身份证" :value="dataInfo.isICard"></van-cell>
			<template data="身份证号码" v-if="dataInfo.isICard === '是'">
				<van-cell title="身份证号码" :value="dataInfo.icard"></van-cell>
			</template>
			<template data="无身份证原因" v-if="dataInfo.isICard === '否'">
				<van-cell title="无身份证原因" :value="dataInfo.noICard"></van-cell>
			</template>
			<van-cell title="出生日期" :value="dataInfo.birth"></van-cell>
			<van-cell title="婚姻状况" :value="dataInfo.hyzks"></van-cell>
			<van-cell title="婚姻备注" :value="dataInfo.marrRemark"></van-cell>
			<van-cell title="初婚时间" :value="dataInfo.fristMarr"></van-cell>
			<van-cell title="二婚时间" :value="dataInfo.secMarr"></van-cell>
			<van-cell title="离异时间" :value="dataInfo.marrCrisis"></van-cell>
		</template>
		<van-cell title="家庭住址" :value="dataInfo.country"></van-cell>
		<van-cell title="婚姻备注" :value="dataInfo.town"></van-cell>
		<van-cell title="婚姻备注" :value="dataInfo.Village"></van-cell>
		<van-cell title="婚姻备注" :value="dataInfo.groups"></van-cell>
		<van-cell title="是否是外地居住" :value="dataInfo.isNonLive"></van-cell>
		<template data="外地居住时间" v-if="dataInfo.isNonLive==='是'">
			<van-cell title="外地居住时间" :value="dataInfo.nonLiveTime"></van-cell>
		</template>
		<van-cell title="文化户口编号" :value="dataInfo.domCode"></van-cell>
		<van-cell title="电话" :value="dataInfo.phone"></van-cell>
		<van-cell title="是否离任村干部" :value="dataInfo.isOgCadre"></van-cell>
		<van-cell title="文化户口编号" :value="dataInfo.domCode"></van-cell>
		<template v-if="dataInfo.isOgCadre==='是'">
			<van-cell title="离任干部，离任时间" :value="dataInfo.lcgbrzsj"></van-cell>
			<van-cell title="离任干部，曾任职位（最高）" :value="dataInfo.lcgbrzzw"></van-cell>
		</template>
		<van-cell title="是否三留人员" :value="dataInfo.sfslry"></van-cell>
		<template v-if="dataInfo.sfslry==='是'">
			<van-cell title="三留人员监护人" :value="dataInfo.slryjhr"></van-cell>
			<van-cell title="三留人员监护人身份证号" :value="dataInfo.slryjhrsfz"></van-cell>
			<van-cell title="三留人员监护人电话" :value="dataInfo.slryjhrdh"></van-cell>
		</template>
		<van-cell title="是否计生两户" :value="dataInfo.isTwoPlan"></van-cell>
		<van-cell title="是否开通广电云" :value="dataInfo.isBroadTV"></van-cell>
		<van-cell title="是否实现通讯网络全覆盖" :value="dataInfo.isNetwork"></van-cell>
		<van-cell title="是否是五保户" :value="dataInfo.slryjhr"></van-cell>
		<van-cell title="三留人员监护人" :value="dataInfo.isFiveHou"></van-cell>
		<van-cell title="黑名单状态" :value="dataInfo.blacklist"></van-cell>
		<van-cell title="三留人员监护人" :value="dataInfo.slryjhr"></van-cell>
		<template data="家庭成员">
			<van-cell>
				<span>家庭成员</span>
			</van-cell>
			<van-cell v-for="item in members" is-link :to=`/people/info/${item.id}`>
				<van-cell value-class="red" :value="item.pName+'(已故)'" v-if="item.isDie === '是'"></van-cell>
				<van-cell :value="item.pName" v-else></van-cell>
			</van-cell>
		</template>
		<van-cell title="是否贫困户" :value="dataInfo.isPoverty"></van-cell>
		<van-cell title="是否列入负面清单" :value="dataInfo.isNegaHou"></van-cell>
		<van-cell title="负面清单例如时间" :value="dataInfo.incTime"></van-cell>
		<van-cell title="列入负面清单备注" :value="dataInfo.negaRemark"></van-cell>
		<van-cell title="是否易地扶贫搬迁" :value="dataInfo.isTransfer"></van-cell>
		<van-cell title="是否有房" :value="dataInfo.isHaveHou"></van-cell>
		<van-cell title="是否有车" :value="dataInfo.isHaveCar"></van-cell>
		<van-cell title="是否国家公职人员" :value="dataInfo.isGwy"></van-cell>
		<van-cell title="是否有工商执照" :value="dataInfo.isHavepet"></van-cell>
		<van-cell title="享受政策" :value="dataInfo.enjoyPol"></van-cell>
		<template v-if="dataInfo.enjoyPol === '危房改造政策'">
			<van-cell title="家庭户类别" :value="dataInfo.houType"></van-cell>
			<van-cell title="年度" :value="dataInfo.hyear"></van-cell>
			<van-cell title="危房等级" :value="dataInfo.dangLevel"></van-cell>
			<van-cell title="补助资金" :value="dataInfo.subMoney"></van-cell>
		</template>
		<template v-if="dataInfo.enjoyPol === '透风漏雨整治'">
			<van-cell title="透风漏雨整治项" :value="dataInfo.draftyLeak"></van-cell>
		</template>
		<van-cell title="入户路是否硬化" :value="dataInfo.isHouseWay"></van-cell>
		<van-cell title="入户路距离" :value="dataInfo.wayDistance"></van-cell>
		<van-cell title="是否低保户" :value="dataInfo.isLowHou"></van-cell>
		<van-cell title="低保保障人数" :value="dataInfo.lowEbjNum"></van-cell>
		<van-cell title="低保保障金" :value="dataInfo.bacMoney"></van-cell>
		<van-cell title="增发补助人数" :value="dataInfo.incSubNum"></van-cell>
		<van-cell title="增发补助金" :value="dataInfo.incSubMoney"></van-cell>
		<van-cell title="家庭人均年收入" :value="dataInfo.nsr"></van-cell>
	</div>
	</div>
</template>
<script>
	module.exports = {
		data() {
			return {
				dataInfo: {},
				members: [],
				show:{
					hidden:false
				}
			}
		},
		activated() {
			this.options = options
			axios.get(UTIL.url(`getPeoInfoById.do?id=${this.$route.params.id}`)).then(response => {
				if (response.data.state === 1) {
					this.dataInfo = response.data.content
					this.members = response.data.members
					this.dataInfo.nsr = (this.dataInfo.jtyrjsr * 12).toFixed(2)
					console.log(this.dataInfo)
				}
			})
		},
		methods: {

		}
	}
</script>
<style scoped>
	.red {
		color: red;
	}
</style>

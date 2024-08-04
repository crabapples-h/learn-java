<template>
	<div data="家庭成员教育信息">
		<template>
			<h4>资助记录</h4>
			<van-cell center title="时间" :value="datainfo.jyYears"></van-cell>
			<van-cell center title="是否享受免费营养餐" :value="datainfo.Nutrition"></van-cell>
			<van-cell center title="是否享受免费消费" :value="datainfo.bzje"></van-cell>
			<van-cell center title="是否享受低收入群体资助" :value="datainfo.Low"></van-cell>
			<van-cell center title="是否享受普通高中教育资助" :value="datainfo.PGxf"></van-cell>
			<van-cell center title="是否享受学前教育资助金额（单位：400元/每生每学期）" :value="datainfo.PreschoolEdu"></van-cell>
			<van-cell center title="是否享受普通高中教育资助" :value="datainfo.JYxqjyzz" v-if="false"></van-cell>
			<van-cell center title="是否享受专项助学金" :value="datainfo.JYxqjyzz" v-if="false"></van-cell>
			<van-cell center title="是否享受学费" :value="datainfo.PGxf"></van-cell>
			<van-cell center title="是否享受书费（单位：200元/每生每学期）" :value="datainfo.PGsf"></van-cell>
			<van-cell center title="是否享受寄宿生住宿补助（单位：250元/每生每学期）" :value="datainfo.PGjssZsbz"></van-cell>
			<van-cell center title="是否享受国家助学金（单位：2000元/每生每年）" :value="datainfo.PGgjzxj"></van-cell>
			<van-cell center title="是否享受中职补助" :value="datainfo.ZZjyBz"></van-cell>
			<van-cell center title="扶贫专项助学金（单位：500/每生每学期）" :value="datainfo.ZZfpzxzxj"></van-cell>
			<van-cell center title="书费（单位：200元/每生每学期）" :value="datainfo.ZZsf"></van-cell>
			<van-cell center title="寄宿生住宿补助（单位：250元/每生每学期）" :value="datainfo.ZZjsszsbz"></van-cell>
			<van-cell center title="国家助学金（单位：2000元/每生每年）" :value="datainfo.ZZgjzxj"></van-cell>
			<van-cell center title="是否享受高等教育资助" :value="datainfo.GJjybz"></van-cell>
			<van-cell center title="扶贫专项助学金（单位：500/每生每学期）" :value="datainfo.GJfpzxzxj"></van-cell>
			<van-cell center title="专科补助学费（单位：1750元/每生每学期）" :value="datainfo.GJzkbzxf"></van-cell>
			<van-cell center title="本科补助学费（单位：1915元/每生每学期）" :value="datainfo.GJbkbzxf"></van-cell>
			<van-cell center title="国家助学金（单位：2500-3500元/每生每年）" :value="datainfo.GJgjzxj"></van-cell>
			<van-cell center title="是否享受研究生资助" :value="datainfo.YJSzz"></van-cell>
			<van-cell center title="国家助学金（博士/10000元）" :value="datainfo.YJSgjzxjbs"></van-cell>
			<van-cell center title="国家助学金（硕士/6000元）" :value="datainfo.YJSgjzxjss"></van-cell>
			<van-cell center title="是否享受教育精准扶贫六条措施" :value="datainfo.JYjzfp"></van-cell>
			<van-cell center title="是否享受学前教育资助" :value="datainfo.JYxqjyzz"></van-cell>
			<van-cell center title="资助金（标准：1000元/每生每年）" :value="datainfo.JYxqjyzzje"></van-cell>
			<van-cell center title="保教育费（标准：600元/每生每年）" :value="datainfo.JYxqjyzzbyf"></van-cell>
			<van-cell center title="是否享受义务教育资助" :value="datainfo.JYywjyzz"></van-cell>
			<van-cell center title="小学生活补助（标准：1000元/每生每年）" :value="datainfo.JYywjyzzxxshbz"></van-cell>
			<van-cell center title="小学生寄宿生活补助（标准：1000元/每生每年）" :value="datainfo.JYywjyzzxxjsshbz"></van-cell>
			<van-cell center title="中学生活补助（标准：1250元/每生每年）" :value="datainfo.JYywjyzzzxjsshbz"></van-cell>
			<van-cell center title="是否享受普通高中资助" :value="datainfo.JYpgzz"></van-cell>
			<van-cell center title="助学金（标准：2000元/每生每年）、教辅、扶贫资助、免除学杂" :value="datainfo.JYpgzzzxj"></van-cell>
			<van-cell center title="是否享受中职资助" :value="datainfo.JYzzzz"></van-cell>
			<van-cell center title="助学金（标准：2000元/每生每年）、教辅、扶贫资助、免除学杂" :value="datainfo.JYzzzzzxj"></van-cell>
			<van-cell center title="是否享受高等教育资助" :value="datainfo.JYgjzz"></van-cell>
			<van-cell center title="助学贷款（标准：8000元/每生每年）" :value="datainfo.JYgjzzdk"></van-cell>
			<van-cell center title="本科助学金（标准：8000元/每生每年）" :value="datainfo.JYgjzzbkzzj"></van-cell>
			<van-cell center title="专科（标准：4830元/每生每年）" :value="datainfo.JYgjzzzkzzj"></van-cell>
			<van-cell center title="录取省外内院校新生入学资助金（标准：500元）" :value="datainfo.JYgjzzsnyx"></van-cell>
			<van-cell center title="录取省外内院校新生入学资助金（标准：1000元）" :value="datainfo.JYgjzzswyx"></van-cell>
			<van-cell center title="报往返学校四次车票（标准：每生每年）" :value="datainfo.JYgjzzwfxx"></van-cell>
		</template>
</template>
</div>
</template>

<script>
	module.exports = {
		data() {
			return {
				minDate: new Date(1900, 0, 1),
				options: {},
				show: {
					jyYears: false
				}
			}
		},
		props: {
			datainfo: {
				type: Object,
				default: () => null
			},
			submit: {
				type: Function,
			},
			remove: {
				type: Function,
			},
		},
		mounted() {
			this.options = options
		},
		methods: {
			formatTime() {
				var date = this.datainfo.jyYears
				this.datainfo.jyYears = new moment(date).format('YYYY-MM-DD')
			},
			saveData() {
				this.$emit('submit', this.datainfo)
			},
			delData(index) {
				this.$emit('remove')
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

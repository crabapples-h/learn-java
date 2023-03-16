<template>
	<div data="家庭成员基本详细信息" id="parent">
		<template v-if="datainfo != null">
			<template data="居民头像">
				<van-cell>
					<span>居民头像</span>
				</van-cell>
				<van-cell :border="false">
					<img class="mui-media-object" :src="url(datainfo.peoPhoto)" style="width: 100%;"
						@touchstart="downloadImg(datainfo.peoPhoto)" @touchend="touchEnd" />
				</van-cell>
			</template>
			<van-cell title="姓名" value-class="red" :value="datainfo.pName+'(已故)'" v-if="isdie"></van-cell>
			<van-cell title="姓名" :value="datainfo.pName" v-else></van-cell>
			<van-cell title="性别" :value="datainfo.Sex"></van-cell>
			<van-cell title="身份证号" :value="datainfo.ICard"></van-cell>
			<van-cell title="与户主关系" :value="datainfo.Relation"></van-cell>
			<van-cell title="民族" :value="datainfo.Nation"></van-cell>
			<van-cell title="政治面貌" :value="datainfo.Political"></van-cell>
			<van-cell title="学历" :value="datainfo.Education"></van-cell>
			<van-cell title="出身日期" :value="datainfo.Birth"></van-cell>
			<van-cell title="婚姻状况" :value="datainfo.Marriage"></van-cell>
			<van-cell title="初婚时间" :value="datainfo.FristMarr"></van-cell>
			<van-cell title="二婚时间" :value="datainfo.SecMarr"></van-cell>
			<van-cell title="婚变时间" :value="datainfo.MarrCrisis"></van-cell>
			<van-cell title="婚姻备注" :value="datainfo.MarrRemark"></van-cell>
			<van-cell title="户籍地" :value="datainfo.Domicile"></van-cell>
			<van-cell title="户籍类别" :value="datainfo.DomicileType"></van-cell>
			<van-cell title="现居住地" :value="datainfo.NowLive"></van-cell>
			<van-cell title="是否外地居住" :value="datainfo.IsNonLive"></van-cell>
			<van-cell title="文化户口编号" :value="datainfo.DomCode"></van-cell>
			<van-cell title="联系电话" :value="datainfo.peoTel"></van-cell>
			<van-cell title="是否离任村干部" :value="datainfo.IsOgCadre"></van-cell>
			<van-cell title="是否三留人员" :value="datainfo.isSlr"></van-cell>
			<van-cell title="监护人" :value="datainfo.jhr"></van-cell>
			<van-cell title="监护人身份证" :value="datainfo.jhrSfz"></van-cell>
			<van-cell title="监护人电话" :value="datainfo.hjrDh"></van-cell>
			<van-cell title="是否享受低保" :value="datainfo.sfxsdb"></van-cell>
		</template>
	</div>
</template>

<script>
	module.exports = {
		data() {
			return {}
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

		},
		mounted() {
			this.options = options
		},
		updated() {
			const viewer = window.Viewer
			var view = new viewer(document.getElementById('parent'), {
				movable: false,
				navbar: false,
				title: false,
				toolbar: false,
			})
		},
		methods: {
			touchEnd() {
				clearTimeout(this.loop); //清空定时器，防止重复注册定时器
			},
			downloadImg(img) {
				let Url = this.url(img)
				clearTimeout(this.loop);
				this.loop = setTimeout(() => {
					var blob = new Blob([''], {
						type: 'application/octet-stream'
					});
					var url = URL.createObjectURL(blob);
					var a = document.createElement('a');
					a.href = Url;
					a.download = Url.replace(/(.*\/)*([^.]+.*)/ig, "$2").split("?")[0];
					var e = document.createEvent('MouseEvents');
					e.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false,
						false,
						0, null);
					a.dispatchEvent(e);
					URL.revokeObjectURL(url);
				}, 1000);
			},
			url(url) {
				return UTIL.url(url)
			},

		}
	}
</script>

<style>
	.red {
		color: red;
	}

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

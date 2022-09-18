<!-- 车辆信息 -->
<template>
	<div class="option" style="margin-top: 6rem;" id="parent">
		<div class="row-item" v-for="(item,index) in dataInfo">
			<van-cell center title="车主" :value="item.owner"></van-cell>
			<van-cell center title="车辆类型" :value="item.carType"></van-cell>
			<van-cell center title="牌照号" :value="item.carLicense"></van-cell>
			<van-cell center title="备注" :value="item.remark"></van-cell>
			<template data="照片">
				<van-cell center :border="false">
					<span>照片</span>
				</van-cell>
				<van-cell center :border="false">
					<img class="mui-media-object" :src="url(item.image)" style="width: 100%;"
						@touchstart="downloadImg(item.image)" @touchend="touchEnd" />
				</van-cell>
			</template>
		</div>
</template>
<script>
	module.exports = {
		data() {
			return {
				dataInfo: [],
			}
		},
		activated() {
			this.options = options
			axios.get(UTIL.url(`/car/getByPeoId.do?pid=${this.$route.params.id}`)).then(response => {
				this.dataInfo = response.data.content
			})
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
		},
	}
</script>
<style scoped>
	.row {
		background-color: #fff;
		margin-top: 5px;
	}

	.saveData {
		position: fixed;
		top: 5.25rem;
		z-index: 999;
		right: 10px;
		background-color: none;
	}

	.row-item {
		padding: 10px 1.25rem
	}
</style>

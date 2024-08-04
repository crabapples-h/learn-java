<!-- 证件卡包 -->
<template>
	<div class="option" style="margin-top: 6rem;" id="parent">
		<template v-for="(item,index) in dataInfo">
			<van-cell center title="所属人" :value="item.owner"></van-cell>
			<van-cell center title="卡号" :value="item.number"></van-cell>
			<van-cell center title="证件类型" :value="item.type"></van-cell>
			<template data="证件照片">
				<van-cell>
					<span>证件照片</span>
				</van-cell>
				<van-cell :border="false">
					<img class="mui-media-object" :src="url(item.image)" style="width: 100%;"
						@touchstart="downloadImg(item.image)" @touchend="touchEnd" />
				</van-cell>
			</template>
		</template>
	</div>
</template>
<script>
	module.exports = {
		data() {
			return {
				options: {},
				dataInfo: [],
				loop: {},
			}
		},
		activated() {
			this.options = options
			axios.get(UTIL.url(`picture/getAllByPid.do?pid=${this.$route.params.id}`)).then(response => {
				this.dataInfo = response.data
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
</style>

<!-- 房屋信息 -->
<template>
	<div class="option" style="margin-top: 6rem;" id="parent">
		<div style="padding: 10px" v-for="(item,index) in houseList">
			<p>第{{index+1}}房产</p>
			<van-cell center title="户主姓名" :value="familyInfo.pName"></van-cell>
			<van-cell center title="户主身份证号" :value="familyInfo.icard"></van-cell>
			<template data="家庭住址">
				<van-cell :border="false">
					<span>家庭住址</span>
				</van-cell>
				<van-cell :border="false">
					<div :id="'map'+index" class="maps"></div>
				</van-cell>
			</template>
			<van-cell center title="海拔" :value="item.elevation"></van-cell>
			<van-cell center title="房屋坐标(x,y)" :value="item.ex+','+item.ey"></van-cell>
			<van-cell center title="地址" :value="item.haddress"></van-cell>
			<van-cell center title="结构" :value="item.hstruc"></van-cell>
			<van-cell center title="安全等级" :value="item.safety"></van-cell>
			<van-cell center title="是否装修" :value="item.isDec"></van-cell>
			<template data="房屋照片">
				<van-cell :border="false">
					<span>房屋照片</span>
				</van-cell>
				<template>
					<van-cell>
						<img class="mui-media-object" :src="url(item.rePicOne)"
							style="max-width: 8rem; max-height: 8rem;" @touchstart="downloadImg(item.rePicOne)"
							@touchend="touchEnd" />
					</van-cell>
					<van-cell>
						<img class="mui-media-object" :src="url(item.rePicTwo)"
							style="max-width: 8rem; max-height: 8rem;" @touchstart="downloadImg(item.rePicTwo)"
							@touchend="touchEnd" />
					</van-cell>
					<van-cell>
						<img class="mui-media-object" :src="url(item.rePicThree)"
							style="max-width: 8rem; max-height: 8rem;" @touchstart="downloadImg(item.rePicThree)"
							@touchend="touchEnd" />
					</van-cell>
					<van-cell>
						<img class="mui-media-object" :src="url(item.afterRePicOne)"
							style="max-width: 8rem; max-height: 8rem;" @touchstart="downloadImg(item.afterRePicOne)"
							@touchend="touchEnd" />
					</van-cell>
					<van-cell>
						<img class="mui-media-object" :src="url(item.afterRePicTwo)"
							style="max-width: 8rem; max-height: 8rem;" @touchstart="downloadImg(item.afterRePicTwo)"
							@touchend="touchEnd" />
					</van-cell>
					<van-cell>
						<img class="mui-media-object" :src="url(item.afterRePicThree)"
							style="max-width: 8rem; max-height: 8rem;" @touchstart="downloadImg(item.afterRePicThree)"
							@touchend="touchEnd" />
					</van-cell>
				</template>
			</template>
			<van-cell center title="厨房排风设施" :value="item.facility"></van-cell>
			<template data="厨房排风设施照片">
				<template>
					<van-cell>
						<span>厨房排风设施照片</span>
					</van-cell>
					<van-cell :border="false">
						<img class="mui-media-object" :src="url(item.facilityPic)"
							style="max-width: 8rem; max-height: 8rem;" @touchstart="downloadImg(item.facilityPic)"
							@touchend="touchEnd" />
					</van-cell>
				</template>
			</template>
			<van-cell center title="主要燃料" :value="item.fuelType"></van-cell>
			<template data="主要燃料照片">
				<template>
					<van-cell :border="false">
						<span>主要燃料照片</span>
					</van-cell>
					<van-cell :border="false">
						<img class="mui-media-object" :src="url(item.fuelTypePic)"
							style="max-width: 8rem; max-height: 8rem;" @touchstart="downloadImg(item.fuelTypePic)"
							@touchend="touchEnd" />
					</van-cell>
				</template>
			</template>
			<van-cell center title="取水类型" :value="item.selfWater"></van-cell>
			<template data="取水照片">
				<template>
					<van-cell :border="false">
						<span>取水照片</span>
					</van-cell>
					<van-cell :border="false">
						<img class="mui-media-object" :src="url(item.waterPic)"
							style="max-width: 8rem; max-height: 8rem;" @touchstart="downloadImg(item.waterPic)"
							@touchend="touchEnd" />
					</van-cell>
				</template>
			</template>
			<van-cell center title="厕所类型" :value="item.wc"></van-cell>
			<template data="厕所照片">
				<template>
					<van-cell :border="false">
						<span>厕所照片</span>
					</van-cell>
					<van-cell :border="false">
						<img class="mui-media-object" :src="url(item.wcpic)" style="max-width: 8rem; max-height: 8rem;"
							@touchstart="downloadImg(item.wcpic)" @touchend="touchEnd" />
					</van-cell>
				</template>
			</template>
		</div>
	</div>

	</div>
</template>
<script>
	module.exports = {
		data() {
			return {
				familyInfo: {},
				houseList: [],
				loop: null,
			}
		},
		activated() {
			const _this = this
			axios.get(UTIL.url(`getPeoInfoById.do?id=${_this.$route.params.id}`)).then(response => {
				console.log(response.data)
				if (response.data.state === 1) {
					_this.familyInfo = response.data.content
				}
			})
			const url = UTIL.url(`house/getByPid.do?pid=${_this.$route.params.id}`)
			axios.get(url).then(response => {
				_this.houseList = response.data
				console.log(_this.houseList)
				_this.houseList.forEach((e, index) => {
					_this.$nextTick(() => {
						var map = new BMapGL.Map(`map${index}`);
						map.enableScrollWheelZoom(true);
						if (!!e.ex && !!e.ey) {
							map.centerAndZoom(new BMapGL.Point(e.ex, e.ey), 14);
							var marker = new BMapGL.Marker(new BMapGL.Point(e.ex, e.ey));
							map.addOverlay(marker);
						} else {
							map.centerAndZoom(new BMapGL.Point(104.893715, 26.450781), 15);
						}
					})
				})
			})
			axios.get(UTIL.url('getArea.do')).then(response => {
				this.areas = response.data
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
				let Url = UTIL.url(img)
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
<style scoped>
	.mui-btn-block-location {
		width: 47%;
		display: inline-block;
	}

	.maps {
		overflow: hidden;
		width: 100%;
		height: 200px;
		margin: 0;
		font-family: "微软雅黑";
	}

	.saveData {
		position: fixed;
		top: 5.25rem;
		z-index: 999;
		right: 10px;
		background-color: none;
	}

	.mui-btn-block {
		height: 25px;
	}
</style>

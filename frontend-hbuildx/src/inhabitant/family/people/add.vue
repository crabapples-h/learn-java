<!-- 添加家庭成员 -->
<template>
	<div class="option" style="margin-top: 6rem;">
		<template>
			<button class="saveData mui-btn-primary" @click="saveData">确定</button>
		</template>
		<h4>添加居民</h4>
		<template data="居民头像">
			<div class="row-item">
				<span style="font-size: 1rem; color: #666666;">居民头像：</span>
				<li class="mui-table-view-cell " v-if="datainfo.peoPhoto">
					<img class="mui-media-object" :src="url(datainfo.peoPhoto)" data-preview-src=""
						data-preview-group="1" style="width: 100%;" />
					<span class="mui-icon mui-icon-trash deleteBtn" @click="deleteImg"
						style="background-color:#DDDDDD; top: 0.75rem; right: 0; position: absolute;z-index:1;"></span>
				</li>
				<div @click="openFiles" v-if="!datainfo.peoPhoto"
					style="text-align: center; color: #0062CC; border: #0062CC 0.0625rem solid; background-color: #FFFFFF;">
					<span class="mui-icon mui-icon-image" style="font-size: 1.5rem;"></span>
					<span>上传</span>
				</div>
				<br>
			</div>
		</template>
		<template data="密码">
			<div class="row-item">
				<span>智慧勺米登录密码(不填默认为123456)</span>
				<div>
					<input v-model="datainfo.password" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="联系电话">
			<div class="row-item">
				<span>联系电话</span>
				<div>
					<input v-model="datainfo.peoTel" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="居民姓名">
			<div class="row-item">
				<span>姓名</span>
				<div>
					<input v-model="datainfo.pName" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="与户主关系">
			<div class="row-item">
				<span>与户主关系</span>
				<select class="mui-btn" v-model="datainfo.Relation" style="text-align-last: center;">
					<option value="">-与户主关系-</option>
					<option :value="item" v-for="item in options.relations">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="性别">
			<div class="row-item">
				<span>性别</span>
				<select class="mui-btn" v-model="datainfo.Sex" style="text-align-last: center;">
					<option value="">-性别-</option>
					<option :value="item" v-for="item in options.sexs">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="民族">
			<div class="row-item">
				<span>民族</span>
				<select class="mui-btn" v-model="datainfo.Nation" style="text-align-last: center;">
					<option value="">-民族-</option>
					<option :value="item" v-for="item in options.mzs">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="政治面貌">
			<div class="row-item">
				<span>政治面貌</span>
				<select class="mui-btn" v-model="datainfo.Political" style="text-align-last: center;">
					<option value="">-政治面貌-</option>
					<option :value="item" v-for="item in options.zzmms">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="学历">
			<div class="row-item">
				<span>学历</span>
				<select class="mui-btn" v-model="datainfo.Education" style="text-align-last: center;">
					<option value="">-学历-</option>
					<option :value="item" v-for="item in options.xls">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="生日">
			<div class="row-item">
				<span>生日</span>
				<div>
					<input class="mui-btn" @click="show.time= true" v-model="datainfo.Birth" style="width: 100%;"
						readonly />
				</div>
			</div>
			<van-popup v-model:show="show.time" position="bottom">
				<van-datetime-picker :show-toolbar="false" type="date" v-model="datainfo.Birth" @change="formatTime" :min-date="minDate">
				</van-datetime-picker>
			</van-popup>
		</template>
		
		<template data="是否有身份证">
			<div class="row-item">
				<span>是否有身份证</span>
				<select class="mui-btn" v-model="datainfo.isICard" style="text-align-last: center;">
					<option value="">-是否有身份证-</option>
					<option :value="item" v-for="item in options.yesorno">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="身份证" v-if='datainfo.isICard==="是"'>
			<div class="row-item">
				<span>身份证</span>
				<div>
					<input v-model="datainfo.ICard" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="无身份证原因" v-if='datainfo.isICard==="否"'>
			<div class="row-item">
				<span>无身份证原因</span>
				<div>
					<input v-model="datainfo.noICard" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="婚姻状况">
			<div class="row-item">
				<span>婚姻状况</span>
				<select class="mui-btn" v-model="datainfo.Marriage" style="text-align-last: center;">
					<option value="">-婚姻状况-</option>
					<option :value="item" v-for="item in options.hyzks">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="初婚时间">
			<div class="row-item">
				<span>初婚时间</span>
				<div>
					<input class="mui-btn" @click="show.FristMarr= true" v-model="datainfo.FristMarr"
						style="width: 100%;" readonly />
				</div>
			</div>
			<van-popup v-model:show="show.FristMarr" position="bottom">
				<van-datetime-picker :show-toolbar="false" type="date" v-model="datainfo.FristMarr"
					@change="formatTime1" :min-date="minDate">
				</van-datetime-picker>
			</van-popup>
		</template>
		<template data="二婚时间">
			<div class="row-item">
				<span>二婚时间</span>
				<div>
					<input class="mui-btn" @click="show.SecMarr= true" v-model="datainfo.SecMarr" style="width: 100%;"
						readonly />
				</div>
			</div>
			<van-popup v-model:show="show.SecMarr" position="bottom">
				<van-datetime-picker :show-toolbar="false" type="date" v-model="datainfo.SecMarr" @change="formatTime2" :min-date="minDate">
				</van-datetime-picker>
			</van-popup>
		</template>
		<template data="婚变时间">
			<div class="row-item">
				<span>婚变时间</span>
				<div>
					<input class="mui-btn" @click="show.MarrCrisis= true" v-model="datainfo.MarrCrisis"
						style="width: 100%;" readonly />
				</div>
			</div>
			<van-popup v-model:show="show.MarrCrisis" position="bottom">
				<van-datetime-picker :show-toolbar="false" type="date" v-model="datainfo.MarrCrisis"
					@change="formatTime3" :min-date="minDate">
				</van-datetime-picker>
			</van-popup>
		</template>
		<template data="婚姻备注">
			<div class="row-item">
				<span>婚姻备注</span>
				<div>
					<input v-model="datainfo.MarrRemark" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="现居住地">
			<div class="row-item">
				<span>现居住地</span>
				<div>
					<input v-model="datainfo.NowLive" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="是否是外地居住">
			<div class="row-item">
				<span>是否是外地居住</span>
				<select class="mui-btn" v-model="datainfo.IsNonLive" style="text-align-last: center;">
					<option value="">-是否是外地居住-</option>
					<option :value="item" v-for="item in options.yesorno">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="外地居住时间">
			<div class="row-item">
				<span>外地居住时间</span>
				<div>
					<input class="mui-btn" @click="show.NonLiveTime= true" v-model="datainfo.NonLiveTime"
						style="width: 100%;" readonly />
				</div>
			</div>
			<van-popup v-model:show="show.NonLiveTime" position="bottom">
				<van-datetime-picker :show-toolbar="false" type="date" v-model="datainfo.NonLiveTime"
					@change="formatTime4"  :min-date="minDate">
				</van-datetime-picker>
			</van-popup>
		</template>
		<template data="是否离任村干部">
			<div class="row-item">
				<span>是否离任村干部</span>
				<select class="mui-btn" v-model="datainfo.IsOgCadre" style="text-align-last: center;">
					<option value="">-是否离任村干部-</option>
					<option :value="item" v-for="item in options.yesorno">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="是否迁出">
			<div class="row-item">
				<span>是否迁出</span>
				<select class="mui-btn" v-model="datainfo.IsOut" style="text-align-last: center;">
					<option value="">-是否迁出-</option>
					<option :value="item" v-for="item in options.yesorno">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="危房等级" v-if="false">
			<div class="row-item">
				<span>危房等级</span>
				<select class="mui-btn" v-model="datainfo.DangLevel" style="text-align-last: center;">
					<option value="">-危房等级-</option>
					<option :value="item" v-for="item in options.wfdjs">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="补助资金" v-if="false">
			<div class="row-item">
				<span>补助资金</span>
				<div>
					<input v-model="datainfo.SubMoney" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="透风漏雨整治项" v-if="false">
			<div class="row-item">
				<span>透风漏雨整治项</span>
				<select class="mui-btn" v-model="datainfo.DraftyLeak" style="text-align-last: center;">
					<option value="">-透风漏雨整治项-</option>
					<option :value="item" v-for="item in options.wfdjs">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="电话">
			<div class="row-item">
				<span>电话</span>
				<div>
					<input v-model="datainfo.tel" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="户id" v-if="false">
			<div class="row-item">
				<span>户id</span>
				<div>
					<input v-model="datainfo.pid" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="是否三留人员">
			<div class="row-item">
				<span>是否三留人员</span>
				<select class="mui-btn" v-model="datainfo.Sfslry" style="text-align-last: center;">
					<option value="">-是否三留人员-</option>
					<option :value="item" v-for="item in options.yesorno">{{item}}</option>
				</select>
			</div>
		</template>
		<template v-if='datainfo.Sfslry==="是"'>
			<template data="三留人员监护人">
				<div class="row-item">
					<span>三留人员监护人</span>
					<div>
						<input v-model="datainfo.Slryjhr" class="mui-btn " style="width: 100%;" />
					</div>
				</div>
			</template>
			<template data="三留人员监护人身份证号">
				<div class="row-item">
					<span>三留人员监护人身份证号</span>
					<div>
						<input v-model="datainfo.Slryjhrsfz" class="mui-btn " style="width: 100%;" />
					</div>
				</div>
			</template>
			<template data="三留人员监护人电话">
				<div class="row-item">
					<span>三留人员监护人电话</span>
					<div>
						<input v-model="datainfo.Slryjhrdh" class="mui-btn " style="width: 100%;" />
					</div>
				</div>
			</template>
		</template>
		<template data="监护人姓名">
			<div class="row-item">
				<span>监护人姓名</span>
				<div>
					<input v-model="datainfo.jhr" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="监护人身份证">
			<div class="row-item">
				<span>监护人身份证</span>
				<div>
					<input v-model="datainfo.jhrSfz" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="监护人电话">
			<div class="row-item">
				<span>监护人电话</span>
				<div>
					<input v-model="datainfo.hjrDh" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="家庭月人均收入">
			<div class="row-item">
				<span>家庭月人均收入</span>
				<div>
					<input v-model="datainfo.Jtyrjsr" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="月保障金额">
			<div class="row-item">
				<span>月保障金额</span>
				<div>
					<input v-model="datainfo.Ybzje" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="离村干部任职时间">
			<div class="row-item">
				<span>离村干部任职时间</span>
				<div>
					<input class="mui-btn" @click="show.Lcgbrzsj= true" v-model="datainfo.Lcgbrzsj" style="width: 100%;"
						readonly />
				</div>
			</div>
			<van-popup v-model:show="show.Lcgbrzsj" position="bottom">
				<van-datetime-picker :show-toolbar="false" type="date" v-model="datainfo.Lcgbrzsj"
					@change="formatTime5"  :min-date="minDate">
				</van-datetime-picker>
			</van-popup>
		</template>
		<template data="离村干部任职职位">
			<div class="row-item">
				<span>离村干部任职职位</span>
				<div>
					<input v-model="datainfo.Lcgbrzzw" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template>
		<template data="是否是享受低保">
			<div class="row-item">
				<span>是否是享受低保</span>
				<select class="mui-btn" v-model="datainfo.sfxsdb" style="text-align-last: center;">
					<option value="">-是否是享受低保-</option>
					<option :value="item" v-for="item in options.yesorno">{{item}}</option>
				</select>
			</div>
		</template>
		<!-- <template data="年龄">
			<div class="row-item">
				<span>年龄</span>
				<div>
					<input v-model="datainfo.age" class="mui-btn " style="width: 100%;" />
				</div>
			</div>
		</template> -->
		<template data="是否是学生">
			<div class="row-item">
				<span>是否是学生</span>
				<select class="mui-btn" v-model="datainfo.isStudent" style="text-align-last: center;">
					<option value="">-是否是学生-</option>
					<option :value="item" v-for="item in options.yesorno">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="是否外出务工">
			<div class="row-item">
				<span>是否外出务工</span>
				<select class="mui-btn" v-model="datainfo.isOuterWorker" style="text-align-last: center;">
					<option value="">-是否外出务工-</option>
					<option :value="item" v-for="item in options.yesorno">{{item}}</option>
				</select>
			</div>
		</template>
	</div>
</template>
<script>
	module.exports = {
		data() {
			return {
				minDate:new Date(1900, 0, 1),
				show: {
					time: false,
					FristMarr: false,
					SecMarr: false,
					MarrCrisis: false,
					NonLiveTime: false,
					Lcgbrzsj: false,
				},
				options: {},
				datainfo: {
					peoPhoto: '',
					password: '',
					peoTel: '',
					pName: '',
					Relation: '',
					Sex: '',
					Nation: '',
					Political: '',
					Education: '',
					Birth: '',
					isICard: '',
					ICard: '',
					noICard: '',
					Marriage: '',
					FristMarr: '',
					SecMarr: '',
					MarrCrisis: '',
					MarrRemark: '',
					NowLive: '',
					IsNonLive: '',
					NonLiveTime: '',
					IsOgCadre: '',
					IsOut: '',
					// DangLevel: '',
					// SubMoney: '',
					// DraftyLeak: '',
					tel: '',
					pid: '',
					jhr: '',
					jhrSfz: '',
					hjrDh: '',
					Jtyrjsr: '',
					Ybzje: '',
					Lcgbrzsj: '',
					Lcgbrzzw: '',
					Sfslry: '',
					Slryjhr: '',
					Slryjhrsfz: '',
					Slryjhrdh: '',
					sfxsdb: '',
					// onlyId: '',
					// age: 0,
					isStudent: '',
					isOuterWorker: '',
				},
			}
		},
		activated() {
			this.options = options
		},
		methods: {
			saveData() {
				let url = UTIL.url(`/member/save_or_update.do`)
				this.datainfo.pid = this.$route.params.id - 0
				this.datainfo.age -= 0
				console.log(this.datainfo)
				axios.post(url, this.datainfo).then(response => {
					if(response.data.state === 1){
						alert('添加成功')
						window.history.back(-1)
					}
				})
			},
			url(url) {
				return UTIL.url(url)
			},
			formatTime() {
				var date = this.datainfo.Birth
				this.datainfo.Birth = new moment(date).format('YYYY-MM-DD')
			},
			formatTime1() {
				var date = this.datainfo.FristMarr
				this.datainfo.FristMarr = new moment(date).format('YYYY-MM-DD')
			},
			formatTime2() {
				var date = this.datainfo.SecMarr
				this.datainfo.SecMarr = new moment(date).format('YYYY-MM-DD')
			},
			formatTime3() {
				var date = this.datainfo.MarrCrisis
				this.datainfo.MarrCrisis = new moment(date).format('YYYY-MM-DD')
			},
			formatTime4() {
				var date = this.datainfo.NonLiveTime
				this.datainfo.NonLiveTime = new moment(date).format('YYYY-MM-DD')
			},
			formatTime5() {
				var date = this.datainfo.Lcgbrzsj
				this.datainfo.Lcgbrzsj = new moment(date).format('YYYY-MM-DD')
			},
			openFiles() {
				const _this = this
				if (mui.os.plus) {
					var buttonTit = [{
						title: "拍照"
					}, {
						title: "从手机相册选择"
					}];
					plus.nativeUI.actionSheet({
						title: "上传图片",
						cancel: "取消",
						buttons: buttonTit
					}, function(b) {
						/*actionSheet 按钮点击事件*/
						switch (b.index) {
							case 0:
								break;
							case 1:
								_this.getImage(); /*拍照*/
								break;
							case 2:
								_this.galleryImg(); /*打开相册*/
								break;
							default:
								break;
						}
					})
				}
			},
			getImage() {
				const _this = this
				var c = plus.camera.getCamera();
				c.captureImage(function(e) {
					plus.io.resolveLocalFileSystemURL(e, function(entry) {
						var imgSrc = entry.toLocalURL() + "?version=" + new Date()
							.getTime();
						_this.uploadImg(imgSrc)
					}, function(e) {
						console.log("读取拍照文件错误：" + e.message);
					});
				}, function(s) {
					console.log("error" + s.message);
				}, {
					filename: "_doc/camera/"
				})
			},
			galleryImg() {
				const _this = this
				plus.gallery.pick(function(e) {
					_this.uploadImg(e)
				}, function(e) {
					console.log("取消选择图片");
				}, {
					filter: "image",
					multiple: false,
					system: false,
				});
			},
			uploadImg(file) {
				const _this = this
				var image = new Image();
				image.src = file
				var wt = plus.nativeUI.showWaiting();
				wt.close();
				var task = plus.uploader.createUpload(UTIL.url('global_file_upload.do'), {
						method: "POST"
					},
					function(t, status) { //上传完成
						if (status == 200) {
							let fileUrl = (JSON.parse(t.responseText)).content
							console.log('fileUrl', fileUrl)
							_this.datainfo.peoPhoto = fileUrl
							wt.close(); //关闭等待提示按钮
						} else {
							alert('上传失败')
							wt.close(); //关闭等待提示按钮
						}
					}
				)

				task.addFile(image.src, {
					key: `file`
				});
				task.start();
			},
			deleteImg() {
				this.datainfo.peoPhoto = ''
			},
		}
	}
</script>
<style scoped>
	.mui-table-view-cell {
		border: 1px solid #f1f1f1;
	}

	.row-item {
		padding: 10px 1.25rem
	}

	.saveData {
		position: fixed;
		top: 5.20rem;
		z-index: 999;
		right: 10px;
		background-color: none;
	}
</style>

<template>
	<div data="家庭成员教育信息">
		<template v-if="datainfo != null">
			<p>黑名单信息</p>
			<template data="户主姓名">
				<div class="row-item">
					<span>户主姓名</span>
					<div>
						<input disabled :value="datainfo.name+'(已故)'" class="mui-btn " style="width: 100%;color:red" v-if="isdie"/>
						<input disabled :value="datainfo.name" class="mui-btn " style="width: 100%;" v-else />
					</div>
				</div>
			</template>
			<template data="户主身份证号">
				<div class="row-item">
					<span>户主身份证号</span>
					<div>
						<input v-model="datainfo.idCard" class="mui-btn " style="width: 100%;" readonly />
					</div>
				</div>
			</template>
			<template data="户主性别">
				<div class="row-item">
					<span>户主性别</span>
					<div>
						<input v-model="datainfo.gender" class="mui-btn " style="width: 100%;" readonly />
					</div>
				</div>
			</template>
			<template data="家庭人口">
				<div class="row-item">
					<span>家庭人口</span>
					<div>
						<input v-model="datainfo.families" class="mui-btn " style="width: 100%;" readonly />
					</div>
				</div>
			</template>
			<template data="列入黑名单时间">
				<div class="row-item">
					<span>列入黑名单时间</span>
					<div>
						<input v-model="datainfo.blacklistTime" class="mui-btn " style="width: 100%;" readonly />
					</div>
				</div>
			</template>
			<template data="列入黑名单原因">
				<div class="row-item">
					<span>列入黑名单原因</span>
					<div>
						<textarea v-model="datainfo.blacklistReason" class="mui-btn " readonly></textarea>
					</div>
				</div>
			</template>
			<template data="列入黑名单说明">
				<div class="row-item">
					<span>列入黑名单说明</span>
					<div>
						<textarea v-model="datainfo.blacklistRemark" class="mui-btn " readonly></textarea>
					</div>
				</div>
			</template>
			<template data="移除黑名单">
				<div class="row-item">
					<span>移除黑名单</span>
					<input value="移除黑名单" class="mui-btn " style="width: 100%; color: #f00;" readonly
						@click="removeBlack" />
				</div>
			</template>
		</template>
	</div>
</template>

<script>
	module.exports = {
		components: {
		},
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
			remove: {
				type: Function,
			},
		},
		mounted() {
			this.options = options
		},
		methods: {
			toEduList(id) {
				this.$router.push({
					path: `family/info-black/${id}`,
					query: {
						mid: this.datainfo.id,
						cid: this.datainfo.id
					}
				})
			},
			removeBlack() {
				this.$emit('remove', this.datainfo.id)
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

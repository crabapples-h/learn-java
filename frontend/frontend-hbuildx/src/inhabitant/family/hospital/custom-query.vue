<template>
	<div class="option" style="margin-top: 6rem;">
		<template data="是否属于家庭签约对象">
			<div style="padding: 10px 1.25rem">
				<span>是否属于家庭签约对象</span>
				<select class="mui-btn" v-model="params.isJtqy">
					<option value=""> --是否属于家庭签约对象--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="是否家庭医生签约服务" v-if="params.isJtqy==='是'">
			<div style="padding: 10px 1.25rem">
				<span>是否家庭医生签约服务</span>
				<select class="mui-btn" v-model="params.isJtys">
					<option value=""> --是否家庭医生签约服务--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="是否属于重大疾病患者" v-if="params.isJtys==='是'">
			<div style="padding: 10px 1.25rem">
				<span>是否属于重大疾病患者</span>
				<select class="mui-btn" v-model="params.isZdjb">
					<option value=""> --是否属于重大疾病患者--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="重大疾病类型" v-if="params.isZdjb==='是'">
			<div style="padding: 10px 1.25rem">
				<span>重大疾病类型</span>
				<select class="mui-btn" v-model="params.jblx">
					<option value=""> --重大疾病类型--</option>
					<option :value="item.value" v-for="item in options.dbs">{{item.label}}</option>
				</select>
			</div>
		</template>
		<template data="是否享受临时救助">
			<div style="padding: 10px 1.25rem">
				<span>是否享受临时救助</span>
				<select class="mui-btn" v-model="params.isLsjz">
					<option value=""> --是否享受临时救助--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="临时救助类型" v-if="params.isLsjz==='是'">
			<div style="padding: 10px 1.25rem">
				<span>临时救助类型</span>
				<select class="mui-btn" v-model="params.jzlx">
					<option value=""> --临时救助类型--</option>
					<option :value="item" v-for="item in options.jzlxs">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="是否属于残疾人员">
			<div style="padding: 10px 1.25rem">
				<span>是否属于残疾人员</span>
				<select class="mui-btn" v-model="params.isCj">
					<option value=""> --是否属于残疾人员--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="残疾等级" v-if="params.isCj==='是'">
			<div style="padding: 10px 1.25rem">
				<span>残疾等级</span>
				<select class="mui-btn" v-model="params.cjdj">
					<option value=""> --残疾等级--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="是否享受残疾补贴" v-if="params.isCj==='是'">
			<div style="padding: 10px 1.25rem">
				<span>是否享受残疾补贴</span>
				<select class="mui-btn" v-model="params.isCjbt">
					<option value=""> --是否享受残疾补贴--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="是否患有四类慢性病">
			<div style="padding: 10px 1.25rem">
				<span>是否患有四类慢性病</span>
				<select class="mui-btn" v-model="params.isMxb">
					<option value=""> --是否患有四类慢性病--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="选择慢性病类型" v-if="params.isMxb==='是'">
			<div style="padding: 10px 1.25rem">
				<span>选择慢性病类型</span>
				<select class="mui-btn" v-model="params.mxbLx">
					<option value=""> --选择慢性病类型--</option>
					<option :value="item" v-for="item in options.mxbs">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="是否购买医疗保险">
			<div style="padding: 10px 1.25rem">
				<span>是否购买医疗保险</span>
				<select class="mui-btn" v-model="params.isYlbx">
					<option value=""> --是否购买医疗保险--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<div class='mui-button-row'>
			<button type="button" style="width: 12rem" class="mui-btn mui-btn-primary" @click="submit">查找</button>
		</div>
		<br />
	</div>
</template>
<script>
	module.exports = {
		data() {
			return {
				options: {},
				params: {
					isJtqy: '',
					isJtys: '',
					isZdjb: '',
					jblx: '',
					isLsjz: '',
					jzlx: '',
					isCj: '',
					cjdj: '',
					isCjbt: '',
					isMxb: '',
					mxbLx: '',
					isYlbx: '',
				},
			}
		},
		activated() {
			this.options = options
		},
		methods: {
			submit() {
				let param = {
					isJtqydx: this.params.isJtqy,
					isDoctor: this.params.isJtys,
					hasZdjb: this.params.isZdjb,
					jblx: this.params.jblx,
					lsjz: this.params.isLsjz,
					lsyljzlx: this.params.jzlx,
					isCjry: this.params.isCj,
					cjdj: this.params.cjdj,
					cjbzje: this.params.isCjbt,
					isSlmxhz: this.params.isMxb,
					slmxblx: this.params.mxbLx,
					hasYb: this.params.isYlbx,
				}
				localStorage.setItem('hospitalQuery', JSON.stringify(param))
				this.$router.push('/hospital/list')
			}
		}
	}
</script>
<style scoped>
	.mui-btn-block {
		height: 25px;
	}
</style>

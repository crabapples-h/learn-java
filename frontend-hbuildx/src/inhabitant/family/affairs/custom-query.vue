<template>
	<div class="option" style="margin-top: 6rem;">
		<template data="享受高龄津贴">
			<div style="padding: 10px 1.25rem">
				<span>享受高龄津贴</span>
				<select class="mui-btn" v-model="params.isGljt">
					<option value=""> --享受高龄津贴--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="是否退役军人">
			<div style="padding: 10px 1.25rem">
				<span>是否退役军人</span>
				<select class="mui-btn" v-model="params.isTyjr">
					<option value=""> --是否退役军人--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="享受优抚待遇">
			<div style="padding: 10px 1.25rem">
				<span>享受优抚待遇</span>
				<select class="mui-btn" v-model="params.isYfdy">
					<option value=""> --享受优抚待遇--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="是否留守儿童">
			<div style="padding: 10px 1.25rem">
				<span>是否留守儿童</span>
				<select class="mui-btn" v-model="params.isLset">
					<option value=""> --是否留守儿童--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="是否孤儿">
			<div style="padding: 10px 1.25rem">
				<span>是否孤儿</span>
				<select class="mui-btn" v-model="params.isGe">
					<option value=""> --是否孤儿--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="是否享受低保">
			<div style="padding: 10px 1.25rem">
				<span>是否享受低保</span>
				<select class="mui-btn" v-model="params.isXsdb">
					<option value=""> --是否享受低保--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="是否特困供养人员">
			<div style="padding: 10px 1.25rem">
				<span>是否特困供养人员</span>
				<select class="mui-btn" v-model="params.isTkgy">
					<option value=""> --是否特困供养人员--</option>
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
				params: {
					isGljt: '',
					isTyjr: '',
					isYfdy: '',
					isLset: '',
					isGe: '',
					isXsdb: '',
					isTkgy: '',
				},
			}
		},
		activated() {},
		methods: {
			submit() {
				let param = {
					isEnjoyAdAge: this.params.isGljt,
					veterans: this.params.isTyjr,
					isEnjoyMat: this.params.isYfdy,
					isLset: this.params.isLset,
					isGe: this.params.isGe,
					isLowIncome: this.params.isXsdb,
					isTkgyrydx: this.params.isTkgy,
				}
				localStorage.setItem('affairsQuery', JSON.stringify(param))
				this.$router.push('/affairs/list')
			}
		}
	}
</script>
<style scoped>
	.mui-btn-block {
		height: 25px;
	}
</style>

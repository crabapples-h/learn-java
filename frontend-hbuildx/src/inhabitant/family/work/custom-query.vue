<template>
	<div class="option" style="margin-top: 6rem;">
		<template data="就业现状">
			<div style="padding: 10px 1.25rem">
				<span>就业现状</span>
				<select class="mui-btn" v-model="params.jyxz">
					<option value=""> --就业现状--</option>
					<option :value="item" v-for="item in options.jyxzs">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="是否享受创业补贴">
			<div style="padding: 10px 1.25rem">
				<span>是否享受创业补贴</span>
				<select class="mui-btn" v-model="params.isCybt">
					<option value=""> --是否享受创业补贴--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="就业类型">
			<div style="padding: 10px 1.25rem">
				<span>就业类型</span>
				<select class="mui-btn" v-model="params.jylx">
					<option value=""> --就业类型--</option>
					<option :value="item" v-for="item in options.jylxs">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="就业区域">
			<div style="padding: 10px 1.25rem">
				<span>就业区域</span>
				<select class="mui-btn" v-model="params.jyqy">
					<option value=""> --就业区域--</option>
					<option :value="item" v-for="item in options.jyqys">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="是否参加就业培训">
			<div style="padding: 10px 1.25rem">
				<span>是否参加就业培训</span>
				<select class="mui-btn" v-model="params.isJypx">
					<option value=""> --是否参加就业培训--</option>
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</template>
		<template data="培训工种">
			<div style="padding: 10px 1.25rem">
				<span>培训工种</span>
				<select class="mui-btn" v-model="params.pxgz">
					<option value=""> --培训工种--</option>
					<option :value="item" v-for="item in options.pxgzs">{{item}}</option>
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
					jyxz: '',
					isCybt: '',
					jylx: '',
					jyqy: '',
					isJypx: '',
					pxgz: '',
				},
			}
		},
		activated() {
			this.options = options
		},
		methods: {
			submit() {
				let param = {
					jyxz: this.params.jyxz,
					isXscybt: this.params.isCybt,
					cymc: this.params.jylx,
					area: this.params.jyqy,
					isJypx: this.params.isJypx,
					pxgz: this.params.pxgz,
				}
				localStorage.setItem('workQuery', JSON.stringify(param))
				this.$router.push('/work/list')
			}
		}
	}
</script>
<style scoped>
	.mui-btn-block {
		height: 25px;
	}
</style>

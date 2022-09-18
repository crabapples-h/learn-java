<template>
	<div class="option" style="margin-top: 6rem;">
		<template data="是否是在校生">
			<div style="padding: 10px 1.25rem">
				<span>是否是在校生</span>
				<select class="mui-btn" v-model="params.isZxs">
					<option value=""> --是否是在校生--</option>
					<option :value="item" v-for="item in options.yesorno">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="入学适龄儿童">
			<div style="padding: 10px 1.25rem">
				<span>入学适龄儿童</span>
				<select class="mui-btn" v-model="params.isSlet">
					<option :value="item" v-for="item in options.yesorno">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="享受过教育资助">
			<div style="padding: 10px 1.25rem">
				<span>享受过教育资助</span>
				<select class="mui-btn" v-model="params.isJyzz">
					<option :value="item" v-for="item in options.yesorno">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="时间" v-if="false">
			<div style="padding: 10px 1.25rem">
				<span>时间</span>
				<select class="mui-btn " v-model="params.sj">
					<option value="">--选择时间--</option>
					<option :value="item" v-for="item in options.years">{{item}}</option>
				</select>
			</div>
		</template>
		<template data="学历">
			<div style="padding: 10px 1.25rem">
				<span>学历</span>
				<select class="mui-btn" v-model="params.xl">
					<option :value="item" v-for="(item,index) in dataList[0]">{{item}}({{dataList[1][index]}}人)</option>
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
					isZxs: '',
					isSlet: '',
					isJyzz: '',
					sj: '',
					xl: '',
				},
				dataList:[]
			}
		},
		activated() {
			this.options = options
			let cun = JSON.parse(localStorage.getItem('user')).cun
			axios.get(UTIL.url(`/statistics/getEduDistribution.do?village=${cun}`)).then(response=>{
				console.log(response)
				this.dataList = response.data
			})
		},
		methods: {
			submit() {
				let param = {
					isStudent: this.params.isZxs,
					shouldEducated: this.params.isSlet,
					hasZz: this.params.isJyzz,
					year: this.params.sj,
					// xl: this.params.xl,
				}
				localStorage.setItem('eduQuery', JSON.stringify(param))
				this.$router.push('/edu/list')
			}
		}
	}
</script>
<style scoped>
	.mui-btn-block {
		height: 25px;
	}
</style>

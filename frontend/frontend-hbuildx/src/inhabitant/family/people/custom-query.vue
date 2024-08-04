<template>
	<div class="option" style="margin-top: 6rem;">
		<div class="row-item">
			<span>是否在校学生</span>
			<select class="mui-btn " v-model="form.isStudent">
				<option value=""> --是否在校学生--</option>
				<option value="是">是</option>
				<option value="否">否</option>
			</select>
		</div>
		<div class="row-item">
			<span>是否外出务工人员</span>
			<select class="mui-btn " v-model="form.isDgr">
				<option value=""> --是否外出务工人员--</option>
				<option :value="item" v-for="item in options.yesorno">{{item}}</option>
			</select>
		</div>
		<div class="row-item">
			<span>村</span>
			<select class="mui-btn " v-model="form.cun" @change="getZus(form.cun)">
				<option value="">--选择村--</option>
				<option :value="item" v-for="item in options.cuns">{{item}}</option>
			</select>
		</div>
		<div class="row-item" v-if="options.zus.length">
			<span>组</span>
			<select class="mui-btn " v-model="form.zu">
				<option value="">--选择组--</option>
				<option :value="item.Zu" v-for="item in options.zus">{{item.Zu}}</option>
			</select>
		</div>
		<div class="row-item">
			<span>政治面貌</span>
			<select class="mui-btn " v-model="form.zzmm">
				<option value=""> --选择政治面貌--</option>
				<option :value="item" v-for="item in options.zzmms">{{item}}</option>
			</select>
		</div>
		<div class="row-item">
			<span>学历</span>
			<select class="mui-btn " v-model="form.xl">
				<option value=""> --选择学历--</option>
				<option :value="item" v-for="item in options.xls">{{item}}</option>
			</select>
		</div>
		<div class="row-item">
			<span>民族</span>
			<select class="mui-btn " v-model="form.mz">
				<option value=""> --选择民族--</option>
				<option :value="item" v-for="item in options.mzs">{{item}}</option>
			</select>
		</div>
		<div class="row-item">
			<span>性别</span>
			<select class="mui-btn " v-model="form.xb">
				<option value=""> --选择性别--</option>
				<option value="男">男</option>
				<option value="女">女</option>
			</select>
		</div>
		<div class="row-item">
			<span>婚姻状况</span>
			<select class="mui-btn " v-model="form.hyzk">
				<option value=""> --选择婚姻状况--</option>
				<option :value="item" v-for="item in options.hyzks">{{item}}</option>
			</select>
		</div>
		<div class="row-item">
			<span>年龄</span>
			<div>
				<input v-model="form.nl" class="mui-btn " style="width: 100%;" />
			</div>
		</div>
		<div class="row-item">
			<span>是否建档立卡户</span>
			<select class="mui-btn " v-model="form.isJdlk">
				<option value=""> --是否建档立卡户--</option>
				<option value="是">是</option>
				<option value="否">否</option>
			</select>
		</div>
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
				form: {
					isStudent: '',
					isDgr: '',
					cun: '',
					zu: '',
					zzmm: '',
					xl: '',
					mz: '',
					xb: '',
					hyzk: '',
					nl: '',
					isJdlk: '',
				},
				options: {
					zus: [],
				},
			}
		},
		activated() {
			this.options = options
		},
		methods: {
			submit() {
				let param = {
					isStudent: this.form.isStudent,
					isDgr: this.form.isDgr,
					cun: this.form.cun,
					zu: this.form.zu,
					zzmm: this.form.zzmm,
					xl: this.form.xl,
					mz: this.form.mz,
					xb: this.form.xb,
					hyzk: this.form.hyzk,
					nl: this.form.nl,
					isJdlk: this.form.isJdlk,
				}
				localStorage.setItem('peopleQuery', JSON.stringify(param))
				this.$router.push('/people/list')
			},
			getZus(cun) {
				axios.get(UTIL.url(`getZu.do?Village=${cun}`)).then(response => {
					this.options.zus = response.data
				})
			},
		}
	}
</script>
<style scoped>
	. {
		height: 25px;
	}

	.row-item {
		padding: 10px 1.25rem
	}
</style>

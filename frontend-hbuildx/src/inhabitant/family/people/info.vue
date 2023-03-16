<template>
	<div class="option" style="margin-top: 6rem;">
		<ul class="mui-table-view">
			<p v-if="dataInfo!==null">
				<span>姓名：{{dataInfo.pName}}</span>
				<span style="color: red;" v-if="dataInfo.IsDie === '是'">(已故)</span>
				<span>身份证：{{dataInfo.ICard}}</span>
			</p>
			<router-link :to=`/people/info-base/${$route.params.id}` tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">基本信息</a>
				</li>
			</router-link>
			<router-link :to=`/people/info-affairs/${$route.params.id}` tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">民政信息</a>
				</li>
			</router-link>
			<router-link :to=`/people/info-edu/${$route.params.id}` tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">教育情况</a>
				</li>
			</router-link>
			<router-link :to=`/people/info-hospital/${$route.params.id}` tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">医疗健康</a>
				</li>
			</router-link>
			<router-link :to=`/people/info-work/${$route.params.id}` tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">就业情况</a>
				</li>
			</router-link>
			<router-link :to=`/people/visit/list/${$route.params.id}` tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">信访情况</a>
				</li>
			</router-link>
			<br>
		</ul>
	</div>
</template>
<script>
	module.exports = {
		data() {
			return {
				dataInfo: null,
			}
		},
		activated() {
			axios.get(UTIL.url(`/member/getMemberById.do?id=${this.$route.params.id}`)).then(({
				data
			}) => {
				this.dataInfo = data.content
				console.log('this.dataInfo-->people-->', this.dataInfo)
			})
		},
		methods: {}
	}
</script>
<style scoped>
	.option ul li {
		line-height: 3rem;
	}
</style>

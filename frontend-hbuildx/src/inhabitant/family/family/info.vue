<template>
	<div class="option" style="margin-top: -4rem;">
		<ul class="mui-table-'view">
			<router-link :to=`/family/module/info-base/${$route.params.id}`>
				<li class="mui-table-view-cell mui-media"
					style="background-color: #576B95;background-image: linear-gradient(45deg,#0062CC, #008888)">
					<a class="mui-navigate-right" style="margin-top: 2rem">
						<div class="mui-media-body" v-if="isLike">
							<span class="name" style="font-size: 1.5rem;color: #FFFFFF">
								<span>{{familyInfo.pName}}</span>
								<span style="font-size: 1rem;color:red" v-if="familyInfo.isDie ==='是'">(已故)</span>
								<span style="font-size: 1rem;color:#B6B6B6">
									<span>({{familyInfo.village}}-{{familyInfo.groups}})</span>
								</span>
							</span>
							<p style="line-height: 1.5rem;font-size: 1rem">
								<span class="" style="color: #B6B6B6">{{familyInfo.icard}}</span>
							</p>
						</div>
						<div class="mui-media-body" v-if="!isLike">
							<span class="name" style="font-size: 1.5rem;color: #FFFFFF">
								<span>{{userInfo.pName}}(成员)</span>
								<span style="font-size: 1rem;color:red" v-if="userInfo.isDie ==='是'">(已故)</span>
								<span style="font-size: 1rem;color:#B6B6B6">
									<span>({{userInfo.village}}-{{userInfo.groups}})</span>
								</span>
							</span>
							<p style="line-height: 1rem;font-size: 1rem">
								<span style="font-size: 1rem;color:#B6B6B6">{{userInfo.icard}}</span>
							</p>
							<div style="width: 100%;height: 16px;"></div>
							<span class="name" style="font-size: 1rem;color: #FFFFFF">
								<span>{{familyInfo.pName}}&nbsp;(户主)</span>
								<span style="color:red" v-if="familyInfo.isDie ==='是'">(已故)</span>
								<span style="font-size: 1rem;color:#B6B6B6">
									<span>({{familyInfo.village}}-{{familyInfo.groups}})</span>
								</span>
							</span>
							<p style="line-height: 1rem;font-size: 1rem">
								<span class="" style="color: #B6B6B6">{{familyInfo.icard}}</span>
							</p>
						</div>
					</a>
				</li>
			</router-link>
		</ul>
		<ul class="mui-table-view">
			<router-link :to=`/family/module/info-house/${$route.params.id}` tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">房屋信息</a>
				</li>
			</router-link>
			<router-link :to=`/family/module/info-card/${$route.params.id}` tag="span" v-if="false">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">证件/卡包</a>
				</li>
			</router-link>
			<router-link :to=`/family/module/info-people-list/${$route.params.id}` tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">家庭成员</a>
				</li>
			</router-link>
			<router-link :to=`/family/module/info-money/${$route.params.id}` tag="span"  v-if="false">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">收入信息</a>
				</li>
			</router-link>
			<router-link :to=`/family/module/info-policy/${$route.params.id}` tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">享受政策</a>
				</li>
			</router-link>
			<router-link :to=`/family/module/info-car/${$route.params.id}` tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">车辆信息</a>
				</li>
			</router-link>
			<router-link :to=`/family/module/info-black/${$route.params.id}` tag="span" v-if="false">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">黑名单</a>
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
				familyInfo: {pName:''},
				userInfo: {pName:''},
				isLike: false
			}
		},
		activated() {
			let id = this.$route.params.id
			this.userInfo = {}
			this.familyInfo = {}
			let userId = this.$route.query.userId
			let familyUrl = UTIL.url(`getPeoInfoById.do?id=${id}`)
			axios.get(familyUrl).then(response => {
				if (response.data.state === 1) {
					this.familyInfo = response.data.content
					console.info('this.familyInfo-->', this.familyInfo)
				}
			})
			let userUrl = UTIL.url(`/member/getById.do?id=${userId}`)
			axios.get(userUrl).then(response => {
				if (response.data.state === 1) {
					this.userInfo = response.data.content
					console.info('this.userInfo-->', this.userInfo)
				}
			})
			
			
		},
		mounted() {},
		methods: {
		}
	}
</script>
<style scoped>
	.option ul li {
		line-height: 3rem;
	}
</style>

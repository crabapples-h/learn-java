<template>
	<span>
		<ul class="mui-table-view">
			<router-link to="/family/index" tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">
						<span class="mui-icon mui-icon-contact" style="color: #0062CC;">&nbsp;&nbsp; </span>住户
					</a>
					<span class="mui-badge mui-badge-danger">{{dataInfo.family}}</span>
				</li>
			</router-link>
			<router-link to="/people/index" tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">
						<span class="mui-icon mui-icon-contact" style="color: #0062CC;">&nbsp;&nbsp; </span>居民</a>
					<!-- <span class="mui-badge mui-badge-danger">{{dataInfo.alivePeople}}</span> -->
				</li>
			</router-link>
			<router-link to="/affairs/index" tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">
						<span class="mui-icon mui-icon-star" style="color: #FFA500;">&nbsp;&nbsp;</span>民政</a>
				</li>
			</router-link>
			<router-link to="/hospital/index" tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right" href="enterpriseExamine.html">
						<span class="mui-icon mui-icon-plusempty" style="color: #EE0A24;">&nbsp;&nbsp;</span>医疗</a>
				</li>
			</router-link>
			<router-link to="/work/index" tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right" href="enterpriseExamine.html">
						<span class="mui-icon mui-icon-spinner-cycle mui-spin"
							style="color: #F56C6C;">&nbsp;&nbsp;</span>就业</a>
				</li>
			</router-link>
			<router-link to="/edu/index" tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right" href="enterpriseExamine.html">
						<span class="mui-icon mui-icon-compose" style="color: #896BDA;">&nbsp;&nbsp;</span>教育</a>
				</li>
			</router-link>
			<router-link to="/black/index" tag="span">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right" href="enterpriseExamine.html">
						<span class="mui-icon mui-icon-email" style="color: #008000;">&nbsp;&nbsp;</span>黑名单</a>
				</li>
			</router-link>
		</ul>
	</span>

</template>
<script>
	module.exports = {
		data() {
			return {
				dataInfo: {},
			};
		},
		activated() {},
		mounted() {
			// let user={"acc":"SMZzf","uid":2,"AName":"勺米镇政府","tel":"-/-","zu":"-/-","pwd":"SMZzf","cun":"-/-"}
			// let user={"acc":"SMZss8485","uid":49,"AName":"蒋明才","tel":"15186314999","zu":"梭沙组","pwd":"072116","cun":"梭沙村"}
			// localStorage.setItem('user',JSON.stringify(user))
			var name = JSON.parse(localStorage.getItem("user")).cun;
			this.getVillagerCount(name)
		},
		methods: {
			getVillagerCount(name) {
				if (!name.endsWith('村')) {
					name = ''
				}
				let url = UTIL.url(`statistics/getVillagerCount.do?village=${name}`)
				axios.get(url).then(response => {
					this.dataInfo = response.data
				})
			},
		}
	}
</script>
<style>
	.mui-table-view-cell {
		border-bottom: 1px solid #cbcbcb;
	}
</style>

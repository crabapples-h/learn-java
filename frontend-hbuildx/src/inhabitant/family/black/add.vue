<template>
	<div class="option" style="margin-top: 6rem;">
		<select v-model="reason" v-if="familyDataFull.blacklist==='否'">
			<option value="">--请选择加入黑名单原因--</option>
			<option value="未参加农合医疗保险">未参加农合医疗保险</option>
			<option value="滥办酒席">滥办酒席</option>
			<option value="不赡养老人">不赡养老人</option>
			<option value="违规建房">违规建房</option>
			<option value="不抚养未成年子女">不抚养未成年子女</option>
			<option value="环境卫生长期脏乱差">环境卫生长期脏乱差</option>
			<option value="不支持镇村工作">不支持镇村工作</option>
			<option value="长期非法越级上访">长期非法越级上访</option>
			<option value="其他">其他</option>
		</select>
		<span style="font-size: 1rem; color: #666666">添加说明</span>
		<textarea id="adminContent" v-model="remark" style="height: 10rem; width: 100%" @input="wordCount"></textarea>
		<p style="color: #CCCCCC; text-align:right"><span id="word-count">0</span>/200</p>
		<button id="sumbit" type="button" class="mui-btn mui-btn-primary" style="width: 100%; height: 3rem"
			@click="submit">提交</button>
	</div>
</template>
<script>
	module.exports = {
		data() {
			return {
				familyDataFull: JSON.parse(localStorage.getItem('familyData-full')),
				reason: '',
				remark: '',
			}
		},
		mounted() {},
		methods: {
			wordCount() {
				var content = document.getElementById('adminContent').value;
				document.getElementById('word-count').innerHTML = content.length;
				if (content.length > 200) {
					document.getElementById('word-count').style.color = 'darkred';
					document.getElementById('sumbit').disabled = 'disabled';
				} else {
					document.getElementById('sumbit').disabled = '';
					document.getElementById('word-count').style.color = '#CCCCCC';
				}
			},
			submit() {
				let data = {
					id: this.familyDataFull.id,
					bl: this.familyDataFull.blacklist === '否' ? '是' : '否',
					remark:  this.remark,
					reason: this.reason
				}
				console.log(data)
				const url1 = UTIL.url('changeBlackListStatus4App.do')
				axios.post(url1,data).then(response=>{
					if(response.data.state===1){
						alert('操作成功')
						this.$router.push(`/family/info/${this.familyDataFull.id}`)
					}
				})
			}
		}
	}
</script>
<style scoped>

</style>

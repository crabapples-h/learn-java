<template>
	<div class="parent-body">
		<!--实验室-->
		<el-row class="form-line">
			<el-col :span="5"><label>实验室</label></el-col>
			<el-col :span="8">
				<el-select v-model="form.roomsId" style="width:100%" size="small" @change="roomsChange">
					<el-option v-for="item in roomsList" :key="item.id" :label="item.name" :value="item.id" size="small">
					</el-option>
				</el-select>
			</el-col>
		</el-row>
		<!--实验设备-->
		<el-row class="form-line">
			<el-col :span="5"><label>实验设备</label></el-col>
			<el-col :span="8">
				<el-select v-model="form.machinesId" style="width:100%" size="small" @change="machinesChange">
					<el-option v-for="item in machinesList" :key="item.id" :label="item.name" :value="item.id" size="small">
					</el-option>
				</el-select>
			</el-col>
		</el-row>
		<!--运行时长-->
		<el-row class="form-line">
			<el-col :span="5"><label>运行时长(分钟)</label></el-col>
			<el-col :span="8">
				<el-input v-model="form.machines.time" clearable size="small" :disabled="true"></el-input>
			</el-col>
		</el-row>
		<!--开始时间-->
		<el-row class="form-line">
			<el-col :span="5"><label>开始时间</label></el-col>
			<el-col :span="8">
				<el-date-picker v-model="form.startTime" type="datetime" style="width: 100%" size="small"
				                placeholder="选择开始时间" @change="startTimeChange" :disabled="this.form.machinesId === ''">
				</el-date-picker>
			</el-col>
		</el-row>

		<!--结束时间-->
		<el-row class="form-line">
			<el-col :span="5"><label>结束时间</label></el-col>
			<el-col :span="8">
				<el-date-picker v-model="form.endTime" type="datetime" style="width: 100%" size="small"
				                placeholder="结束时间自动计算" :disabled="true">
				</el-date-picker>
			</el-col>
		</el-row>

		<el-row class="form-line">
			<el-col :span="2">
				<el-button type="primary" @click="saveForm" size="small">确认预约</el-button>
			</el-col>
		</el-row>
	</div>
</template>

<script>
    module.exports = {
        data() {
            return {
                roomsList: [],
                machinesList: [],
                form: {
                    id: '',
                    roomsId: '',
                    machinesId: '',
                    startTime: '',
                    endTime: '',
                    machines: {}
                },
            };
        },
        activated() {
            this.getRoomsList()
        },
        mounted() {
        },
        methods: {
            machinesChange(id) {
                this.getMachinesById(id)
                this.form.startTime = ''
                this.form.endTime = ''
            },
            roomsChange(id) {
                this.getRoomsById(id)
            },
            startTimeChange(time) {
                let startTime = new moment(time)
                let endTime = new moment(time).add('m', this.form.machines.time)
                this.form.endTime = endTime.format('YYYY-MM-DD HH:mm:ss')
                console.log(startTime.format('YYYY-MM-DD HH:mm:ss'))
                console.log(endTime.format('YYYY-MM-DD HH:mm:ss'))
            },
            saveForm() {
                const _this = this;
                console.log(_this.form)
                axios.post(`/api/saveLineUps`, _this.form).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error(result.message);
                        return
                    }
                    _this.$message.success(result.message);
                }).catch(function (error) {
                    window.location.reload();
                    console.log('请求出现错误:', error);
                });
            },
            getMachinesById(id) {
                const _this = this;
                axios.get(`/api/getMachinesById?id=${id}`).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return
                    }
                    _this.form.machines = result.data;
                }).catch(function (error) {
                    console.log('请求出现错误:', error);
                });
            },
            getRoomsById(id) {
                const _this = this;
                axios.get(`/api/getRoomsById?id=${id}`).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error(result.message);
                        return
                    }
                    this.machinesList = result.data.machines
                    // _this.form = result.data;
                    // _this.form.machines = result.data.machines.map(e => {
                    //     return e.id
                    // })
                }).catch(function (error) {
                    console.log('请求出现错误:', error);
                });
            },
            getRoomsList() {
                const _this = this;
                axios.get('/api/getRoomsList').then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return;
                    }
                    _this.roomsList = result.data;
                }).catch(function (error) {
                    console.log('请求出现错误:', error);
                });
            },
        }
    }
</script>

<style scoped>
	.form-line {
		margin-top: 16px;
	}
</style>

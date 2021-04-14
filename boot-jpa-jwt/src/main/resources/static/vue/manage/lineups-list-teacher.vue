<template>
	<el-row>
		<el-table :data="tableData" stripe style="width: 100%" :height="500">
			<el-table-column prop="createTime" label="预约时间"></el-table-column>
			<!--			<el-table-column prop="startTime" label="开始时间"></el-table-column>-->
			<!--			<el-table-column prop="endTime" label="结束时间"></el-table-column>-->
			<el-table-column prop="reallyStartTime" label="开始时间"></el-table-column>
			<el-table-column prop="reallyEndTime" label="结束时间"></el-table-column>
			<el-table-column label="实验图片">
				<template slot-scope="scope">
					<img style="width: 100%;" :src="scope.row.imgSrc" @click="showImg(scope)"/>
					<el-dialog :visible.sync="show.img" width="50%">
						<img :src="show.imgSrc" style="width: 100%;">
					</el-dialog>
				</template>
			</el-table-column>
			<el-table-column prop="user.name" label="预约人"></el-table-column>
			<el-table-column prop="status" label="状态">
				<template slot-scope="scope">
					<el-tag size="small" v-if="scope.row.status === 0">未开始</el-tag>
					<el-tag size="small" v-if="scope.row.status === 1" type="warning">使用中</el-tag>
					<el-tag size="small" v-if="scope.row.status === 2" type="success">已结束</el-tag>
					<el-tag size="small" v-if="scope.row.status === 3" type="info">已取消</el-tag>
				</template>
			</el-table-column>
			<el-table-column label="操作" width="200" v-if="false">
				<template slot-scope="scope">
					<el-button type="primary" @click="startUse(scope)" size="mini" v-if="scope.row.status === 0">开始</el-button>
					<el-button type="primary" @click="endUse(scope)" size="mini" v-if="scope.row.status === 1">结束</el-button>
					<el-button type="danger" @click="closeUse(scope)" size="mini" v-if="scope.row.status === 0">撤销</el-button>
				</template>
			</el-table-column>
		</el-table>
	</el-row>
</template>

<script>
    module.exports = {
        data() {
            return {
                tableData: [],
                imgSrc: '',
                show: {
                    dialog: false,
                    img: false,
                    imgSrc: '',
                },
            };
        },
        activated() {
            this.getTableDataList()
        },
        mounted() {
        },
        methods: {
            showImg(scope) {
                this.show.img = true
                this.show.imgSrc = scope.row.imgSrc;
            },
            getTableDataList() {
                const _this = this;
                axios.get('/api/getLineUpsList').then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return;
                    }
                    _this.tableData = result.data;
                }).catch(function (error) {
                    console.error('请求出现错误:', error);
                });
            },
        },

    }
</script>

<style scoped>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
</style>

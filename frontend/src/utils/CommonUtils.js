import confirm from 'ant-design-vue'
// import confirm from 'ant-design-vue/confirm'
import Vue from 'vue'

export default function confirm1() {
    console.log(12333333)
    this.$confirm({
        title: '确认操作?',
        cancelText: '取消',
        okText: '确定',
        onOk() {
            // _this.$http.post(`/api/user/lockUser/${e.id}`).then(result => {
            //     console.log('通过api获取到的数据:', result);
            //     if (result.status !== 200) {
            //         _this.$message.error(result.message);
            //         return
            //     }
            //     _this.$message.success(result.message)
            // }).catch(function (error) {
            //     console.log('请求出现错误:', error);
            // });
        },
    });
}
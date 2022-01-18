<template>
  <a-modal title="修改密码" :visible.sync="show.parent" width="25%" ok-text="确认" cancel-text="取消"
           @ok="submitUpdatePassword" @cancel="closeUpdatePassword">
    <a-form-model ref="ruleForm" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-model-item ref="password" label="原密码" prop="password">
        <a-input-password v-model="form.password"/>
      </a-form-model-item>
      <a-form-model-item ref="newPassword" label="新密码" prop="newPassword">
        <a-input-password v-model="form.newPassword"/>
      </a-form-model-item>
      <a-form-model-item ref="rePassword" label="重复密码" prop="againPassword">
        <a-input-password v-model="form.againPassword"/>
      </a-form-model-item>
    </a-form-model>
  </a-modal>
</template>

<script>
import {reactive} from "vue";

export default {
  setup() {
    const data = reactive({
      show: {parent: false},
      rules: {parent: false},
      labelCol: {parent: false},
      wrapperCol: {parent: false},
      form: {parent: false},
    })

    function showUpdatePassword() {
      this.show.updatePassword = true
    }

    showUpdatePassword:() => {

    }
  },
  data() {
    return {
      labelCol: {span: 5},
      wrapperCol: {span: 16},
      rules: {
        password: [
          {required: true, message: '请输入原密码', trigger: 'change'},
          {min: 8, max: 16, message: '长度为8-16位', trigger: 'change'},
        ],
        newPassword: [
          {required: true, message: '请输入新密码', trigger: 'change'},
          {min: 8, max: 16, message: '长度为8-16位', trigger: 'change'},
        ],
        againPassword: [
          {required: true, message: '请重复新密码', trigger: 'change'},
          {min: 8, max: 16, message: '长度为8-16位', trigger: 'change'},
        ],
      },
      form: {
        password: '',
        newPassword: '',
        againPassword: ''
      },
      show: {
        updatePassword: false
      },
      userInfo: {
        name: ''
      },
    };
  },
}
</script>

<style scoped lang="less">

.title {
  font-size: 20px;
  color: #fff;
  font-weight: 700;
}

.ant-layout-header {
  background: @primary-color;
  color: #fff;
  height: 7vh;
  line-height: 7vh;
}

.ant-layout-footer {
  background: @primary-color;
  color: #fff;
  height: 10vh;
  line-height: 7vh;
}

.ant-layout-sider {
  width: 100%;
  height: 83vh;
  background: #fff;
}

.ant-layout-content {
  box-shadow: inset 0 0 5px fade(@primary-color, 20%);
  padding: 12px;
  background: #fff;
  min-height: 120px;
  height: 83vh;
  overflow: auto;
}

/*滚动条整体样式*/
.ant-layout-content::-webkit-scrollbar {
  width: 10px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 1px;
  margin-right: 10px;
  opacity: 0.2;
}

/*滚动条里面小方块(滑块 )*/
.ant-layout-content::-webkit-scrollbar-thumb {
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 5px @primary-color;
  background: fade(@primary-color, 20%);
  opacity: 0.2;
}

/*滚动条里面轨道(背景)*/
.ant-layout-content::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 5px @primary-color;
  border-radius: 10px;
  background: fade(@primary-color, 20%);
  opacity: 0.2;
}
</style>

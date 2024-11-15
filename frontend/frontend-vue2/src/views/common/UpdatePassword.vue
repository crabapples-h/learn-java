<template>
  <a-layout-header>
    <a-row>
      <a-col :span="4">
        <span class="title">{{ title }}</span>
      </a-col>
      <a-col :span="3" :offset="17">
        <a-dropdown placement="bottomCenter">
            <span>
              <span>{{ userInfo.name }}&nbsp;</span>
              <a-icon type="setting"/>
            </span>
          <a-menu slot="overlay">
            <a-menu-item key="1" @click="showUpdatePassword">
              <a-icon type="user"/>
              修改密码
            </a-menu-item>
            <a-menu-item key="2" @click="logout">
              <a-icon type="close"/>
              退出登录
            </a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px"> Button
            <a-icon type="down"/>
          </a-button>
        </a-dropdown>
      </a-col>
    </a-row>
    <a-modal title="修改密码" :visible.sync="show.updatePassword" width="25%" ok-text="确认" cancel-text="取消"
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
  </a-layout-header>
</template>

<script>
import CommonApi from '@/api/CommonApi'
import { SysApis } from '@/api/Apis'

export default {
  name: "C-PageHeader",
  props: {
    title: {
      type: String,
      default: '管理系统'
    },
    userInfo: {
      type: Object,
      default: {}
    },
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
    };
  },
  activated() {
  },
  mounted() {
    // this.$router.push({name: 'welcome'})
  },
  methods: {
    showUpdatePassword() {
      this.show.updatePassword = true
    },
    closeUpdatePassword() {
      this.show.updatePassword = false
      this.$refs.ruleForm.resetFields();
    },
    submitUpdatePassword() {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          console.info('success', this.form);
          if (this.form.newPassword !== this.form.againPassword) {
            this.$message.error('两次密码不一致');
            return
          }
          this.$http.post(SysApis.updatePassword, this.form).then(result => {
            if (result.status !== 200) {
              this.$message.error(result.message);
              return;
            }
            this.$message.success(result.message);
            this.closeUpdatePassword()
          }).catch(function (error) {
            console.error('出现错误:', error);
          });
        } else {
          console.log('验证失败');
          return false;
        }
      })
    },
    logout() {
      CommonApi.logout().then(res => {
        if (res.status === 200) {
          window.location.reload()
        }
      })
    },
  }
}
</script>

<style scoped lang="less">
@import "~@public/color.less";

.title {
  font-size: 20px;
  color: #fff;
  font-weight: 700;
}

.ant-layout-header {
  background: @primary-color;
  color: #fff;
}
</style>

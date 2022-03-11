<template>
  <div>
    <a-layout-header>
      <a-row>
        <a-col :span="4">
          <span class="title">{{ title }}</span>
        </a-col>
        <a-col :span="3" :offset="17">
          <a-dropdown placement="topCenter">
            <a-icon type="setting"/>
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
          <span>{{ userInfo.name }}</span>
        </a-col>
      </a-row>
    </a-layout-header>

  </div>
</template>

<script>
import CommonApi from "@/api/CommonApi";
import {SysApis} from "@/api/Apis";
import {reactive} from "vue";

export default {
  props: {
    title: {
      type: String,
      default: '管理系统'
    }
  },
  setup() {
    const data = reactive({
      title: ''
    })
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
      CommonApi.logout()
    },
  }
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

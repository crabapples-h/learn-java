<template>
  <a-layout>
    <a-layout-header>
      <a-row>
        <a-col :span="4">
          <span class="title">管理系统</span>
        </a-col>
        <a-col :span="3" :offset="17">
          <a-dropdown>
            <a-icon type="setting"/>
            <a-menu slot="overlay">
              <a-menu-item key="1" @click="changePassword">
                <a-icon type="user"/>
                修改密码
              </a-menu-item>
              <a-menu-item key="2" @click="logout">
                <a-icon type="user"/>
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
    <a-layout>
      <a-layout-sider>
        <a-menu style="width: 200px;height: 100%" mode="inline">
          <a-sub-menu :key="menu.key" @titleClick="titleClick" v-for="menu in menus"
                      v-if="menu.children && menu.children.length">
            <span slot="title"><a-icon :type="menu.icon"/><span>{{ menu.name }}</span></span>
            <a-sub-menu :key="child.key" v-if="child.children && child.children.length" v-for="child in menu.children">
              <span slot="title"><a-icon :type="child.icon"/><span>{{ child.name }}</span></span>
              <a-menu-item :key="item.key" v-for="item in child.children" @click="clickMenu(item.url)">
                <a-icon :type="item.icon"/>
                <span>{{ item.name }}</span>
              </a-menu-item>
            </a-sub-menu>
            <a-menu-item :key="child.key" v-else @click="clickMenu(child.url)">
              <a-icon :type="child.icon"/>
              <span>{{ child.name }}</span>
            </a-menu-item>
          </a-sub-menu>
          <a-menu-item :key="menu.key" v-else @click="clickMenu(menu.url)">
            <a-icon :type="menu.icon"/>
            <span>{{ menu.name }}</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout-content class="content">
        <a-modal title="修改密码" :visible.sync="show.changePassword" width="25%" ok-text="确认" cancel-text="取消"
                 @ok="submitForm" @cancel="closeModal">
          <a-form-model ref="ruleForm" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
            <a-form-model-item ref="password" label="原密码" prop="password">
              <a-input-password v-model="form.password"/>
            </a-form-model-item>
            <a-form-model-item ref="newPassword" label="新密码" prop="newPassword">
              <a-input-password v-model="form.newPassword"/>
            </a-form-model-item>
            <a-form-model-item ref="rePassword" label="重复密码" prop="rePassword">
              <a-input-password v-model="form.rePassword"/>
            </a-form-model-item>
          </a-form-model>
        </a-modal>
        <keep-alive>
          <router-view name="innerView"></router-view>
        </keep-alive>
      </a-layout-content>
    </a-layout>
    <a-layout-footer></a-layout-footer>
  </a-layout>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      labelCol: {span: 5},
      wrapperCol: {span: 16},
      openKeys: [],
      rules: {
        password: [
          {required: true, message: '请输入原密码', trigger: 'change'},
          {min: 8, max: 16, message: '长度为8-16位', trigger: 'change'},
        ],
        newPassword: [
          {required: true, message: '请输入新密码', trigger: 'change'},
          {min: 8, max: 16, message: '长度为8-16位', trigger: 'change'},
        ],
        rePassword: [
          {required: true, message: '请重复新密码', trigger: 'change'},
          {min: 8, max: 16, message: '长度为8-16位', trigger: 'change'},
        ],
      },
      form: {
        password: '',
        newPassword: '',
        rePassword: ''
      },
      show: {
        changePassword: false
      },
      userInfo: {
        name: ''
      },
      menus: [
        {
          key: '1',
          name: '科研管理',
          icon: 'appstore',
          url: '',
          children: [
            {
              key: '11',
              name: '课题申报管理',
              icon: 'appstore',
              url: '/manage-index/sub-apply',
            },
          ]
        },
        {
          key: '2',
          name: '数据共享',
          icon: 'appstore',
          url: '/',
          children: [
            {
              key: '21',
              name: '锦途工程介绍',
              icon: 'appstore',
              url: '/jingtu-projects/info',
            },
            {
              key: '22',
              name: '国际本科4+0模式介绍',
              icon: 'appstore',
              url: '/jingtu-projects/4and0',
            },
            {
              key: '23',
              name: '全日制本科4+1模式介绍',
              icon: 'appstore',
              url: '/jingtu-projects/4and1',
            },
            {
              key: '24 ',
              name: '重点国防生定向培养',
              icon: 'appstore',
              url: '/jingtu-projects/countries',
            },
          ]
        },
        {
          key: '3',
          name: '科研分析',
          icon: 'appstore',
          url: '/really-projects/list',
        },
        {
          key: '4',
          name: '数据对接',
          icon: 'appstore',
          url: '/school-org/list',
          children: []
        },
        // {
        //   key: '99',
        //   name: '系统管理',
        //   icon: 'appstore',
        //   url: '/',
        //   children: [
        //     {
        //       key: '12331',
        //       name: '菜单管理',
        //       icon: 'appstore',
        //       url: '/settings-menu',
        //     }
        //   ]
        // },
      ],
    };
  },
  mounted() {
    let token = sessionStorage.getItem('crabapples-token')
    // this.getUserInfo()
    // this.$router.push({name: 'welcome'})
  },
  methods: {
    titleClick(e) {
      console.log('titleClick', e);
    },
    changePassword() {
      this.show.changePassword = true
    },
    closeModal() {
      this.show.changePassword = false
      this.$refs.ruleForm.resetFields();
    },
    logout() {
      this.$http.logout()
    },

    clickMenu(url) {
      this.$router.push({path: url})
      console.log(url)
    },
    getUserInfo() {
      this.$http.get('/api/getUserInfo').then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        if (result.data !== null) {
          this.userInfo = result.data;
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
    },
    submitForm() {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          console.info('success', this.form);
          if (this.form.newPassword !== this.form.rePassword) {
            this.$message.error('两次密码不一致');
            return
          }
          this.$http.post('/api/updatePassword', this.form).then(result => {
            if (result.status !== 200) {
              this.$message.error(result.message);
              return;
            }
            this.$message.success(result.message);
            this.closeModal()
          }).catch(function (error) {
            console.error('出现错误:', error);
          });
        } else {
          console.log('验证失败');
          return false;
        }
      })
    },
  }
}
</script>

<style scoped>
.ant-layout-header, .ant-layout-footer {
  background: #7dbcea;
  color: #fff;
  height: 5vh;
  line-height: 5vh;
}

.title {
  font-size: 20px
}

.ant-layout-footer {
  line-height: 1.5;
}

.ant-layout-sider {
  width: 100%;
  height: 90vh;
  background: #fff;
  /*color: #fff;*/
  /*line-height: 120px;*/
}

.ant-layout-content {
  padding: 16px;
  background: #fff;
  min-height: 120px;
  height: 90vh;
}

.container-parent {
  padding: 16px;
}
</style>
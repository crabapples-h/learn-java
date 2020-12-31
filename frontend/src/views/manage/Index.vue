<template>
  <a-layout>
    <a-layout-header style="color: #fff">
      <a-row>
        <a-col :span="4">
          <span style="font-size: 20px">管理系统</span>
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
          name: '首页管理',
          icon: 'appstore',
          url: '',
          children: [
            {
              key: '11',
              name: '第一版管理',
              icon: 'appstore',
              url: '',
              children: [
                {
                  key: '111',
                  name: '第一页管理',
                  icon: 'appstore',
                  url: '/banners/banners-no1',
                  children: []
                },
                {
                  key: '112',
                  name: '第二页管理',
                  icon: 'appstore',
                  url: '/banners/banners-no2',
                  children: []
                },
                {
                  key: '113',
                  name: '第三页管理',
                  icon: 'appstore',
                  url: '/banners/banners-no3',
                  children: []
                }
              ]
            },
            {
              key: '12',
              name: '第二版管理',
              icon: 'appstore',
              url: '/',
              children: [
                {
                  key: '121',
                  name: '技能培训',
                  icon: 'appstore',
                  url: '/banners/page-no2/list01',
                },
                {
                  key: '122',
                  name: '企业合作',
                  icon: 'appstore',
                  url: '/banners/page-no2/list02',
                },
                {
                  key: '123',
                  name: '重点国防定向培养',
                  icon: 'appstore',
                  url: '/banners/page-no2/list03',
                },
                {
                  key: '124',
                  name: '热点大学简介',
                  icon: 'appstore',
                  url: '/banners/page-no2/list04',
                }
              ]
            },
            {
              key: '13',
              name: '第三版管理',
              icon: 'appstore',
              url: '/banners/page-no3',
              children: [
                {
                  key: '131',
                  name: '国际本科4+0模式',
                  icon: 'appstore',
                  url: '/banners/page-no3/list01',
                },
                {
                  key: '132',
                  name: '全日制本科4+1模式',
                  icon: 'appstore',
                  url: '/banners/page-no3/list02',
                },
                {
                  key: '133',
                  name: '热门技能培训学校',
                  icon: 'appstore',
                  url: '/banners/page-no3/list03',
                },
                {
                  key: '134',
                  name: '国防生定向培养',
                  icon: 'appstore',
                  url: '/banners/page-no3/list04',
                },
                {
                  key: '135',
                  name: '研学旅行',
                  icon: 'appstore',
                  url: '/banners/page-no3/list05',
                },
                {
                  key: '136',
                  name: '热门高薪专业',
                  icon: 'appstore',
                  url: '/banners/page-no3/list06',
                },
                {
                  key: '137',
                  name: '医学护理专业',
                  icon: 'appstore',
                  url: '/banners/page-no3/list07',
                },
                {
                  key: '138',
                  name: '职业规划辅导',
                  icon: 'appstore',
                  url: '/banners/page-no3/list08',
                }
              ]
            },
            // {
            //   key: '14',
            //   name: '第四版管理',
            //   icon: 'appstore',
            //   url: '/',
            // },
            {
              key: '15',
              name: '第五版管理',
              icon: 'appstore',
              url: '/banners/page-no5',
            },
            // {
            //   key: '100',
            //   name: '测试',
            //   icon: 'appstore',
            //   url: '/',
            // }
          ]
        },
        {
          key: '2',
          name: '锦途工程',
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
          name: '实训项目',
          icon: 'appstore',
          url: '/really-projects/list',
        },
        {
          key: '4',
          name: '校企合作',
          icon: 'appstore',
          url: '/school-org/list',
          children: []
        },
        {
          key: '5',
          name: '个人简历',
          icon: 'appstore',
          url: '/resume/list-personal',
          children: []
        },
        {
          key: '6',
          name: '志愿简历',
          icon: 'appstore',
          url: '/resume/list-volunteer',
          children: []
        },
        {
          key: '7',
          name: '手机版',
          icon: 'appstore',
          url: '',
          children: [
            {
              key: '71',
              name: '首页轮播图',
              icon: 'appstore',
              url: '/wap/banner/list',
            },
            {
              key: '72',
              name: '学校简介',
              icon: 'appstore',
              url: '/wap/data-info/info',
              children: []
            },
            {
              key: '73',
              name: '交流合作',
              icon: 'appstore',
              url: '/wap/data-list/jiaoliuhezhuo',
              children: []
            },
            {
              key: '74',
              name: '专业设置',
              icon: 'appstore',
              url: '/wap/data-list/zhuanyeshezhi',
              children: []
            },
            {
              key: '75',
              name: '安置就业',
              icon: 'appstore',
              url: '/wap/data-list/anzhijiuye',
              children: []
            },
            {
              key: '76',
              name: '学校环境',
              icon: 'appstore',
              url: '/wap/data-list/xuexiaohuanjing',
              children: []
            },
            {
              key: '77',
              name: '校园生活',
              icon: 'appstore',
              url: '/wap/data-list/xiaoyuanshenghuo',
              children: []
            },
            {
              key: '78',
              name: '报名信息',
              icon: 'appstore',
              url: '/wap/data-list/baoming',
              children: []
            },
          ]
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
}

.ant-layout-footer {
  line-height: 1.5;
}

.ant-layout-sider {
  width: 100%;
  height: 823px;
  /*background: #ffffff;*/
  /*color: #fff;*/
  /*line-height: 120px;*/
}

.ant-layout-content {
  /*padding: 16px;*/
  background: #fff;
  color: #fff;
  min-height: 120px;
  line-height: 120px;
  height: 823px;
}

.container-parent {
  padding: 16px;
}
</style>
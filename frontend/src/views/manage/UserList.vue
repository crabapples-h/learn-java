<template>
  <div>
    <a-button @click="addUser">添加用户</a-button>
    <a-divider/>
    <a-drawer width="30%" :visible="show.userInfo" @close="closeForm">
      <a-form-model :model="form.userInfo" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-model-item label="id" prop="id" style="display: none">
          <a-input v-model="form.userInfo.id" disabled placeholder="新建时自动生成"/>
        </a-form-model-item>
        <a-form-model-item label="用户名" prop="username">
          <a-input v-model="form.userInfo.username" :disabled="form.type===1" placeholder="请输入用户名"/>
        </a-form-model-item>
        <a-form-model-item label="姓名" prop="name">
          <a-input v-model="form.userInfo.name" placeholder="请输入姓名"/>
        </a-form-model-item>
        <a-form-model-item label="年龄" prop="age">
          <a-input-number v-model="form.userInfo.age" placeholder="请输入年龄"/>
        </a-form-model-item>
        <a-form-model-item label="邮箱" prop="mail">
          <a-input v-model="form.userInfo.mail" placeholder="请输入邮箱"/>
        </a-form-model-item>
        <a-form-model-item label="电话" prop="phone">
          <a-input v-model="form.userInfo.phone" placeholder="请输入电话号码"/>
        </a-form-model-item>
        <span v-if="form.userInfo.role === 0">
          <a-form-model-item label="权限" prop="role">
            <a-radio-group v-model="form.userInfo.role" default-value="2">
              <a-radio value="1">科研管理员</a-radio>
              <a-radio value="2">科研人员</a-radio>
            </a-radio-group>
          </a-form-model-item>
        </span>
        <a-form-model-item label="角色" prop="tags">
          <a-select mode="multiple" v-model="form.userInfo.rolesList" placeholder="请选择角色">
            <a-select-option v-for="item in rolesOptions" :key="item.id">
              {{ item.name }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
      </a-form-model>
      <div class="drawer-bottom-button">
        <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
        <a-button type="primary" @click="submitForm">保存</a-button>
      </div>
    </a-drawer>
    <a-modal title="重置密码" :visible.sync="show.resetPassword" width="30%" ok-text="确认" cancel-text="取消"
             @ok="submitResetPassword" @cancel="closeResetPassword">
      <a-form-model :model="form.resetPassword" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-model-item label="新密码" prop="newPassword">
          <a-input-password v-model="form.resetPassword.newPassword"/>
        </a-form-model-item>
        <a-form-model-item label="重复密码" prop="againPassword">
          <a-input-password v-model="form.resetPassword.againPassword"/>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
    <a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="pagination">
      <span slot="tags" slot-scope="tags">
        <a-tag v-for="tag in tags" :rowKey="tag.id" :color="tag.color">{{ tag.name }}</a-tag>
      </span>
      <span slot="role" slot-scope="role">
          <a-tag color="green" v-if="role === 0">超级管理员</a-tag>
          <a-tag color="orange" v-if="role === 1">科研管理员</a-tag>
          <a-tag color="blue" v-if="role === 2">科研人员</a-tag>
      </span>
      <span slot="status" slot-scope="status">
        <a-tag color="green" v-if="status === 0">正常</a-tag>
        <a-tag color="red" v-else>锁定</a-tag>
      </span>
      <span slot="action" slot-scope="text, record">
        <span v-if="record.role !== 0">
          <c-pop-button title="确认要锁定吗" text="锁定" @click="lockUser(record)" type="primary" v-if="record.status === 0"/>
          <c-pop-button title="确认要解锁吗" text="解锁" @click="unlockUser(record)" v-if="record.status === 1"/>
          <a-divider type="vertical"/>
        </span>
        <span v-if="record.role !== 0">
          <c-pop-button title="确认要删除吗" text="删除" @click="removeUser(record)" type="danger"/>
          <a-divider type="vertical"/>
        </span>
        <a-button type="primary" size="small" @click="editUser(record)">编辑</a-button>
        <a-divider type="vertical"/>
        <a-button type="primary" size="small" @click="showResetPassword(record)">重置密码</a-button>
        <a-divider type="vertical"/>
      </span>
    </a-table>
  </div>
</template>

<script>

import CPopButton from "@/components/c-pop-button";
import CButton from "@/components/c-button";

export default {
  name: "user-list",
  components: {CButton, CPopButton},
  data() {
    return {
      pagination: {
        pageSize: 4,
        pageSizeOptions: ['10', '20', '30', '40'],
        total: 50,
        size: 'middle',
        showSizeChanger: true,
        onChange: ((page, pageSize) => {
          console.log(page, pageSize)
        }),
        // showSizeChange: (current, size) => {
        //   console.log(current, size)

        // },
        showSizeChange: ((current, size) => {
          console.log(current, size)
        })
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'change'},
          {min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change'},
          {whitespace: true, message: '请输入用户名', trigger: 'change'}
        ],
        name: [
          {required: true, message: '请输入名称', trigger: 'change'},
          {min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change'},
          {whitespace: true, message: '请输入名称', trigger: 'change'}
        ],
        age: [
          {required: true, message: '请输入年龄', trigger: 'change'},
        ],
        mail: [
          {required: true, message: '请输入邮箱', trigger: 'change'},
          {whitespace: true, message: '请输入邮箱', trigger: 'change'}
        ],
        phone: [
          {required: true, message: '请输入电话', trigger: 'change'},
          {whitespace: true, message: '请输入电话', trigger: 'change'},
          {min: 8, max: 16, message: '长度为8-16个字符', trigger: 'change'},
        ],
        role: [
          {required: true, message: '请选择角色', trigger: 'change'},
        ],
        tags: [],
        newPassword: [
          {required: true, message: '请输入密码', trigger: 'change'},
          {min: 8, max: 16, message: '长度为8-16个字符', trigger: 'change'},
          {whitespace: true, message: '请输入密码', trigger: 'change'}
        ],
        againPassword: [
          {required: true, message: '请重复输入密码', trigger: 'change'},
          {min: 8, max: 16, message: '长度为8-16个字符', trigger: 'change'},
          {whitespace: true, message: '请重复输入密码', trigger: 'change'}
        ],
      },
      columns: [
        {
          dataIndex: 'username',
          key: 'username',
          title: '用户名',
        },
        {
          dataIndex: 'name',
          title: '姓名',
          key: 'name',
        },
        {
          dataIndex: 'age',
          title: '年龄',
          key: 'age',
        },
        {
          dataIndex: 'mail',
          title: '邮箱',
          key: 'mail',
        },
        {
          dataIndex: 'phone',
          title: '电话',
          key: 'phone',
        },
        {
          dataIndex: 'role',
          title: '角色',
          key: 'role',
          scopedSlots: {customRender: 'role'}
        },
        {
          dataIndex: 'status',
          title: '状态',
          key: 'status',
          scopedSlots: {customRender: 'status'}
        },
        {
          title: '操作',
          key: 'action',
          scopedSlots: {customRender: 'action'},
        },
      ],
      dataSource: [],
      labelCol: {span: 5},
      wrapperCol: {span: 16},
      rolesOptions: [],
      form: {
        type: 0,
        userInfo: {
          id: '',
          name: '',
          age: '',
          mail: '',
          phone: '',
          role: 1,
          status: null,
          rolesList: [],
        },
        resetPassword: {
          id: '',
          newPassword: '',
          againPassword: '',
        },
      },
      show: {
        tags: false,
        userInfo: false,
        resetPassword: false,
      },
    };
  },
  activated() {
  },
  mounted() {
    console.log(122223)
    // this.getList()
  },
  methods: {
    getRolesList() {
      this.$http.get('/api/sys/roles/list').then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        if (result.data !== null) {
          this.rolesOptions = result.data;
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
    },
    refreshData() {
      this.getList()
    },
    getList() {
      console.log(123)
      this.$http.get('/api/user/list').then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        if (result.data !== null) {
          this.dataSource = result.data;
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
    },
    lockUser(e) {
      this.$http.post(`/api/user/lock/${e.id}`).then(result => {
        console.log('通过api获取到的数据:', result);
        if (result.status !== 200) {
          this.$message.error(result.message);
          return
        }
        this.refreshData()
        this.$message.success(result.message)
      }).catch(function (error) {
        console.log('请求出现错误:', error);
      });
    },
    unlockUser(e) {
      this.$http.post(`/api/user/unlock/${e.id}`).then(result => {
        console.log('通过api获取到的数据:', result);
        if (result.status !== 200) {
          this.$message.error(result.message);
          return
        }
        this.refreshData()
        this.$message.success(result.message)
      }).catch(function (error) {
        console.log('请求出现错误:', error);
      });
    },
    removeUser(e) {
      const _this = this;
      this.$confirm({
        title: '确认操作?',
        cancelText: '取消',
        okText: '确定',
        onOk() {
          console.log(e)
          _this.$http.post(`/api/user/del/${e.id}`).then(result => {
            console.log('通过api获取到的数据:', result);
            if (result.status !== 200) {
              _this.$message.error(result.message);
              return
            }
            _this.$message.success(result.message)
            _this.this.refreshData()
          }).catch(function (error) {
            console.log('请求出现错误:', error);
          });
        },
      });
    },
    addUser() {
      this.form.type = 0
      this.getRolesList()
      this.show.userInfo = true
    },
    editUser(e) {
      this.form.type = 1
      this.form.userInfo = e
      this.form.userInfo.rolesList = e.rolesList.map(r => {
        return r.id
      })
      console.log(this.form.userInfo.rolesList)
      this.getRolesList()
      this.show.userInfo = true
    },
    closeForm() {
      this.form.userInfo = {
        id: '',
        name: '',
        age: '',
        mail: '',
        role: null,
        status: null,
        tags: [],
      }
      this.show.userInfo = false
      this.refreshData()
    },
    submitForm() {
      console.log(this.form.userInfo)
      let url = this.form.type === 0 ? '/api/user/add' : '/api/user/edit'
      this.$http.post(url, this.form.userInfo).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        this.closeForm()
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
    },
    closeResetPassword() {
      this.show.resetPassword = false
      this.refreshData()
    },
    showResetPassword(e) {
      this.form.resetPassword.id = e.id
      this.show.resetPassword = true
    },
    submitResetPassword() {
      this.$http.post('/api/user/password/reset', this.form.resetPassword).then(result => {
        console.log('通过api获取到的数据:', result);
        if (result.status !== 200) {
          this.$message.error(result.message);
          return
        }
        this.$message.success(result.message)
        this.closeResetPassword()
      }).catch(function (error) {
        console.log('请求出现错误:', error);
      });
    },
  }
}
</script>

<style scoped>
.drawer-bottom-button {
  position: absolute;
  right: 0;
  bottom: 0;
  width: 100%;
  border-top: 1px solid #e9e9e9;
  padding: 10px 16px;
  background: #fff;
  text-align: right;
  z-index: 1;
}
</style>
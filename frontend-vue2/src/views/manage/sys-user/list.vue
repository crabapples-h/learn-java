<template>
  <div>
    <a-button @click="addUser" v-auth:sys:user:add>添加用户</a-button>
    <a-divider/>
    <a-drawer width="30%" :visible="show.userInfo" @close="closeForm">
      <a-form-model :model="form" :rules="formRules" :label-col="labelCol" :wrapper-col="wrapperCol"
                    ref="userForm">
        <a-form-model-item label="id" prop="id" style="display: none">
          <a-input v-model="form.id" disabled placeholder="新建时自动生成"/>
        </a-form-model-item>
        <a-form-model-item label="用户名" prop="username">
          <a-input v-model="form.username" :disabled="show.isEdit" placeholder="请输入用户名"
                   @blur="checkUsername"/>
        </a-form-model-item>
        <a-form-model-item label="姓名" prop="requireInput">
          <a-input v-model="form.name" placeholder="请输入姓名"/>
        </a-form-model-item>
        <a-form-model-item label="年龄" prop="age">
          <a-input-number v-model="form.age" placeholder="请输入年龄"/>
        </a-form-model-item>
        <a-form-model-item label="邮箱" prop="mail">
          <a-input v-model="form.mail" placeholder="请输入邮箱"/>
        </a-form-model-item>
        <a-form-model-item label="电话" prop="phone">
          <a-input v-model="form.phone" placeholder="请输入电话号码"/>
        </a-form-model-item>
        <a-form-model-item label="新密码">
          <a-input v-model="form.newPassword" placeholder="请输入新密码"/>
        </a-form-model-item>
        <a-form-model-item label="重复密码">
          <a-input v-model="form.againPassword" placeholder="请输入重复密码"/>
        </a-form-model-item>
        <a-form-model-item label="角色">
          <a-select mode="multiple"
                    v-model="form.roleList"
                    placeholder="请选择角色"
                    :options="roleOptions">
          </a-select>
        </a-form-model-item>
      </a-form-model>
      <div class="drawer-bottom-button">
        <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
        <a-button type="primary" @click="submitForm">保存</a-button>
      </div>
    </a-drawer>
    <a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="pagination">
      <span slot="status" slot-scope="status">
        <a-tag color="green" v-if="status === 0">正常</a-tag>
        <a-tag color="red" v-else>锁定</a-tag>
      </span>
      <span slot="action" slot-scope="text, record">
        <span v-if="record.role !== 0">
          <c-pop-button title="确认要锁定吗" text="锁定" @click="lockUser(record)" type="primary"
                        v-if="record.status === 0"
                        v-auth:sys:user:lock/>
          <c-pop-button title="确认要解锁吗" text="解锁" @click="unlockUser(record)" v-if="record.status === 1"
                        v-auth:sys:user:unlock/>
          <a-divider type="vertical"/>
        </span>
        <span v-if="record.role !== 0">
          <c-pop-button title="确认要删除吗" text="删除" @click="removeUser(record)" type="danger" v-auth:sys:user:del/>
          <a-divider type="vertical"/>
        </span>
        <a-button type="primary" size="small" @click="editUser(record)" v-auth:sys:user:edit>编辑</a-button>
      </span>
    </a-table>
  </div>
</template>

<script>

import { SysApis } from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'

export default {
  name: 'user-list',
  mixins: [SystemMinix],
  data() {
    return {
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
          dataIndex: 'gender_dictValue',
          title: '性别',
          key: 'gender',
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
          dataIndex: 'status',
          title: '状态',
          key: 'status',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '操作',
          key: 'action',
          scopedSlots: { customRender: 'action' },
        },
      ],
      roleOptions: [],
      show: {
        userInfo: false,
        isEdit: false,
      },
      url: {
        list: SysApis.userPage,
        save: SysApis.saveUser,
        lock: SysApis.lockUser,
        unlock: SysApis.unlockUser,
        delete: SysApis.delUser,
        userRoles: SysApis.userRoles,
        roleList: SysApis.roleList,
      },
    }
  },
  activated() {
  },
  mounted() {
    this.getRoleList()
  },
  methods: {
    getRoleList() {
      this.$http.get(this.url.roleList).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        this.roleOptions = result.data.map(e => {
          return {
            label: e.name,
            value: e.id,
          }
        })
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
    },
    lockUser(e) {
      this.$http.post(`${this.url.lock}/${e.id}`).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        this.refreshData()
        this.$message.success(result.message)
      }).catch(function (error) {
        console.log('请求出现错误:', error)
      })
    },
    unlockUser(e) {
      this.$http.post(`${this.url.unlock}/${e.id}`).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        this.refreshData()
        this.$message.success(result.message)
      }).catch(function (error) {
        console.log('请求出现错误:', error)
      })
    },
    removeUser(e) {
      const _this = this
      this.$confirm({
        title: '确认操作?',
        cancelText: '取消',
        okText: '确定',
        onOk() {
          _this.$http.post(`${_this.url.delete}/${e.id}`).then(result => {
            if (result.status !== 200) {
              _this.$message.error(result.message)
              return
            }
            _this.$message.success(result.message)
            _this.refreshData()
          }).catch(function (error) {
            console.log('请求出现错误:', error)
          })
        },
      })
    },
    addUser() {
      this.show.isEdit = false
      this.show.userInfo = true
    },
    editUser(e) {
      this.show.isEdit = true
      this.form = e
      this.$http.get(`${this.url.userRoles}/${e.id}`).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        this.$set(this.form, 'roleList', result.data.map(r => {
          return r.id
        }))
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
      this.show.userInfo = true

    },
    closeForm() {
      this.form = {}
      this.show.userInfo = false
      this.refreshData()
    },
    submitForm() {
      this.$refs.userForm.validate(valid => {
        if (valid) {
          this.$http.post(this.url.save, this.form).then(result => {
            if (result.status !== 200) {
              this.$message.error(result.message)
              return
            }
            this.closeForm()
          }).catch(function (error) {
            console.error('出现错误:', error)
          })
        }
      })
    },
    checkUsername() {
      let username = this.form.username
      this.$http.get(`${SysApis.checkUsername}/${username}`).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        this.$message.success(result.message)
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
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

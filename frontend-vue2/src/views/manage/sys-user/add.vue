<template>
  <a-drawer title="添加用户" width="30%" :visible="visible" @close="closeForm">
    <a-form-model :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" ref="userForm">
      <a-form-model-item label="id" prop="id" style="display: none">
        <a-input v-model="form.id" disabled placeholder="新建时自动生成"/>
      </a-form-model-item>
      <a-form-model-item label="用户名" prop="username">
        <a-input v-model="form.username" :disabled="isEdit" placeholder="请输入用户名" />
      </a-form-model-item>
      <a-form-model-item label="姓名" prop="name">
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
      <a-form-model-item label="角色">
        <a-select mode="multiple" v-model="form.roleList" placeholder="请选择角色" :options="roleOptions"/>
      </a-form-model-item>
    </a-form-model>
    <div class="drawer-bottom-button">
      <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
      <a-button type="primary" @click="submit">保存</a-button>
    </div>
  </a-drawer>
</template>

<script>

import { SysApis } from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'
import { checkValidate } from 'ant-design-vue/lib/_util/moment-util'

export default {
  name: 'user-add',
  mixins: [SystemMinix],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    cancel: {
      type: Function,
    },
    isEdit: {
      type: Boolean,
      default: false
    },
  },
  watch: {
    isEdit(nowValue, oldValue) {
      if (nowValue) {
        this.loadUserRoles()
      }
    }
  },
  data() {
    return {
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'change' },
          { min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change' },
          { whitespace: true, message: '请输入用户名', trigger: 'change' },
          { validator: this.checkUsername, message: '用户名已经存在', trigger: 'change' }
        ],
        name: [
          { required: true, message: '请输入名称', trigger: 'change' },
          { min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change' },
          { whitespace: true, message: '请输入名称', trigger: 'change' }
        ],
        age: [
          { required: true, message: '请输入年龄', trigger: 'change' },
        ],
        mail: [
          { required: true, message: '请输入邮箱', trigger: 'change' },
          { whitespace: true, message: '请输入邮箱', trigger: 'change' }
        ],
        phone: [
          { required: true, message: '请输入电话', trigger: 'change' },
          { whitespace: true, message: '请输入电话', trigger: 'change' },
          { min: 8, max: 16, message: '长度为8-16个字符', trigger: 'change' },
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
      url: {
        save: SysApis.saveUser,
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
          return { label: e.name, value: e.id }
        })
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
    },
    loadUserRoles() {
      this.$http.get(`${this.url.userRoles}/${this.form.id.id}`).then(result => {
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
    },
    closeForm() {
      this.form = {
        roleList: []
      }
      this.$emit('cancel')
    },
    submit() {
      this.$refs.userForm.validate(valid => {
        if (valid) {
          this.$http.post(this.url.save, this.form).then(result => {
            if (result.status !== 200) {
              this.$message.error(result.message)
            }
          }).catch(function (error) {
            console.error('出现错误:', error)
          }).finally(() => {
            this.closeForm()
          })
        }
      })
    },
    checkUsername(rule, value, callback) {
      this.$http.get(`${SysApis.checkUsername}/${value}`).then(result => {
        if (result.status !== 200) {
          callback(new Error('用户名已经存在'));
        }
        callback();
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

<template>
  <a-drawer :title="title" width="30%" :visible="visible" @close="closeForm" :destroy-on-close="true">
    <a-form-model :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" ref="ruleForm">
      <a-form-model-item label="id" prop="id" style="display: none">
        <a-input v-model="form.id" disabled placeholder="新建时自动生成"/>
      </a-form-model-item>
      <a-form-model-item label="用户名" prop="username">
        <a-input v-model="form.username" :disabled="isEdit" placeholder="请输入用户名"/>
      </a-form-model-item>
      <a-form-model-item label="姓名" prop="name">
        <a-input v-model="form.name" placeholder="请输入姓名"/>
      </a-form-model-item>
      <a-form-model-item label="性别" prop="gender">
        <a-radio-group v-model="form.gender">
          <a-radio :value="1">男</a-radio>
          <a-radio :value="0">女</a-radio>
        </a-radio-group>
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
      <a-form-model-item label="头像" prop="avatar">
        <c-upload-v2 v-model="form.avatar" list-type="picture-card" :max="1"/>
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
import CUploadV2 from '@comp/c-file-upload-v2.vue'
import { SysApis } from '@/api/Apis'
import system from '@/mixins/system'

export default {
  name: 'user-add',
  components: {CUploadV2},
  mixins: [system],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
    },
    close: {
      type: Function
    },
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    visible(nowValue, oldValue) {
      console.log('visible', this.form)
      if (nowValue && this.isEdit) {
        this.loadUserRoles()
      }
    }
  },
  data() {
    return {
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'change'},
          {min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change'},
          {whitespace: true, message: '请输入用户名', trigger: 'change'},
          {validator: this.checkChineseChar, message: '用户名不能包含中文', trigger: 'change'},
          {validator: this.checkUsername, message: '用户名已经存在', trigger: 'change'},
        ],
        name: [
          {required: true, message: '请输入名称', trigger: 'change'},
          {min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change'},
          {whitespace: true, message: '请输入名称', trigger: 'change'}
        ],
        age: [
          {required: true, message: '请输入年龄', trigger: 'change'}
        ],
        gender: [
          {required: true, message: '请选择性别', trigger: 'change'}
        ],
        mail: [
          {required: true, message: '请输入邮箱', trigger: 'change'},
          {whitespace: true, message: '请输入邮箱', trigger: 'change'}
        ],
        phone: [
          {required: true, message: '请输入电话', trigger: 'change'},
          {whitespace: true, message: '请输入电话', trigger: 'change'},
          {min: 11, max: 11, message: '长度为11个字符', trigger: 'change'}
        ]
      },
      columns: [
        {
          dataIndex: 'username',
          key: 'username',
          title: '用户名'
        },
        {
          dataIndex: 'name',
          title: '姓名',
          key: 'name'
        },
        {
          dataIndex: 'age',
          title: '年龄',
          key: 'age'
        },
        {
          dataIndex: 'gender_dictValue',
          title: '性别',
          key: 'gender'
        },
        {
          dataIndex: 'mail',
          title: '邮箱',
          key: 'mail'
        },

        {
          dataIndex: 'phone',
          title: '电话',
          key: 'phone'
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
          scopedSlots: {customRender: 'action'}
        }
      ],
      roleOptions: [],
      url: {
        save: SysApis.saveUser,
        userRoles: SysApis.userRoles,
        roleList: SysApis.roleList
      }
    }
  },
  activated() {

  },
  mounted() {
    this.getRoleList()
  },
  methods: {
    // submit() {
    //   console.log('this.form.avatar', this.form.avatar)
    // },
    getRoleList() {
      this.$http.get(this.url.roleList).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        this.roleOptions = result.data.map(e => {
          return {label: e.name, value: e.id}
        })
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
    },
    loadUserRoles() {
      this.$http.get(`${this.url.userRoles}/${this.form.id}`).then(result => {
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
    checkChineseChar(rule, value, callback) {
      if (new RegExp(/[\u4E00-\u9FA5]/g).test(value)) {
        callback(new Error('用户名不能包含中文'))
        return
      }
      callback()
    },
    checkUsername(rule, value, callback) {
      if (this.isEdit) {
        callback()
      } else {
        this.$http.get(`${SysApis.checkUsername}/${value}`).then(result => {
          if (result.status !== 200) {
            callback(new Error('用户名已经存在'))
          } else {
            callback()
          }
        }).catch(function (error) {
          console.error('出现错误:', error)
        })
      }
    }
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

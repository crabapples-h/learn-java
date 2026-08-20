<template>
  <a-drawer :title="title" width="30%" :open="open" @close="closeForm" :destroy-on-close="true">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="id" name="id" style="display: none">
        <a-input v-model:value="form.id" disabled placeholder="新建时自动生成" />
      </a-form-item>
      <a-form-item label="用户名" name="username">
        <a-input v-model:value="form.username" :disabled="isEdit" placeholder="请输入用户名" />
      </a-form-item>
      <a-form-item label="姓名" name="name">
        <a-input v-model:value="form.name" placeholder="请输入姓名" />
      </a-form-item>
      <a-form-item label="性别" name="gender">
        <a-radio-group v-model:value="form.gender">
          <a-radio :value="1">男</a-radio>
          <a-radio :value="0">女</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="年龄" name="age">
        <a-input-number v-model:value="form.age" placeholder="请输入年龄" />
      </a-form-item>
      <a-form-item label="邮箱" name="mail">
        <a-input v-model:value="form.mail" placeholder="请输入邮箱" />
      </a-form-item>
      <a-form-item label="电话" name="phone">
        <a-input v-model:value="form.phone" placeholder="请输入电话号码" />
      </a-form-item>
      <a-form-item label="头像" name="avatar">
        <c-upload-v2 :value="form.avatar" list-type="picture-card" :max="1"
                     @change="val => (form.avatar = val)" />
      </a-form-item>
      <a-form-item label="角色">
        <a-select v-model:value="form.roleList" mode="multiple" placeholder="请选择角色" :options="roleOptions" />
      </a-form-item>
    </a-form>
    <div class="drawer-bottom-button">
      <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
      <a-button type="primary" @click="onSubmit">保存</a-button>
    </div>
  </a-drawer>
</template>

<script setup>
import { ref, watch } from 'vue'
import { message } from 'ant-design-vue'
import request from '@/utils/request'
import { SysApis } from '@/api/Apis'
import { useSystem } from '@/utils/useSystem'
import CUploadV2 from '@/components/c-file-upload-v2.vue'

const props = defineProps({
  open: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: '',
  },
  isEdit: {
    type: Boolean,
    default: false,
  },
  editData: {
    type: Object,
    default: () => null,
  },
})

const emit = defineEmits(['close'])

const {
  form,
  formRef,
  labelCol,
  wrapperCol,
  submit,
} = useSystem({
  save: SysApis.saveUser,
})

const roleOptions = ref([])

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'change' },
    { min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change' },
    { whitespace: true, message: '请输入用户名', trigger: 'change' },
    { validator: checkChineseChar, message: '用户名不能包含中文', trigger: 'change' },
    { validator: checkUsername, message: '用户名已经存在', trigger: 'change' },
  ],
  name: [
    { required: true, message: '请输入名称', trigger: 'change' },
    { min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change' },
    { whitespace: true, message: '请输入名称', trigger: 'change' },
  ],
  age: [{ required: true, message: '请输入年龄', trigger: 'change' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  mail: [
    { required: true, message: '请输入邮箱', trigger: 'change' },
    { whitespace: true, message: '请输入邮箱', trigger: 'change' },
  ],
  phone: [
    { required: true, message: '请输入电话', trigger: 'change' },
    { whitespace: true, message: '请输入电话', trigger: 'change' },
    { min: 11, max: 11, message: '长度为11个字符', trigger: 'change' },
  ],
}

watch(
  () => props.open,
  val => {
    if (val) {
      form.value = props.isEdit && props.editData ? { ...props.editData } : {}
      if (props.isEdit) {
        loadUserRoles()
      }
    }
  },
)

const closeForm = () => {
  form.value = {}
  emit('close')
}

const onSubmit = () => submit(formRef.value, closeForm)

const getRoleList = async () => {
  try {
    const result = await request.get(SysApis.roleList)
    if (result.status !== 200) {
      message.error(result.message)
      return
    }
    roleOptions.value = result.data.map(e => ({ label: e.name, value: e.id }))
  } catch (error) {
    console.error('出现错误:', error)
  }
}

const loadUserRoles = async () => {
  try {
    const result = await request.get(`${SysApis.userRoles}/${form.value.id}`)
    if (result.status !== 200) {
      message.error(result.message)
      return
    }
    form.value.roleList = result.data.map(r => r.id)
  } catch (error) {
    console.error('出现错误:', error)
  }
}

function checkChineseChar(rule, value, callback) {
  if (value && new RegExp(/[一-龥]/g).test(value)) {
    callback(new Error('用户名不能包含中文'))
    return
  }
  callback()
}

function checkUsername(rule, value, callback) {
  if (props.isEdit) {
    callback()
    return
  }
  if (!value) {
    callback()
    return
  }
  request
    .get(`${SysApis.checkUsername}/${value}`)
    .then(result => {
      if (result.status !== 200) {
        callback(new Error('用户名已经存在'))
      } else {
        callback()
      }
    })
    .catch(error => {
      console.error('出现错误:', error)
      callback()
    })
}

getRoleList()
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

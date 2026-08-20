<template>
  <a-modal :open="open" title="修改密码" width="35%" ok-text="确认" cancel-text="取消" @ok="submit"
           @cancel="closeForm">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="新密码" name="newPassword">
        <a-input-password v-model:value="form.newPassword" />
      </a-form-item>
      <a-form-item label="重复密码" name="againPassword">
        <a-input-password v-model:value="form.againPassword" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
import { ref } from 'vue'
import { message } from 'ant-design-vue'
import request from '@/utils/request'
import { SysApis } from '@/api/Apis'
import { useSystem } from '@/utils/useSystem'

const props = defineProps({
  open: {
    type: Boolean,
    default: false,
  },
  userId: {
    type: String,
    default: '',
  },
})

const emit = defineEmits(['cancel'])

const { form, formRef, labelCol, wrapperCol } = useSystem({})

const rules = {
  newPassword: [
    { required: true, message: '请输入密码', trigger: 'change' },
    { min: 1, max: 16, message: '长度为8-16个字符', trigger: 'change' },
    { whitespace: true, message: '请输入密码', trigger: 'change' },
  ],
  againPassword: [
    { required: true, message: '请重复输入密码', trigger: 'change' },
    { min: 1, max: 16, message: '长度为8-16个字符', trigger: 'change' },
    { whitespace: true, message: '请重复输入密码', trigger: 'change' },
    { validator: checkPassword, message: '两次密码不一致', trigger: 'change' },
  ],
}

const closeForm = () => {
  form.value = {}
  emit('cancel')
}

const submit = async () => {
  let valid = false
  try {
    await formRef.value.validate()
    valid = true
  } catch (e) {
    console.log('表单校验失败:', e)
  }
  if (!valid) return
  form.value.id = props.userId
  try {
    const result = await request.post(SysApis.resetPassword, form.value)
    if (result.status !== 200) {
      message.error(result.message)
    }
  } catch (error) {
    console.error('出现错误:', error)
  } finally {
    closeForm()
  }
}

function checkPassword(rule, value, callback) {
  if (form.value.newPassword && form.value.newPassword === form.value.againPassword) {
    callback()
  } else {
    callback(new Error('两次密码不一致'))
  }
}
</script>

<style scoped>
</style>

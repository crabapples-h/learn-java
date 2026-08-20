<template>
  <a-modal :open="open" width="50%" ok-text="确认" cancel-text="取消" @ok="submit" @cancel="closeForm">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="字典项文字" name="text">
        <a-input v-model:value="form.text" />
      </a-form-item>
      <a-form-item label="字典项代码" name="value">
        <a-input v-model:value="form.value" />
      </a-form-item>
    </a-form>
    <div class="drawer-bottom-button">
      <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
      <a-button type="primary" @click="submit">保存</a-button>
    </div>
  </a-modal>
</template>

<script setup>
import { watch } from 'vue'
import { message } from 'ant-design-vue'
import request from '@/utils/request'
import { SysApis } from '@/api/Apis'
import { useSystem } from '@/utils/useSystem'

const props = defineProps({
  open: {
    type: Boolean,
    default: false,
  },
  dictCode: {
    type: String,
    default: '',
  },
  editData: {
    type: Object,
    default: () => null,
  },
})

const emit = defineEmits(['cancel'])

const {
  form,
  formRef,
  labelCol,
  wrapperCol,
} = useSystem({})

const rules = {
  text: [
    { required: true, message: '请输入字典项文字', trigger: 'change' },
    { whitespace: true, message: '请输入字典项文字', trigger: 'change' },
  ],
  value: [
    { required: true, message: '请输入代码', trigger: 'change' },
    { whitespace: true, message: '请输入代码', trigger: 'change' },
  ],
}

watch(
  () => props.open,
  val => {
    if (val) {
      form.value = props.editData ? { ...props.editData } : {}
    }
  },
)

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
    console.log('校验失败:', e)
  }
  if (!valid) return
  form.value.dictCode = props.dictCode
  try {
    const result = await request.post(SysApis.saveDictItems, form.value)
    if (result.status !== 200) {
      message.error(result.message)
    }
  } catch (error) {
    console.error('出现错误:', error)
  } finally {
    closeForm()
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

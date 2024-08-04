<template>
  <a-modal :visible="visible" width="50%" ok-text="确认" cancel-text="取消" @ok="submit" @cancel="closeForm">
    <a-form-model :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" ref="form">
      <a-form-model-item label="字典项代码" prop="code">
        <a-input v-model="form.code"/>
      </a-form-model-item>
      <a-form-model-item label="字典项值" prop="value">
        <a-input v-model="form.value"/>
      </a-form-model-item>
    </a-form-model>
    <div class="drawer-bottom-button">
      <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
      <a-button type="primary" @click="submit">保存</a-button>
    </div>
  </a-modal>

</template>

<script>

import { SysApis } from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'

export default {
  name: 'dict-item-add',
  mixins: [SystemMinix],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    cancel: {
      type: Function,
    },
    dictCode: {
      type: String,
      default: false
    },
  },
  data() {
    return {
      rules: {
        value: [
          { required: true, message: '请输入字典项值', trigger: 'change' },
          { whitespace: true, message: '请输入字典项值', trigger: 'change' }
        ],
        code: [
          { required: true, message: '请输入代码', trigger: 'change' },
          { whitespace: true, message: '请输入代码', trigger: 'change' }
        ],
      },
      form: {},
      url: {
        save: SysApis.saveDictItems,
      }
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {
    closeForm() {
      this.form = {}
      this.$emit('cancel')
    },
    submit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.form.dictCode = this.dictCode
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

<template>
  <a-modal title="修改密码" :visible.sync="visible" width="35%" ok-text="确认" cancel-text="取消"
           @ok="submit" @cancel="closeForm">
    <a-form-model ref="form" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-model-item ref="newPassword" label="新密码" prop="newPassword">
        <a-input-password v-model="form.newPassword" />
      </a-form-model-item>
      <a-form-model-item ref="rePassword" label="重复密码" prop="againPassword">
        <a-input-password v-model="form.againPassword" />
      </a-form-model-item>
    </a-form-model>
  </a-modal>
</template>

<script>

import { SysApis } from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'

export default {
  name: 'user-change-password',
  mixins: [SystemMinix],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    cancel: {
      type: Function
    },
    userId: {
      type: String,
      default: false
    }
  },
  data() {
    return {
      rules: {
        newPassword: [
          { required: true, message: '请输入密码', trigger: 'change' },
          { min: 1, max: 16, message: '长度为8-16个字符', trigger: 'change' },
          { whitespace: true, message: '请输入密码', trigger: 'change' }
        ],
        againPassword: [
          { required: true, message: '请重复输入密码', trigger: 'change' },
          { min: 1, max: 16, message: '长度为8-16个字符', trigger: 'change' },
          { whitespace: true, message: '请重复输入密码', trigger: 'change' },
          { validator: this.checkPassword, message: '两次密码不一致', trigger: 'change' }
        ]
      },
      url: {
        reset: SysApis.resetPassword
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
      console.log('form', this.form)
      this.$refs.form.validate(valid => {
        console.log('form', valid)
        if (valid) {
          this.form.id = this.userId
          this.$http.post(this.url.reset, this.form).then(result => {
            if (result.status !== 200) {
              this.$message.error(result.message)
            }
          }).catch(function(error) {
            console.error('出现错误:', error)
          }).finally(() => {
            this.closeForm()
          })
        }
      })
    },
    checkPassword(rule, value, callback) {
      console.log(rule,value,this.form)
      if (this.form.newPassword && (this.form.newPassword === this.form.againPassword)) {
        callback()
      } else {
        callback(new Error('两次密码不一致'))
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

<template>
  <a-drawer title="字典详情" width="50%" :visible="visible" @close="closeDetail">
    <a-button type="primary" size="small" @click="showAddItem()">新增</a-button>
    <a-divider/>
    <a-table :data-source="dictItemList" key="id" bordered>
      <a-table-column key="code" title="字典项代码" data-index="code"/>
      <a-table-column key="value" title="字典项值" data-index="value"/>
      <a-table-column key="id" title="操作" data-index="id">
        <template slot-scope="text, record">
          <a-button type="primary" size="small" @click="showEditItem(record)">编辑</a-button>
          <a-divider type="vertical"/>
          <c-pop-button title="确定要删除吗" text="删除" type="danger" @click="removeDictItem(record)"/>
        </template>
      </a-table-column>
    </a-table>
  </a-drawer>
</template>

<script>

import { SysApis } from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'

export default {
  name: 'dict-add',
  mixins: [SystemMinix],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    cancel: {
      type: Function,
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
        save: SysApis.saveDicts,
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

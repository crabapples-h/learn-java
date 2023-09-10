<template>
  <div>
    <a-button @click="showAdd()" v-auth:sys:menus:add>添加字典</a-button>
    <a-divider/>
    <a-drawer title="字典详情" width="50%" :visible="show.dictItem" @close="closeDictItem">
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
    <a-modal :visible="show.addItem" width="50%" ok-text="确认" cancel-text="取消" @ok="submitAddItemForm"
             @cancel="closeAddItem">
      <a-form-model :model="itemForm" :rules="formRules"
                    :label-col="labelCol"
                    :wrapper-col="wrapperCol"
                    ref="dictItemForm">
        <a-form-model-item label="字典项代码" prop="code">
          <a-input v-model="itemForm.code"/>
        </a-form-model-item>
        <a-form-model-item label="字典项值" prop="value">
          <a-input v-model="itemForm.value"/>
        </a-form-model-item>
      </a-form-model>
      <div class="drawer-bottom-button">
        <a-button :style="{ marginRight: '8px' }" @click="closeAddItem">关闭</a-button>
        <a-button type="primary" @click="submitAddItemForm">保存</a-button>
      </div>
    </a-modal>
    <a-modal :visible="show.add" width="50%" ok-text="确认" cancel-text="取消" @ok="submitForm"
             @cancel="closeForm">
      <a-form-model :model="form" :rules="formRules"
                    :label-col="labelCol"
                    :wrapper-col="wrapperCol"
                    ref="dictForm">
        <a-form-model-item label="ID" style="display: none">
          <a-input v-model="form.id" disabled placeholder="新建字典时自动生成"/>
        </a-form-model-item>
        <a-form-model-item label="字典名称" prop="name">
          <a-input v-model="form.name"/>
        </a-form-model-item>
        <a-form-model-item label="字典代码" prop="code">
          <a-input v-model="form.code"/>
        </a-form-model-item>
        <a-form-model-item label="排序" prop="sort">
          <a-input v-model="form.sort"/>
        </a-form-model-item>
      </a-form-model>
      <div class="drawer-bottom-button">
        <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
        <a-button type="primary" @click="submitForm">保存</a-button>
      </div>
    </a-modal>
    <a-table :data-source="dataSource" key="id" :columns="columns" :pagination="pagination">
      <span slot="action" slot-scope="text, record">
        <c-pop-button title="确定要删除吗" text="删除" type="danger" @click="remove(record)"/>
        <a-divider type="vertical"/>
        <a-button type="primary" size="small" @click="showEdit(record)">编辑</a-button>
        <a-divider type="vertical"/>
        <a-button type="primary" size="small" @click="showAddItem(record)">添加字典项</a-button>
        <a-divider type="vertical"/>
        <a-button type="primary" size="small" @click="showDictItem(record)">查看字典项</a-button>
      </span>
      <span slot="icon" slot-scope="text, record">
        <a-icon :type='text.substring(text.indexOf("\"") + 1,text.lastIndexOf("\"")) || "appstore"'/>
      </span>
    </a-table>
  </div>
</template>

<script>

import { SysApis } from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'

export default {
  name: 'menus-list',
  mixins: [SystemMinix],
  data() {
    return {
      formRules: {
        value: [
          { required: true, message: '请输入字典项值', trigger: 'change' },
          { whitespace: true, message: '请输入字典项值', trigger: 'change' }
        ],
      },
      columns: [
        {
          dataIndex: 'name',
          title: '名称',
        },
        {
          dataIndex: 'code',
          title: '代码',
        },
        {
          dataIndex: 'sort',
          title: '排序',
        },
        {
          dataIndex: 'action',
          title: '操作',
          scopedSlots: { customRender: 'action' },
        },
      ],
      dataSource: [],
      dictItemList: [],
      itemForm: {},
      show: {
        add: false,
        addItem: false,
        dictItem: false,
      },
      url: {
        list: SysApis.dictPage,
        save: SysApis.saveDicts,
        delete: SysApis.delDicts,
        saveDictItems: SysApis.saveDictItems,
        dictItemListByCode: SysApis.dictItemListByCode,
        deleteDictItems: SysApis.delDictItems,
      }
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {
    showDictItem(e) {
      this.itemForm.dictCode = e.code
      this.$http.get(`${this.url.dictItemListByCode}/${e.code}`,).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        if (result.data !== null) {
          this.dictItemList = result.data.records || result.data
        }
        this.show.dictItem = true
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
    },
    closeDictItem() {
      this.show.dictItem = false
    },
    showAddItem(e) {
      if (e) {
        this.itemForm.dictCode = e.code
      }
      this.show.addItem = true
    },
    showEditItem(e) {
      this.itemForm = e
      this.show.addItem = true
    },
    closeAddItem(e) {
      this.show.addItem = false
    },
    submitAddItemForm() {
      this.$refs.dictItemForm.validate(valid => {
        if (valid) {
          this.$http.post(this.url.saveDictItems, this.itemForm).then(result => {
            if (result.status !== 200) {
              this.$message.error(result.message)
              return
            }
            this.closeAddItem()
          }).catch(function (error) {
            console.error('出现错误:', error)
          })
        }
      })
    },
    showAdd() {
      this.show.add = true
    },
    showEdit(e) {
      this.form = e
      this.show.add = true
    },
    closeForm() {
      this.show.add = false
      this.refreshData()
    },
    submitForm() {
      this.$refs.dictForm.validate(valid => {
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
        } else {
          console.log('error submit!!')
          return false
        }
      })

    },
    removeDictItem(e) {
      this.$http.post(`${this.url.deleteDictItems}/${e.id}`).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        this.showDictItem(e)
        this.$message.success(result.message)
      }).catch(function (error) {
        console.log('请求出现错误:', error)
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

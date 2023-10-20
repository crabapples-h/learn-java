<template>
  <div>
    <a-button @click="showAdd()" v-auth:sys:menus:add>添加字典</a-button>
    <a-divider/>
    <dict-detail/>
    <add-dict :visible="show.add" @cancel="closeForm" :is-edit="show.edit" ref="AddDict"/>
    <add-dict-item :visible="show.addItem" width="50%" ok-text="确认" cancel-text="取消" @cancel="closeItemForm"/>
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
import AddDict from '@/views/manage/sys-dict/add.vue'
import AddDictItem from '@/views/manage/sys-dict/add-item.vue'
import DictDetail from '@/views/manage/sys-dict/detail.vue'

export default {
  name: 'menus-list',
  mixins: [SystemMinix],
  components: {
    AddDict,
    AddDictItem,
    DictDetail
  },
  data() {
    return {
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
      show: {
        add: false,
        edit: false,
        dictItem: false,
      },
      url: {
        list: SysApis.dictPage,
        delete: SysApis.delDicts,
      },
      dictCode: '',
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {
    showAdd() {
      this.show.add = true
    },
    closeForm() {
      this.show.add = false
      this.show.edit = false
      this.refreshData()
    },
    showEdit(e) {
      this.form = e
      this.show.add = true
      this.show.edit = true
    },

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
      this.dictCode = e.code
      this.show.addItem = true
    },
    showEditItem(e) {
      this.itemForm = e
      this.show.addItem = true
    },
    closeItemForm(e) {
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

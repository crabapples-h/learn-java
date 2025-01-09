<template>
  <div>
    <a-form layout="inline" @keyup.enter.native="getList">
      <a-space align="center" style="flex-wrap: wrap">
        <a-form-item label="菜单">
          <a-input placeholder="请输入菜单" v-model="queryParam.name" :allow-clear="true"/>
        </a-form-item>
        <a-button type="default" @click="getList" icon="search">查询</a-button>
        <a-button type="default" @click="resetSearch" icon="reload">重置</a-button>
        <a-button type="primary" @click="showAdd" icon="plus" v-auth:sys:menus:add ghost>添加</a-button>
      </a-space>
    </a-form>
    <a-divider/>
    <a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="pagination"
             :scroll="{x:true}" bordered>
      <span slot="action" slot-scope="text, record">
      <a-space align="center" style="flex-wrap: wrap">
        <c-pop-button title="确定要删除吗" text="删除" type="danger" @click="remove(record)" v-auth:sys:menus:del/>
        <a-button type="primary" size="small" @click="showEdit(record)" v-auth:sys:menus:edit>编辑</a-button>
        <span v-if="record.menusType === 1">
          <a-button type="primary" size="small" @click="showAddChild(record)"
                    v-auth:sys:menus:add-children>添加子菜单</a-button>
        </span>
        </a-space>
      </span>
      <span slot="icon" slot-scope="text, record">
           <svg class="iconfont" aria-hidden="true" style="width: 50px;height: 50px">
            <use :xlink:href="'#icon-'+text"></use>
          </svg>
      </span>
      <span slot="type" slot-scope="text, record">
        <a-tag size="small" color="green" v-if="record.menusType === 1">菜单</a-tag>
        <a-tag size="small" color="blue" v-if="record.menusType === 2">按钮</a-tag>
        <a-tag size="small" color="purple" v-if="record.menusType === 3">超链接</a-tag>
      </span>
    </a-table>
    <add-menu :visible="show.add" @close="closeAdd"/>
    <add-menu :visible="show.edit" @close="closeEdit" ref="editForm"/>
  </div>
</template>

<script>

import {SysApis} from '@/api/Apis'
import system from '@/mixins/system'
import AddMenu from '@/views/manage/sys-menu/add.vue'

export default {
  name: 'menu-list',
  mixins: [system],
  components: {
    AddMenu
  },
  data() {
    return {
      columns: [
        {
          dataIndex: 'name',
          title: '名称',
          align: 'center',
          width: 180
        },
        {
          dataIndex: 'icon',
          title: '图标',
          scopedSlots: {customRender: 'icon'},
          align: 'center',
          width: 80
        },
        {
          dataIndex: 'sort',
          title: '排序',
          align: 'center',
          width: 80
        },
        {
          dataIndex: 'type',
          title: '类型',
          scopedSlots: {customRender: 'type'},
          align: 'center',
          width: 80
        },
        {
          dataIndex: 'permission',
          title: '授权标识',
          align: 'center',
        },
        {
          title: '操作',
          key: 'action',
          scopedSlots: {customRender: 'action'},
          width: 250
        }
      ],
      dataSource: [],
      show: {
        add: false,
      },
      url: {
        list: SysApis.menuPage,
        remove: SysApis.delMenus,
      }
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {
    getList() {
      let page = this.getQueryPage()
      this.$http.get(this.url.list, {params: page}).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        if (result.data !== null) {
          let format = function (data) {
            return data.map(e => {
              if (e.children && e.children.length > 0) {
                e.children = format(e.children)
              } else {
                delete e.children;
              }
              return e
            }).sort((a, b) => {
              return a.sort - b.sort
            })
          }
          this.dataSource = format(result.data.records)
          this.pagination.total = result.data.total || result.data.totalRow
          this.pagination.current = result.data.current || result.data.pageNumber
          this.pagination.pageSize = result.data.size || result.data.pageSize
        }
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
    },

    showAddChild(e) {
      this.$refs.editForm.form.pid = e.id
      this.show.edit = true
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

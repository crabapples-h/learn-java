<template>
  <div>
    <a-button @click="addRoles" v-auth:sys:roles:add>添加角色</a-button>
    <a-divider/>
    <a-drawer title="" width="50%" :visible="show.roles" @close="closeRolesForm">
      <a-form-model :model="form" :rules="formRules" :label-col="labelCol" :wrapper-col="wrapperCol" ref="roleForm">
        <a-form-model-item label="ID" style="display: none">
          <a-input v-model="form.id" disabled placeholder="新建时自动生成"/>
        </a-form-model-item>
        <a-form-model-item label="名称" prop="name">
          <a-input v-model="form.name"/>
        </a-form-model-item>
        <a-form-model-item label="菜单">
          <a-tree-select
            :tree-data="menusOptions"
            v-model="form.menusList"
            tree-checkable
            :show-checked-strategy="SHOW_TYPE"
            :show-line="show.treeLine"
            :checkStrictly="false"
            :replace-fields="replaceFields"
            v-if="false"/>
          <a-tree
            v-model="form.menusList"
            :checkable="true"
            :default-expand-all="true"
            :check-strictly="false"
            :selected-keys="form.menusList"
            :tree-data="menusOptions"
            :replace-fields="replaceFields"/>
        </a-form-model-item>
      </a-form-model>
      <div class="drawer-bottom-button">
        <a-button :style="{ marginRight: '8px' }" @click="closeRolesForm">关闭</a-button>
        <a-button type="primary" @click="submitRolesForm">保存</a-button>
      </div>
    </a-drawer>
    <a-modal title="菜单列表" :visible.sync="show.menus" width="50%" :footer="null" @cancel="closeShowMenus">
      <div style="height: 60vh;overflow-y: scroll">
        <a-table :data-source="menusDataSource"
                 :columns="menusColumns"
                 :pagination="false">
        <span slot="icon" slot-scope="text, record">
        <a-icon :type='text.substring(text.indexOf("\"") + 1,text.lastIndexOf("\"")) || "appstore"'/>
      </span>
          <span slot="type" slot-scope="text, record">
        <a-tag size="small" color="green" v-if="record.menusType === 1">菜单</a-tag>
        <a-tag size="small" color="blue" v-if="record.menusType === 2">按钮</a-tag>
        <a-tag size="small" color="purple" v-if="record.menusType === 3">超链接</a-tag>
      </span>
        </a-table>
      </div>
    </a-modal>
    <a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="pagination">
      <span slot="action" slot-scope="text, record">
        <c-pop-button title="确定要删除吗" text="删除" type="danger" size="small" @click="removeRoles(record)"
                      v-auth:sys:roles:del/>
        <a-divider type="vertical" v-auth:sys:roles:del/>
        <a-button type="primary" size="small" @click="editRoles(record)" v-auth:sys:roles:edit>编辑</a-button>
        <a-divider type="vertical" v-auth:sys:roles:edit/>
        <a-button type="primary" size="small" @click="showMenus(record)">查看菜单</a-button>
      </span>
    </a-table>
  </div>
</template>

<script>
import commonApi from '@/api/CommonApi'
import { SysApis } from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'

export default {
  name: 'roles-list',
  mixins: [SystemMinix],
  data() {
    return {
      formRules: {},
      columns: [
        {
          dataIndex: 'name',
          title: '角色',
          key: 'name',
          width: '50%'
        },
        {
          title: '操作',
          key: 'action',
          scopedSlots: { customRender: 'action' },
          width: '50%'
        },
      ],
      menusColumns: [
        {
          dataIndex: 'name',
          title: '名称',
        },
        {
          dataIndex: 'icon',
          title: '图标',
          scopedSlots: { customRender: 'icon' },
        },
        {
          dataIndex: 'sort',
          title: '排序',
        },
        {
          dataIndex: 'type',
          title: '类型',
          scopedSlots: { customRender: 'type' },
        },
      ],
      menusDataSource: [],
      show: {
        treeLine: true,
        roles: false,
        menus: false,
      },
      menusOptions: [],
      replaceFields: {
        children: 'children', title: 'name', key: 'id'
      },
      url: {
        list: SysApis.rolePage,
        delete: SysApis.delRoles,
        roleMenus: SysApis.roleMenus,
        save: SysApis.saveRoles,
        menuList: SysApis.menuList,
      },
      allMenuList: []
    }
  },
  activated() {
  },
  mounted() {
    this.getMenusList()
  },
  methods: {
    refreshData() {
      this.resetForm()
      this.getList()
    },
    removeRoles(e) {
      this.$http.post(`${this.url.delete}/${e.id}`).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        this.$message.success(result.message)
        this.refreshData()
      }).catch(function (error) {
        console.log('请求出现错误:', error)
      })
    },
    addRoles() {
      this.getMenusList()
      this.show.roles = true
    },
    editRoles(e) {
      this.getMenusList()
      this.form.id = e.id
      this.form.name = e.name
      this.$http.get(`${this.url.roleMenus}/${e.id}`).then(result => {
        let hasMenuList = result.data.map(e => {
          return { id: e.id, name: e.name, pid: e.pid, sort: e.sort }
        })
        /*
         * 新增子菜单后，需要将其父级菜单设置为未选择状态
         * 1.首先从两个数组中筛选出不一样的元素，这些元素就是没有权限的菜单
         * 2.记录下没有权限的菜单的pid
         */
        let differentMenuList = this.allMenuList.filter(e => {
          return hasMenuList.every(r => {
            return e.id !== r.id
          })
        }).map(e => e.pid)
        /*
         * 3.如果角色拥有的菜单中包含了没有权限的菜单则将其过滤掉
         */
        this.form.menusList = result.data.filter(e => {
          return !differentMenuList.includes(e.id)
        }).map(e => e.id)
      })
      this.show.roles = true
    },
    closeRolesForm() {
      this.show.roles = false
      this.refreshData()
      commonApi.refreshSysData()
    },
    submitRolesForm() {
      this.$refs.roleForm.validate(valid => {
        if (valid) {
          this.$http.post(this.url.save, this.form).then(result => {
            if (result.status !== 200) {
              this.$message.error(result.message)
              return
            }
            this.closeRolesForm()
          }).catch(function (error) {
            console.error('出现错误:', error)
          })
        }
      })
    },
    tree2list(list, data) {
      list.forEach(r => {
        data.push({ id: r.id, name: r.name, pid: r.pid, sort: r.sort })
        this.tree2list(r.children, data)
      })
    },
    getMenusList() {
      this.$http.get(this.url.menuList).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        if (result.data !== null) {
          let allMenuList = []
          this.menusOptions = result.data
          this.tree2list(this.menusOptions, allMenuList)
          this.allMenuList = allMenuList
        }
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
    },
    showMenus(e) {
      const format = function (data) {
        return data.map(e => {
          let menus = {
            id: e.id,
            key: e.id,
            name: e.name,
            icon: e.icon,
            url: e.path,
            sort: e.sort,
            menusType: e.menusType,
          }
          if (e.children && e.children.length > 0) {
            menus.children = format(e.children)
          }
          return menus
        }).sort((a, b) => {
          return a.sort - b.sort
        })
      }
      this.$http.get(`${this.url.roleMenus}/${e.id}`).then(result => {
        this.menusDataSource = format(result.data)
        this.show.menus = true
      })
    },
    closeShowMenus() {
      this.show.menus = false
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

<template>
  <div>
    <a-button @click="addRoles" v-auth:sys:roles:add>添加角色</a-button>
    <a-divider/>
    <a-drawer title="" width="50%" :visible="show.roles" @close="closeRolesForm">
      <a-form-model :model="form.roles" :rules="rules.roles" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-model-item label="ID" style="display: none">
          <a-input v-model="form.roles.id" disabled placeholder="新建时自动生成"/>
        </a-form-model-item>
        <a-form-model-item label="名称" prop="name">
          <a-input v-model="form.roles.name"/>
        </a-form-model-item>
        <a-form-model-item label="菜单">
          <a-tree-select :tree-data="menusOptions" v-model="form.roles.menusList" tree-checkable
                         :show-checked-strategy="SHOW_TYPE" :show-line="show.treeLine" :checkStrictly="false"/>
        </a-form-model-item>
      </a-form-model>
      <div class="drawer-bottom-button">
        <a-button :style="{ marginRight: '8px' }" @click="closeRolesForm">关闭</a-button>
        <a-button type="primary" @click="submitRolesForm">保存</a-button>
      </div>
    </a-drawer>
    <a-modal :visible.sync="show.menus" width="50%" :footer="null" @cancel="closeShowMenus">
      <a-table :data-source="menusDataSource" key="id" :columns="menusColumns" :pagination="false">
        <span slot="icon" slot-scope="text, record">
        <a-icon :type='text.substring(text.indexOf("\"") + 1,text.lastIndexOf("\"")) || "appstore"'/>
      </span>
        <span slot="type" slot-scope="text, record">
        <a-tag size="small" color="green" v-if="record.menusType === 1">菜单</a-tag>
        <a-tag size="small" color="blue" v-if="record.menusType === 2">按钮</a-tag>
        <a-tag size="small" color="purple" v-if="record.menusType === 3">超链接</a-tag>
      </span>
      </a-table>
    </a-modal>
    <a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="pagination">
      <span slot="action" slot-scope="text, record">
        <c-pop-button title="确定要删除吗" text="删除" type="danger" size="small" @click="removeRoles(record)"
                      v-auth:sys:roles:del/>
        <a-divider type="vertical"/>
        <a-button type="primary" size="small" @click="editRoles(record)" v-auth:sys:roles:edit>编辑</a-button>
        <a-divider type="vertical"/>
        <a-button type="primary" size="small" @click="showMenus(record)">查看菜单</a-button>
      </span>
    </a-table>
  </div>
</template>

<script>
import { TreeSelect } from 'ant-design-vue'
import CPopButton from '@/components/c-pop-button'
import { initCPagination } from '@/views/common/C-Pagination.js'
import commonApi from '@/api/CommonApi'
import { SysApis } from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'

export default {
  name: 'roles-list',
  components: {
    CPopButton,
  },
  mixins: [SystemMinix],
  data() {
    return {
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
      rules: {
        roles: {
          name: [
            { required: true, message: '请输入角色名称', trigger: 'blur' },
            { min: 2, max: 16, message: '长度为2-16个字符', trigger: 'blur' },
            { whitespace: true, message: '请输入角色名称', trigger: 'blur' }
          ],
        },
      },
      form: {
        roles: {
          id: '',
          name: '',
          menusList: [],
        },
      },
      show: {
        treeLine: true,
        roles: false,
        menus: false,
      },
      menusOptions: [],
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {
    resetRolesForm() {
      this.form.roles = {
        id: '',
        name: '',
        menusList: [],
      }
    },
    refreshData() {
      this.resetRolesForm()
      this.getList()
    },
    getList({ current, pageSize }) {
      let page = { pageIndex: current, pageSize }
      this.$http.get(SysApis.rolesList, { params: page }).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        if (result.data !== null) {
          this.dataSource = result.data.content || result.data.content
          this.pagination.total = result.data.pageable.dataCount
          this.pagination.current = result.data.pageable.pageIndex + 1
        }
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
    },
    removeRoles(e) {
      this.$http.post(`${SysApis.delRoles}/${e.id}`).then(result => {
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
      this.show.roles = true
      this.getMenusList()
    },
    editRoles(e) {
      this.form.roles.id = e.id
      this.form.roles.name = e.name
      let ids = []
      this.formatDefaultMenusOption(ids, e.sysMenus)
      this.form.roles.menusList = ids
      this.getMenusList()
      this.show.roles = true
    },
    closeRolesForm() {
      this.show.roles = false
      this.refreshData()
      commonApi.refreshSysData()
    },
    submitRolesForm() {
      this.$http.post(SysApis.saveRoles, this.form.roles).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        this.closeRolesForm()
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
    },
    getMenusList() {

      this.$http.get(SysApis.menusList).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        if (result.data !== null) {
          this.menusOptions = result.data
        }
        this.menusOptions = this.formatMenusOption(result.data)
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
    },
    formatMenusOption(tree) {
      return tree.map(e => {
        return {
          title: e.name,
          value: e.id,
          key: e.id,
          children: this.formatMenusOption(e.children)
        }
      })
    },
    formatDefaultMenusOption(ids, rolesMenus) {
      rolesMenus.forEach(e => {
        let children = e.children
        if (children.length >= 0) {
          this.formatDefaultMenusOption(ids, children)
        }
        if (e.delFlag === 0) {
          ids.push(e.id)
        }
      })
    },
    showMenus(e) {
      this.$http.get(SysApis.menusList).then(result => {
        console.log(result)
      })
      console.log(e)
      let format = function (data) {
        return data.map(e => {
          let menus = {
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
      this.menusDataSource = format(e.sysMenus)
      console.log(this.menusDataSource)
      this.show.menus = true
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

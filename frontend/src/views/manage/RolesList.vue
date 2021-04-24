<template>
  <div>
    <a-button @click="addRoles">添加角色</a-button>
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
      <a-table :data-source="menusDataSource" rowKey="id" :columns="menusColumns" :pagination="false">
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
    <a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="false">
      <span slot="action" slot-scope="text, record">
        <c-pop-button title="确定要删除吗" text="删除" type="danger" size="small" @click="removeRoles(record)"/>
        <a-divider type="vertical"/>
        <a-button type="primary" size="small" @click="editRoles(record)">编辑</a-button>
        <a-divider type="vertical"/>
        <a-button type="primary" size="small" @click="showMenus(record)">查看菜单</a-button>
      </span>
    </a-table>
  </div>
</template>

<script>
import {TreeSelect} from "ant-design-vue";
import CPopButton from "@/components/c-pop-button";
import {SHOW_CHILD} from "ant-design-vue/lib/vc-tree-select";
import commonApi from "@/api/CommonApi"
export default {
  name: "roles-list",
  components: {CPopButton},
  data() {
    return {
      SHOW_TYPE: TreeSelect.SHOW_CHILD,
      labelCol: {span: 5},
      wrapperCol: {span: 16},
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
          scopedSlots: {customRender: 'action'},
          width: '50%'
        },
      ],
      dataSource: [],
      menusColumns: [
        {
          dataIndex: 'name',
          title: '名称',
        },
        {
          dataIndex: 'icon',
          title: '图标',
          scopedSlots: {customRender: 'icon'},
        },
        {
          dataIndex: 'sort',
          title: '排序',
        },
        {
          dataIndex: 'type',
          title: '类型',
          scopedSlots: {customRender: 'type'},
        },
      ],
      menusDataSource: [],
      rules: {
        roles: {
          name: [
            {required: true, message: '请输入角色名称', trigger: 'blur'},
            {min: 2, max: 16, message: '长度为2-16个字符', trigger: 'blur'},
            {whitespace: true, message: '请输入角色名称', trigger: 'blur'}
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
    };
  },
  activated() {
    this.getList()
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
    getList() {
      this.$http.get('/api/sys/roles/list/page').then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        if (result.data !== null) {
          this.dataSource = result.data;
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
    },
    removeRoles(e) {
      this.$http.post(`/api/sys/roles/remove/${e.id}`).then(result => {
        console.log('通过api获取到的数据:', result);
        if (result.status !== 200) {
          this.$message.error(result.message);
          return
        }
        this.$message.success(result.message)
        this.refreshData()
      }).catch(function (error) {
        console.log('请求出现错误:', error);
      });
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
      this.$http.post('/api/sys/roles/save', this.form.roles).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        this.closeRolesForm()
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
    },
    getMenusList() {
      this.$http.get('/api/sys/menus/list').then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        if (result.data !== null) {
          this.menusOptions = result.data;
        }
        this.menusOptions = this.formatMenusOption(result.data)
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
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
      let format = function (data) {
        return data.map(e => {
          let menus = {
            key: e.id,
            name: e.name,
            icon: e.icon,
            url: e.path,
            sort: e.sort
          }
          if (e.children && e.children.length > 0) {
            menus.children = format(e.children)
          }
          return menus
        }).sort((a, b) => {
          return a.sort - b.sort
        });
      }
      this.menusDataSource = format(e.sysMenus)
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
<template>
  <div>
    <a-button @click="addRoles">添加角色</a-button>
    <a-divider/>
    <a-drawer title="" width="50%" :visible="show.roles" @close="closeRolesForm">
      <a-form-model :model="form.roles" :rules="rules.roles" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-model-item label="ID">
          <a-input v-model="form.roles.id" disabled placeholder="新建角色时自动生成"/>
        </a-form-model-item>
        <a-form-model-item label="名称" prop="name">
          <a-input v-model="form.roles.name"/>
        </a-form-model-item>
        <a-form-model-item label="菜单">
          <a-tree-select :tree-data="menusOptions" v-model="form.roles.menusList" tree-checkable
                         :show-checked-strategy="SHOW_PARENT"/>
        </a-form-model-item>
      </a-form-model>
      <div class="drawer-bottom-button">
        <a-button :style="{ marginRight: '8px' }" @click="closeRolesForm">关闭</a-button>
        <a-button type="primary" @click="submitRolesForm">保存</a-button>
      </div>
    </a-drawer>
    <a-modal :visible.sync="show.test" width="50%" ok-text="确认" cancel-text="取消" @ok="" @cancel=""></a-modal>
    <!--    <a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="false">-->
    <!--      <span slot="action" slot-scope="text, record">-->
    <!--        <a-button type="danger" size="small" @click="removeRoles(record)">删除</a-button>-->
    <!--        <a-divider type="vertical"/>-->
    <!--        <a-button type="primary" size="small" @click="editRoles(record)">编辑</a-button>-->
    <!--        <a-divider type="vertical"/>-->
    <!--        <a-button type="primary" size="small" @click="editRoles(record)">分配菜单</a-button>-->
    <!--      </span>-->
    <!--    </a-table>-->
    <!--    :row-selection="rowSelection"-->
    <a-table :columns="columns" :data-source="dataSource"/>
  </div>
</template>

<script>
import {TreeSelect} from "ant-design-vue";

export default {
  name: "menus-list",
  data() {
    return {
      SHOW_PARENT: TreeSelect.SHOW_PARENT,
      labelCol: {span: 5},
      wrapperCol: {span: 16},
      sysUser: sessionStorage.getItem("sysUser"),
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
        roles: false,
        test: false,
      },
      menusOptions: [],
      defaultMenusOptions: [],
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
      this.$http.get('/api/sys/menus/list').then(result => {
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
      const _this = this;
      this.$confirm({
        title: '确认操作?',
        cancelText: '取消',
        okText: '确定',
        onOk() {
          console.log(e)
          _this.$http.post(`/api/sys/roles/remove/${e.id}`).then(result => {
            console.log('通过api获取到的数据:', result);
            if (result.status !== 200) {
              _this.$message.error(result.message);
              return
            }
            _this.getList()
            _this.$message.success(result.message)
          }).catch(function (error) {
            console.log('请求出现错误:', error);
          });
        },
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
        if (children.length > 0) {
          this.formatDefaultMenusOption(ids, children)
        } else {
          ids.push(e.id)
        }
      })
    },

    closeResetPassword() {
      this.show.resetPassword = false
    },
    showResetPassword(e) {
      this.form.resetPassword.id = e.id
      this.show.resetPassword = true
    },
    submitResetPassword() {
      console.log(this.form.resetPassword)
      this.$http.post('/api/user/resetPassword', this.form.resetPassword).then(result => {
        console.log('通过api获取到的数据:', result);
        if (result.status !== 200) {
          this.$message.error(result.message);
          return
        }
        this.$message.success(result.message)
      }).catch(function (error) {
        console.log('请求出现错误:', error);
      });
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
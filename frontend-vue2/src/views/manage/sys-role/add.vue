<template>
  <a-drawer title="添加角色" width="50%" :visible="visible" @close="closeForm">
    <a-form-model :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol"
                  ref="roleForm">
      <a-form-model-item label="ID" style="display: none">
        <a-input v-model="form.id" disabled placeholder="新建时自动生成"/>
      </a-form-model-item>
      <a-form-model-item label="名称" prop="name">
        <a-input v-model="form.name"/>
      </a-form-model-item>
      <a-form-model-item label="菜单">
        <a-tree-select
          :tree-data="menusOptions"
          v-model="form.hasMenusIds"
          tree-checkable
          :show-checked-strategy="SHOW_TYPE"
          :show-line="show.treeLine"
          :checkStrictly="false"
          :replace-fields="replaceFields"
          v-if="false"/>
        <a-tree
          v-model="form.hasMenusIds"
          :checkable="true"
          :default-expand-all="true"
          :check-strictly="false"
          :selected-keys="form.hasMenusIds"
          :tree-data="menusOptions"
          :replace-fields="replaceFields"/>
      </a-form-model-item>
    </a-form-model>
    <div class="drawer-bottom-button">
      <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
      <a-button type="primary" @click="submit">保存</a-button>
    </div>
  </a-drawer>
</template>

<script>
import { SysApis } from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'

export default {
  name: 'add-role',
  mixins: [SystemMinix],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    cancel: {
      type: Function,
    },
    isEdit: {
      type: Boolean,
      default: false
    },
  },
  watch: {
    isEdit(nowValue, oldValue) {
      if (nowValue) {
        this.loadRoleMenus()
      }
    }
  },
  data() {
    return {
      replaceFields: {
        children: 'children', title: 'name', key: 'id'
      },
      rules: {},
      show: {
        treeLine: true,
      },
      menusOptions: [],
      url: {
        save: SysApis.saveRoles,
        menuList: SysApis.menuList,
        roleMenus: SysApis.roleMenus,
      },
      form:{
        hasMenusIds:[]
      },
      allMenuList: [],
    }
  },
  activated() {
    this.getMenusList()
  },
  mounted() {
  },
  methods: {
    loadRoleMenus() {
      this.$http.get(`${this.url.roleMenus}/${this.form.id}`).then(result => {
        console.log(result.data)
        let hasMenus = []
        let hasMenusIds = []
        result.data.forEach(e => {
          hasMenus.push({ id: e.id, name: e.name, pid: e.pid, sort: e.sort })
          hasMenusIds.push(e.id)
        })
        this.form.hasMenusIds = hasMenusIds
        // console.log('hasMenus', hasMenus)
        console.log('hasMenusIds', hasMenusIds)

        /*
        * 新增子菜单后再编辑时，需要将其父级菜单设置为未选择状态
        * 1.首先从两个数组中筛选出不一样的元素，这些元素就是没有权限的菜单
        * 2.记录下没有权限的菜单的pid
        */
        // let notHasMenus = this.allMenuList.filter(e => {
        //   return hasMenus.every(r => {
        //     return e.id !== r.id
        //   })
        // }).map(e => e.id)
        // console.log('notHasMenus', notHasMenus)
        console.log('allMenuList', this.allMenuList)
        /*
         * 3.如果角色拥有的菜单中包含了没有权限的菜单则将其过滤掉
         */
        // this.form.menusList = result.data.filter(e => {
        //   return !notHasMenus.includes(e.id)
        // }).map(e => e.id)
        // console.log(this.form.menusList, '  this.form.menusList')
      })
    },
    getMenusList() {
      this.$http.get(this.url.menuList).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        if (result.data !== null) {
          this.menusOptions = result.data
          this.allMenuList = this.tree2list(this.menusOptions)
        }
      }).catch(function (error) {
        console.error('出现错误:', error)
      }).finally(() => {

      })
    },
    tree2list(list, data = []) {
      list.forEach(r => {
        data.push({ id: r.id, name: r.name, pid: r.pid, sort: r.sort })
        this.tree2list(r.children, data)
      })
      return data
    },
    closeForm() {
      this.form = {}
      this.$emit('cancel')
    },
    submit() {
      this.$refs.roleForm.validate(valid => {
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

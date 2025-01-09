<template>
  <a-drawer :title="title" width="50%" :visible="visible" @close="closeForm" :destroy-on-close="true">
    <a-form-model :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol"
                  ref="roleForm">
      <a-form-model-item label="ID" style="display: none">
        <a-input v-model="form.id" disabled placeholder="新建时自动生成"/>
      </a-form-model-item>
      <a-form-model-item label="名称" prop="name">
        <a-input v-model="form.name"/>
      </a-form-model-item>
      <a-form-model-item label="代码" prop="code">
        <a-input v-model="form.code"/>
      </a-form-model-item>
      <a-form-model-item label="菜单">
        <a-tree
            v-model="form.menuList"
            :checkable="true"
            :default-expand-all="true"
            :check-strictly="false"
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
import system from '@/mixins/system'

export default {
  name: 'add-role',
  mixins: [system],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
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
      console.log('watch', nowValue)
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
      rules: {
        name: [
          {required: true, message: '请输入名称', trigger: 'change'},
          {min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change'},
          {whitespace: true, message: '请输入名称', trigger: 'change'}
        ],
        code: [
          {required: true, message: '请输入角色代码', trigger: 'change'},
          {min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change'},
          {whitespace: true, message: '请输入代码', trigger: 'change'}
        ],
      },
      show: {
        treeLine: true,
      },
      menusOptions: [],
      url: {
        save: SysApis.saveRoles,
        menuList: SysApis.menuList,
        roleMenus: SysApis.roleMenus,
      },
      form: {
        menuList: []
      },
    }
  },
  activated() {
    this.getMenusList()
  },
  mounted() {
  },
  methods: {
    loadRoleMenus() {
      console.log("form", this.form)
      this.$http.get(`${this.url.roleMenus}/${this.form.id}`).then(result => {
        let hasMenusIds = []
        result.data.forEach(e => {
          hasMenusIds.push(e.id)
        })
        this.form.menuList = hasMenusIds
      })
    },
    getMenusList() {
      this.$http.get(this.url.menuList).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        if (result.data !== null) {
          // this.menusOptions = this.tree2list(result.data).sort((a, b) => {
          //   return b.sort - a.sort
          // })
          this.menusOptions = result.data.sort((a, b) => {
            return a.sort - b.sort
          })
        }
      }).catch(function (error) {
        console.error('出现错误:', error)
      }).finally(() => {

      })
    },
    tree2list(list, data = []) {
      list.forEach(r => {
        data.push({id: r.id, name: r.name, pid: r.pid, sort: r.sort})
        this.tree2list(r.children, data)
      })
      return data
    },
    closeForm() {
      this.form = {
        menuList: []
      }
      this.$emit('cancel')
    },
    submit() {
      this.$refs.roleForm.validate(valid => {
        if (valid) {
          this.$http.post(this.url.save, this.form).then(result => {
            if (result.status !== 200) {
              this.$message.error(result.message)
            } else {
              this.$message.success(result.message)
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

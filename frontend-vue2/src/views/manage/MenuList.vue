<template>
  <div>
    <a-button @click="addMenus()" v-auth:sys:menus:add>添加菜单</a-button>
    <a-divider/>
    <a-modal :visible="show.menus" width="50%" ok-text="确认" cancel-text="取消" @ok="submitMenusForm"
             @cancel="closeMenusForm">
      <a-form-model :model="form" :rules="formRules" :label-col="labelCol" :wrapper-col="wrapperCol" ref="menuForm">
        <a-form-model-item label="ID" style="display: none">
          <a-input v-model="form.id" disabled placeholder="新建时自动生成"/>
        </a-form-model-item>
        <a-form-model-item label="名称" prop="name">
          <a-input v-model="form.name"/>
        </a-form-model-item>
        <a-form-model-item label="图标" prop="icon" v-if="form.menusType === 1||form.menusType === 3">
          <a-input v-model="form.icon"/>
        </a-form-model-item>
        <a-form-model-item label="排序" prop="sort" v-if="form.menusType === 1">
          <a-input-number :min="0" :max="9999" v-model="form.sort"/>
        </a-form-model-item>
        <a-form-model-item label="类型" prop="menusType">
          <a-radio-group name="radioGroup" v-model="form.menusType">
            <a-radio :value="1">
              <a-tag size="small" color="green">菜单</a-tag>
            </a-radio>
            <a-radio :value="2">
              <a-tag size="small" color="blue">按钮</a-tag>
            </a-radio>
            <a-radio :value="3">
              <a-tag size="small" color="purple">超链接</a-tag>
            </a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="是否隐藏" prop="showFlag" v-if="form.menusType !== 2">
          <a-radio-group name="radioGroup" v-model="form.showFlag">
            <a-radio :value="0">显示</a-radio>
            <a-radio :value="1">隐藏</a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="浏览器访问路径" prop="path" v-if="form.menusType === 1">
          <a-input v-model="form.path"/>
        </a-form-model-item>
        <a-form-model-item label="文件路径" prop="filePath" v-if="form.menusType === 1">
          <a-input addon-before="@/views/" addon-after=".vue" v-model="form.filePath"/>
        </a-form-model-item>
        <a-form-model-item label="授权标识" prop="permission" v-if="form.menusType === 2">
          <a-input v-model="form.permission"/>
        </a-form-model-item>
        <a-form-model-item label="超链接地址" prop="link" v-if="form.menusType === 3">
          <a-input v-model="form.link"/>
        </a-form-model-item>
      </a-form-model>
      <div class="drawer-bottom-button">
        <a-button :style="{ marginRight: '8px' }" @click="closeMenusForm">关闭</a-button>
        <a-button type="primary" @click="submitMenusForm">保存</a-button>
      </div>
    </a-modal>
    <a-table :data-source="dataSource" key="id" :columns="columns" :pagination="pagination">
      <span slot="action" slot-scope="text, record">
        <c-pop-button title="确定要删除吗" text="删除" type="danger" @click="removeMenus(record)" v-auth:sys:menus:del/>
        <a-divider type="vertical"/>
        <a-button type="primary" size="small" @click="editMenus(record)" v-auth:sys:menus:edit>编辑</a-button>
        <span v-if="record.menusType === 1">
          <a-divider type="vertical"/>
          <a-button type="primary" size="small" @click="addChildMenus(record)"
                    v-auth:sys:menus:add-children>添加子菜单</a-button>
        </span>
      </span>
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
</template>

<script>

import commonApi from '@/api/CommonApi'
import { SysApis } from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'

export default {
  name: 'menus-list',
  mixins: [SystemMinix],
  data() {
    return {
      formRules: {
        menusType: [
          { required: true, message: '类型不能为空', trigger: 'change' },
        ],
        showFlag: [
          { required: true, message: '请选择是否隐藏', trigger: 'change' },
        ],
        filePath: [
          { required: true, message: '请输入文件路径', trigger: 'change' },
          { whitespace: true, message: '请输入文件路径', trigger: 'change' }
        ],
        path: [
          { required: true, message: '请输入浏览器访问路径', trigger: 'change' },
          { whitespace: true, message: '请输入浏览器访问路径', trigger: 'change' }
        ],
        link: [
          { required: true, message: '请输入超链接地址', trigger: 'change' },
          { whitespace: true, message: '请输入超链接地址', trigger: 'change' }
        ],
      },
      columns: [
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
        {
          dataIndex: 'permission',
          title: '授权标识',
        },
        {
          dataIndex: 'action',
          title: '操作',
          scopedSlots: { customRender: 'action' },
        },
      ],
      dataSource: [],
      show: {
        menus: false,
        test: false,
      },
      url: {
        list: SysApis.menuPage,
        save: SysApis.saveMenus,
        delete: SysApis.delMenus,
      }
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {
    refreshData() {
      this.resetForm()
      this.getList()
    },
    getList() {
      let page = this.getQueryPage()
      this.$http.get(this.url.list, { params: page }).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        if (result.data !== null) {
          let format = function (data) {
            return data.map(e => {
              let menus = {
                id: e.id,
                key: e.id,
                name: e.name,
                icon: e.icon,
                url: e.path,
                sort: e.sort,
                menusType: e.menusType,
                path: e.path,
                link: e.link,
                filePath: e.filePath,
                permission: e.permission,
                showFlag: e.showFlag,
              }
              if (e.children && e.children.length > 0) {
                menus.children = format(e.children)
              }
              return menus
            }).sort((a, b) => {
              return a.sort - b.sort
            })
          }
          this.dataSource = format(result.data.records)
          this.pagination.total = result.data.total
          this.pagination.current = result.data.current
          this.pagination.pageSize = result.data.size
        }
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
    },
    removeMenus(e) {
      this.$http.post(`${this.url.delete}/${e.id}`).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        this.refreshData()
        this.$message.success(result.message)
      }).catch(function (error) {
        console.log('请求出现错误:', error)
      })
    },
    addMenus() {
      this.show.menus = true
    },
    addChildMenus(e) {
      this.form.pid = e.id
      this.show.menus = true
    },
    editMenus(e) {
      this.form = e
      this.show.menus = true
    },
    closeMenusForm() {
      this.show.menus = false
      this.refreshData()
      commonApi.refreshSysData()
    },
    submitMenusForm() {
      this.$refs.menuForm.validate(valid => {
        if (valid) {
          this.$http.post(this.url.save, this.form).then(result => {
            if (result.status !== 200) {
              this.$message.error(result.message)
              return
            }
            this.closeMenusForm()
          }).catch(function (error) {
            console.error('出现错误:', error)
          })
        } else {
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

<template>
  <div>
    <a-button @click="showAdd" v-auth:sys:user:add>添加用户</a-button>
    <a-divider/>
    <add-user :visible="show.add" @cancel="closeForm" :is-edit="show.edit" ref="addUser"/>
    <change-password :visible="show.changePassword" @cancel="closeChangePasswordForm" :user-id="userId"
                     ref="changePassword"/>
    <a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="pagination">
      <template #status="value,record">
        <a-tag color="green" v-if="value === 0">正常</a-tag>
        <a-tag color="red" v-else>锁定</a-tag>
      </template>
      <template #action="value,record">
        <template v-if="record.username !== 'admin'">
          <c-pop-button title="确认要锁定吗" text="锁定" @click="lockUser(record)" type="primary"
                        v-if="record.status === 0" v-auth:sys:user:lock/>
          <c-pop-button title="确认要解锁吗" text="解锁" @click="unlockUser(record)"
                        v-if="record.status === 1" v-auth:sys:user:unlock/>
          <a-divider type="vertical"/>
          <c-pop-button title="确认要删除吗" text="删除" @click="remove(record)" type="danger" v-auth:sys:user:del/>
          <a-divider type="vertical"/>
          <a-button @click="showChangePassword(record)" size="small">修改密码</a-button>
          <a-button @click="showChangePassword(record)" v-auth:sys:user:change-password>修改密码</a-button>
        </template>
        <a-button type="primary" size="small" @click="showEdit(record)" v-auth:sys:user:edit>编辑</a-button>
      </template>
    </a-table>
  </div>
</template>

<script>

import { SysApis } from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'
import AddUser from '@/views/manage/sys-user/add.vue'
import ChangePassword from '@/views/manage/sys-user/change-password.vue'

export default {
  name: 'user-list',
  mixins: [SystemMinix],
  components: {
    AddUser, ChangePassword
  },
  data() {
    return {
      columns: [
        {
          dataIndex: 'username',
          key: 'username',
          title: '用户名',
        },
        {
          dataIndex: 'name',
          title: '姓名',
          key: 'name',
        },
        {
          dataIndex: 'age',
          title: '年龄',
          key: 'age',
        },
        {
          dataIndex: 'gender_dictValue',
          title: '性别',
          key: 'gender',
        },
        {
          dataIndex: 'mail',
          title: '邮箱',
          key: 'mail',
        },

        {
          dataIndex: 'phone',
          title: '电话',
          key: 'phone',
        },
        {
          dataIndex: 'status',
          title: '状态',
          key: 'status',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '操作',
          key: 'action',
          scopedSlots: { customRender: 'action' },
        },
      ],
      roleOptions: [],
      show: {
        add: false,
        detail: false,
        edit: false,
        changePassword: false,
      },
      url: {
        list: SysApis.userPage,
        lock: SysApis.lockUser,
        unlock: SysApis.unlockUser,
        delete: SysApis.delUser,
      },
      userId: ''
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
      this.$refs.addUser.form = e
      this.show.add = true
      this.show.edit = true
    },
    showChangePassword(e) {
      this.userId = e.id
      this.show.changePassword = true
    },
    closeChangePasswordForm(e) {
      this.show.changePassword = false
      this.refreshData()
    },
    lockUser(e) {
      this.$http.post(`${this.url.lock}/${e.id}`).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        this.$message.success(result.message)
      }).catch(function (error) {
        console.log('请求出现错误:', error)
      }).finally(() => {
        this.refreshData()
      })
    },
    unlockUser(e) {
      this.$http.post(`${this.url.unlock}/${e.id}`).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message)
          return
        }
        this.$message.success(result.message)
      }).catch(function (error) {
        console.log('请求出现错误:', error)
      }).finally(() => {
        this.refreshData()

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

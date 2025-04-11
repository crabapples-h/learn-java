<template>
  <div>
    <a-form layout="inline" @keyup.enter.native="getList">
      <a-space align="center" style="flex-wrap: wrap">
        <a-form-item label="用户名">
          <a-input placeholder="请输入用户名" v-model="queryParam.username" :allow-clear="true"/>
        </a-form-item>
        <a-form-item label="姓名">
          <a-input placeholder="请输入姓名" v-model="queryParam.name" :allow-clear="true"/>
        </a-form-item>
        <a-form-item label="手机号">
          <a-input placeholder="请输入手机号" v-model="queryParam.phone" :allow-clear="true"/>
        </a-form-item>
        <a-button type="default" @click="getList" icon="search">查询</a-button>
        <a-button type="default" @click="resetSearch" icon="reload">重置</a-button>
        <a-button type="primary" @click="showAdd" icon="plus" v-auth:sys:user:add ghost>添加</a-button>
      </a-space>
    </a-form>
    <a-divider/>
    <add-user :visible="show.add" @close="closeAdd" title="添加"/>
    <add-user :visible="show.edit" @close="closeEdit" :is-edit="true" title="编辑" ref="editForm"/>
    <change-password :visible="show.changePassword" @cancel="closeChangePasswordForm" :user-id="userId"
                     ref="changePassword"/>

    <img src="/api/file/download/LOCAL/1910781574052450304.jpg">
    <a-table :data-source="dataSource" bordered
             rowKey="id"
             :columns="columns"
             :pagination="pagination"
             :scroll="{ x: 1200}">
      <template #status="value,record">
        <a-tag color="green" v-if="value === 0">正常</a-tag>
        <a-tag color="red" v-else>锁定</a-tag>
      </template>
      <template #action="value,record">
        <a-space align="center" style="flex-wrap: wrap">
          <a-button type="primary" size="small" @click="showEdit(record)" v-auth:sys:user:edit>编辑</a-button>
          <template v-if="record.username !== 'admin'">
            <c-pop-button title="确认要锁定吗" text="锁定" @click="lockUser(record)" type="danger" :ghost="true"
                          v-if="record.status === 0" v-auth:sys:user:lock/>
            <c-pop-button title="确认要解锁吗" text="解锁" @click="unlockUser(record)"
                          v-if="record.status === 1" v-auth:sys:user:unlock/>
            <a-button @click="showChangePassword(record)" v-auth:sys:user:change-password size="small">
              重置密码
            </a-button>
            <c-pop-button title="确认要删除吗" text="删除" @click="remove(record)" type="danger" v-auth:sys:user:del/>
          </template>
        </a-space>
      </template>
    </a-table>
  </div>
</template>

<script>

import { SysApis } from '@/api/Apis'
import system from '@/mixins/system'
import AddUser from '@/views/manage/sys-user/add.vue'
import ChangePassword from '@/views/manage/sys-user/change-password.vue'

export default {
  name: 'user-list',
  mixins: [system],
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
          align: 'center',
          width: 80
        },
        {
          dataIndex: 'name',
          title: '姓名',
          key: 'name',
          align: 'center',
          width: 100
        },
        {
          dataIndex: 'age',
          title: '年龄',
          key: 'age',
          align: 'center',
          width: 80
        },
        {
          dataIndex: 'gender_dictText',
          title: '性别',
          key: 'gender',
          align: 'center',
          width: 100
        },
        {
          dataIndex: 'mail',
          title: '邮箱',
          key: 'mail',
          align: 'center',
          width: 200
        },
        {
          dataIndex: 'phone',
          title: '电话',
          key: 'phone',
          align: 'center',
          width: 200
        },
        {
          dataIndex: 'status',
          title: '状态',
          key: 'status',
          scopedSlots: {customRender: 'status'},
          align: 'center',
          width: 80
        },
        {
          title: '操作',
          key: 'action',
          scopedSlots: {customRender: 'action'},
          width: 250
        }
      ],
      roleOptions: [],
      show: {
        add: false,
        detail: false,
        edit: false,
        changePassword: false
      },
      url: {
        list: SysApis.userPage,
        lock: SysApis.lockUser,
        unlock: SysApis.unlockUser,
        remove: SysApis.delUser
      },
      userId: '',
      title: '添加',
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {
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
    }
  }
}
</script>

<style lang="less" scoped>
//@import "~@public/color.less";
.query-form {
  display: flex;
  flex-direction: row;
  justify-content: start;
  align-items: center
}

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

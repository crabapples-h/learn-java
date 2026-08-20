<template>
  <div>
    <a-form layout="inline" @keyup.enter="getList">
      <a-space align="center" style="flex-wrap: wrap">
        <a-form-item label="用户名">
          <a-input placeholder="请输入用户名" v-model:value="queryParam.username" allow-clear />
        </a-form-item>
        <a-form-item label="姓名">
          <a-input placeholder="请输入姓名" v-model:value="queryParam.name" allow-clear />
        </a-form-item>
        <a-form-item label="手机号">
          <a-input placeholder="请输入手机号" v-model:value="queryParam.phone" allow-clear />
        </a-form-item>
        <a-button type="default" @click="getList">查询</a-button>
        <a-button type="default" @click="resetSearch">重置</a-button>
        <a-button type="primary" ghost v-auth:sys:user:add @click="showAdd">添加</a-button>
      </a-space>
    </a-form>
    <a-divider />
    <add-user :open="show.add" @close="closeAdd" title="添加" />
    <add-user :open="show.edit" @close="closeEdit" :is-edit="true" title="编辑" :edit-data="editData" />
    <change-password :open="show.changePassword" @cancel="closeChangePasswordForm" :user-id="userId" />
    <a-table :data-source="dataSource" bordered row-key="id" :columns="columns" :pagination="pagination"
             :scroll="{ x: 1200 }">
      <template #bodyCell="{ column, record, text }">
        <template v-if="column.key === 'avatar'">
          <img :src="'/api/file/download/' + record.avatar" style="width: 40px;height: 40px;border-radius: 50%" />
        </template>
        <template v-else-if="column.key === 'status'">
          <a-tag color="green" v-if="text === 0">正常</a-tag>
          <a-tag color="red" v-else>锁定</a-tag>
        </template>
        <template v-else-if="column.key === 'action'">
          <a-space align="center" style="flex-wrap: wrap">
            <a-button type="primary" size="small" v-auth:sys:user:edit @click="showEdit(record)">编辑</a-button>
            <template v-if="record.username !== 'admin'">
              <c-pop-button v-if="record.status === 0" title="确认要锁定吗" text="锁定" type="danger" :ghost="true"
                            v-auth:sys:user:lock @click="lockUser(record)" />
              <c-pop-button v-if="record.status === 1" title="确认要解锁吗" text="解锁" @click="unlockUser(record)"
                            v-auth:sys:user:unlock />
              <a-button size="small" v-auth:sys:user:change-password @click="showChangePassword(record)">
                重置密码
              </a-button>
              <c-pop-button title="确认要删除吗" text="删除" type="danger" v-auth:sys:user:del @click="remove(record)" />
            </template>
          </a-space>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { message } from 'ant-design-vue'
import request from '@/utils/request'
import { SysApis } from '@/api/Apis'
import { useSystem } from '@/utils/useSystem'
import CPopButton from '@/components/c-pop-button.vue'
import AddUser from './add.vue'
import ChangePassword from './change-password.vue'

const columns = [
  { dataIndex: 'username', key: 'username', title: '用户名', align: 'center', width: 80 },
  { dataIndex: 'name', key: 'name', title: '姓名', align: 'center', width: 100 },
  { dataIndex: 'avatar', key: 'avatar', title: '头像', align: 'center', width: 80 },
  { dataIndex: 'age', key: 'age', title: '年龄', align: 'center', width: 80 },
  { dataIndex: 'gender_dictText', key: 'gender', title: '性别', align: 'center', width: 100 },
  { dataIndex: 'mail', key: 'mail', title: '邮箱', align: 'center', width: 200 },
  { dataIndex: 'phone', key: 'phone', title: '电话', align: 'center', width: 200 },
  { dataIndex: 'status', key: 'status', title: '状态', align: 'center', width: 80 },
  { title: '操作', key: 'action', width: 250 },
]

const {
  dataSource,
  queryParam,
  pagination,
  show,
  editData,
  getList,
  resetSearch,
  showAdd,
  closeAdd,
  showEdit,
  closeEdit,
  remove,
  refreshData,
} = useSystem({
  list: SysApis.userPage,
  lock: SysApis.lockUser,
  unlock: SysApis.unlockUser,
  remove: SysApis.delUser,
})

show.changePassword = false
const userId = ref('')

const showChangePassword = e => {
  userId.value = e.id
  show.changePassword = true
}

const closeChangePasswordForm = () => {
  show.changePassword = false
  refreshData()
}

const lockUser = async e => {
  try {
    const result = await request.post(`${SysApis.lockUser}/${e.id}`)
    if (result.status !== 200) {
      message.error(result.message)
      return
    }
    message.success(result.message)
  } catch (error) {
    console.log('请求出现错误:', error)
  } finally {
    refreshData()
  }
}

const unlockUser = async e => {
  try {
    const result = await request.post(`${SysApis.unlockUser}/${e.id}`)
    if (result.status !== 200) {
      message.error(result.message)
      return
    }
    message.success(result.message)
  } catch (error) {
    console.log('请求出现错误:', error)
  } finally {
    refreshData()
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

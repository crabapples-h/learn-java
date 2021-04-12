<template>
  <div>
    <a-button @click="addSub">发起申报</a-button>
    <a-divider/>
    <a-modal :visible="show.addSub" @cancel="closeAddSub" @ok="submitSubject" cancelText="取消" okText="保存">
      <a-form-model ref="ruleForm" :model="form.addSub" :rules="rules.addSub" :label-col="labelCol"
                    :wrapper-col="wrapperCol">
        <a-form-model-item ref="password" label="课题名称" prop="title">
          <a-input v-model="form.addSub.title" placeholder="请输入课题名称"/>
        </a-form-model-item>
        <a-form-model-item ref="personList" label="科研人员" prop="personList">
          <a-select mode="multiple" :default-value="[]" v-model="form.addSub.personList" placeholder="请选择参与的科研人员">
            <a-select-option v-for="i in 25" :key="(i + 9).toString(36) + i">
              {{ (i + 9).toString(36) + i }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item ref="stepList" label="阶段目标" prop="stepList">
          <a-form-model-item v-for="(item, index) in form.addSub.stepList" :key="index">
            <a-input v-model="form.addSub.stepList[index].content" placeholder="请输入当前阶段目标"/>
          </a-form-model-item>
          <a-button type="dashed" style="width: 45%" @click="addStep">
            <a-icon type="plus-circle" style="color: #109bf1"/>
            增加目标
          </a-button>
          <a-divider type="vertical"/>
          <a-button type="dashed" style="width: 45%" @click="removeStep">
            <a-icon type="minus-circle" style="color: red"/>
            减少目标
          </a-button>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
    <a-modal :visible="show.resultList" @cancel="closeAddSub" cancelText="关闭">
      <a-table :columns="columns" :data-source="dataSource">
        <span slot="customTitle"> 课题名称</span>
        <span slot="name" slot-scope="text">{{ text }}</span>
        <span slot="tags" slot-scope="tags">
          <a-tag v-for="tag in tags" :key="tag" color="red">{{ tag }}</a-tag>
        </span>
        <span slot="beginTime" slot-scope="text">{{ text }}</span>
        <span slot="endTime" slot-scope="text">{{ text }}</span>
      </a-table>
    </a-modal>
    <a-table :columns="columns" :data-source="dataSource">
      <span slot="customTitle"> 课题名称</span>
      <span slot="name" slot-scope="text">{{ text }}</span>
      <span slot="tags" slot-scope="tags">
      <a-tag v-for="tag in tags" :key="tag" color="red">{{ tag }}</a-tag>
    </span>
      <span slot="status" slot-scope="status">
      <a-tag v-if="status === 0" color="green">{{ status }}</a-tag>
      <a-tag v-if="status === 1" color="geekblue">{{ status }}</a-tag>
      <a-tag v-if="status === 2" color="pink">{{ status }}</a-tag>
    </span>
      <span slot="beginTime" slot-scope="text">{{ text }}</span>
      <span slot="endTime" slot-scope="text">{{ text }}</span>
      <span slot="action" slot-scope="text, record">
      <a-button type="danger" size="small" @click="endSub()">申请结题</a-button>
      <a-divider type="vertical"/>
      <a-button type="primary" size="small">测试</a-button>
      <a-divider type="vertical"/>
         <a-dropdown>
              <a-button type="default" size="small">成果
              <a class="ant-dropdown-link"><a-icon type="down" :style="{color:'#000'}"/></a>
              </a-button>
            <a-menu slot="overlay">
              <a-menu-item key="1">
               <a-upload name="file" :multiple="true" action="/api/uploadFile" :headers="headers">
                  <a-button type="default" size="small">添加</a-button>
                </a-upload>
              </a-menu-item>
              <a-menu-item key="2" @click="showResultList(record)">
               <a-button type="default" size="small">查看</a-button>
              </a-menu-item>
            </a-menu>
            <a-button style="margin-left: 8px"> Button
              <a-icon type="down"/>
            </a-button>
          </a-dropdown>
    </span>
    </a-table>
  </div>
</template>

<script>
export default {
  name: "sub-apply",
  data() {
    return {
      rules: {
        addSub: {
          title: [
            {required: true, message: '请输入课题名称', trigger: 'change'},
            {min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change'},
          ],
          personList: [
            // {required: true, message: '请选择参与科研人员', trigger: 'change'},
          ],
          stepList: [
            {required: true, message: '阶段目标至少有一个', trigger: 'change'},
          ],
        },
      },
      headers: {
        authorization: 'authorization-text',
      },
      columns: [
        {
          dataIndex: 'name',
          key: 'name',
          slots: {title: 'customTitle'},
          scopedSlots: {customRender: 'name'},
        },
        {
          title: '状态',
          dataIndex: 'status',
          key: 'status',
          scopedSlots: {customRender: 'status'},
        },
        {
          title: '标签',
          key: 'tags',
          dataIndex: 'tags',
          scopedSlots: {customRender: 'tags'},
        },
        {
          title: '开始时间',
          key: 'beginTime',
          dataIndex: 'beginTime',
          scopedSlots: {customRender: 'beginTime'},
        },
        {
          title: '结束时间',
          key: 'endTime',
          dataIndex: 'endTime',
          scopedSlots: {customRender: 'endTime'},
        },
        {
          title: '操作',
          key: 'action',
          scopedSlots: {customRender: 'action'},
        },
      ],
      dataSource: [
        {
          id: '1',
          key: '1',
          name: '课题1',
          beginTime: '2020-11-12',
          endTime: '2020-11-12',
          status: 0,
          tags: ['nice', 'developer'],
        },
        {
          id: '2',
          key: '2',
          name: '课题2',
          beginTime: '2020-11-12',
          endTime: '2020-11-12',
          status: 1,
          tags: ['loser'],
        },
        {
          id: '3',
          key: '3',
          name: '课题3',
          beginTime: '2020-11-12',
          endTime: '2020-11-12',
          status: 2,
          tags: ['cool', 'teacher'],
        },
      ],

      labelCol: {span: 5},
      wrapperCol: {span: 16},
      openKeys: [],
      form: {
        addSub: {
          title: '',
          personList: [],
          stepList: [
            {
              indexNum: 0,
              content: '',
            }
          ]
        },
      },
      show: {
        addSub: false,
        resultList: false,
      },
      userInfo: {
        name: ''
      },
    };
  },
  mounted() {
    // let token = sessionStorage.getItem('crabapples-token')
    this.getList()
    // this.$router.push({name: 'welcome'})
  },
  methods: {
    getList() {
      this.$http.get('/api/subject/list').then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        console.log(result)
        if (result.data !== null) {
          // this.userInfo = result.data;
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
      // this.resetSubForm()
    },
    resetSubForm() {
      this.form.addSub = {
        title: '',
        personList: [],
        stepList: [
          {
            index: 0,
            content: '',
          }
        ]
      }
    },
    addSub() {
      this.show.addSub = true
    },
    closeAddSub() {
      this.show.addSub = false
      this.resetSubForm()
    },
    submitSubject() {
      console.log(this.form.addSub)
      this.$http.post('/api/subject/save', this.form.addSub).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        console.log(result)
        if (result.data !== null) {
          // this.userInfo = result.data;
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
      // this.resetSubForm()
    },
    removeStep() {
      if (this.form.addSub.stepList.length > 1)
        this.form.addSub.stepList.pop()
    },
    addStep() {
      let step = {indexNum: this.form.addSub.stepList.length, content: ''}
      this.form.addSub.stepList.push(step)
    },

    endSub() {
      this.$confirm({
        title: '确定要结束课题吗?',
        content: '确定要结束课题吗?结束后无法恢复！',
        onOk() {
          return new Promise((resolve, reject) => {
            setTimeout(Math.random() > 0.5 ? resolve : reject, 1000);
          }).catch(() => console.log('Oops errors!'));
        },
        onCancel() {
        },
      });
    },

    addResult(info) {
      if (info.file.status !== 'uploading') {
        console.log(info.file, info.fileList);
      }
      if (info.file.status === 'done') {
        this.$message.success(`${info.file.name} 上传成功`);
      } else if (info.file.status === 'error') {
        this.$message.error(`${info.file.name} 上传失败`);
      }
    },
    showResultList(e) {
      console.log(e.id)
      this.show.resultList = true
    },
    titleClick(e) {
      console.log('titleClick', e);
    },
    closeModal() {
      // this.show.changePassword = false
      // this.$refs.ruleForm.resetFields();
    },

    clickMenu(url) {
      this.$router.push({path: url})
      console.log(url)
    },
    getUserInfo() {
      this.$http.get('/api/getUserInfo').then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        if (result.data !== null) {
          this.userInfo = result.data;
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
    },
  }
}
</script>

<style scoped>

.ant-layout-header, .ant-layout-footer {
  background: #7dbcea;
  color: #fff;
}

.ant-layout-footer {
  line-height: 1.5;
}

.ant-layout-sider {
  width: 100%;
  height: 823px;
  /*background: #ffffff;*/
  /*color: #fff;*/
  /*line-height: 120px;*/
}

.ant-layout-content {
  /*padding: 16px;*/
  background: #fff;
  color: #fff;
  min-height: 120px;
  line-height: 120px;
  height: 823px;
}

.container-parent {
  padding: 16px;
}
</style>
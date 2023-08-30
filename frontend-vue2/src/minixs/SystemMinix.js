import { initCPagination } from '@/views/common/C-Pagination'
import { TreeSelect } from 'ant-design-vue'

export default {
  data() {
    return {
      SHOW_TYPE: TreeSelect.SHOW_CHILD,
      labelCol: { span: 5 },
      wrapperCol: { span: 16 },
      pagination: initCPagination(this.changeIndex, this.changeSize),
      dataSource: [],
    }
  },
  created() {
  },
  activated() {
    this.getList(this.pagination)
  },
  mounted() {
  },
  methods: {
    changeIndex() {
      this.getList(this.pagination)
    },
    changeSize() {
      this.getList(this.pagination)
    },
  }
}

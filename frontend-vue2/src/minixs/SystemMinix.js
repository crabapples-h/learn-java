import { initCPagination } from '@/views/common/C-Pagination'
import { TreeSelect } from 'ant-design-vue'
import { SysApis } from '@/api/Apis'

export default {
  data() {
    return {
      SHOW_TYPE: TreeSelect.SHOW_CHILD,
      labelCol: { span: 5 },
      wrapperCol: { span: 16 },
      pagination: initCPagination(this.changeIndex, this.changeSize),
      dataSource: [],
      url: {
        list: ''
      }
    }
  },
  created() {
  },
  activated() {
    this.getList()
  },
  mounted() {
  },
  methods: {
    listToTree(list, pidKey, pid = null) {
      const tree = []
      for (const item of list) {
        if (item.pid === pid) {
          const children = this.listToTree(list, item.id)
          if (children.length) {
            item.children = children
          }
          tree.push(item)
        }
      }
      return tree
    },
    getQueryPage() {
      return {
        pageIndex: this.pagination.current,
        pageSize: this.pagination.pageSize
      }
    },
    changeIndex(page, pageSize) {
      this.getList()
    },
    changeSize(pageIndex, pageSize) {
      this.pagination.pageSize = pageSize
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
          this.dataSource = result.data.content || result.data
          // if (result.data.pageable) {
          //   this.pagination.total = result.data.totalElements
          //   this.pagination.current = result.data.pageable.pageNumber + 1
          // }
        }
      }).catch(function (error) {
        console.error('出现错误:', error)
      })
    },

  }
}

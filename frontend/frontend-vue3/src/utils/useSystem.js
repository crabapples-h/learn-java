/**
 * useSystem —— 替代 vue2 中 `mixins/system.js` 的组合式函数
 * 提供列表页/表单页通用的分页、查询、提交、删除等逻辑
 */
import { reactive, ref, onMounted } from 'vue'
import request from '@/utils/request'
import { message } from 'ant-design-vue'

export function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}

export function useSystem(url = {}) {
  const form = ref({})
  const editData = ref({})
  const dataSource = ref([])
  const queryParam = ref({})
  const spinning = ref(false)
  const scrollY = ref(500)
  const formRef = ref(null)
  const show = reactive({
    add: false,
    edit: false,
    detail: false,
    extend: false,
    addItem: false,
    editItem: false,
  })
  const labelCol = { span: 5 }
  const wrapperCol = { span: 16 }

  const headers = {
    'crabapples-token': localStorage.getItem('TOKEN') || '',
  }

  const pagination = reactive({
    pageSize: 10,
    pageSizeOptions: ['10', '20', '30', '40'],
    total: 0,
    current: 1,
    size: 'middle',
    showSizeChanger: true,
    buildOptionText: ({ value }) => `${value}条/页`,
  })

  function getQueryPage() {
    return {
      pageIndex: pagination.current,
      pageSize: pagination.pageSize,
    }
  }

  function changeIndex(pageIndex, pageSize) {
    pagination.current = pageIndex
    pagination.pageSize = pageSize
    getList()
  }

  function changeSize(pageIndex, pageSize) {
    pagination.current = pageIndex
    pagination.pageSize = pageSize
    getList()
  }

  // 允许页面覆盖 getList（分页/刷新等内部调用会走覆盖后的实现）
  let listHandler = null
  function setListHandler(fn) {
    listHandler = fn
  }

  pagination.onChange = changeIndex
  pagination.onShowSizeChange = changeSize

  function getTableScroll(extraHeight = 74) {
    const tHeader = document.getElementsByClassName('ant-table-thead')[0]
    let tHeaderBottom = 0
    if (tHeader) {
      tHeaderBottom = tHeader.getBoundingClientRect().bottom
    }
    const windowHeight = document.documentElement.clientHeight
    return windowHeight - tHeaderBottom - extraHeight
  }

  async function getList() {
    if (listHandler) {
      await listHandler()
      return
    }
    if (!url.list) {
      console.log('组件[url.list]为空')
      return
    }
    spinning.value = true
    try {
      const params = Object.assign({}, queryParam.value, getQueryPage())
      const result = await request.get(url.list, { params })
      if (result.status !== 200) {
        message.error(result.message)
        return
      }
      if (result.data !== null) {
        dataSource.value = result.data.records || result.data
        pagination.total = result.data.total || result.data.totalRow
        pagination.current = result.data.current || result.data.pageNumber
        pagination.pageSize = result.data.size || result.data.pageSize
      }
    } catch (error) {
      console.error('出现错误:', error)
    } finally {
      spinning.value = false
    }
  }

  async function submit(formEl = formRef.value, onClose) {
    if (!formEl) return
    let valid = false
    try {
      await formEl.validate()
      valid = true
    } catch (e) {
      console.log('表单校验失败:', e)
    }
    if (!valid) return
    try {
      const result = await request.post(url.save, form.value)
      if (result.status !== 200) {
        message.error(result.message)
        return
      }
      message.success(result.message)
      setTimeout(() => {
        if (typeof onClose === 'function') {
          onClose()
        } else {
          closeForm()
        }
      }, 500)
    } catch (error) {
      console.error('出现错误:', error)
    }
  }

  async function remove(e) {
    try {
      const result = await request.delete(`${url.remove}/${e.id}`)
      if (result.status !== 200) {
        message.error(result.message)
        return
      }
      message.success(result.message)
    } catch (error) {
      console.error('出现错误:', error)
    } finally {
      refreshData()
    }
  }

  function showExtend() {
    show.extend = true
  }

  function closeExtend() {
    show.extend = false
  }

  function showAdd() {
    show.add = true
  }

  function closeAdd() {
    show.add = false
    refreshData()
  }

  function showEdit(e) {
    editData.value = Object.assign({}, e)
    show.edit = true
  }

  function closeEdit() {
    show.edit = false
    refreshData()
  }

  function showDetail() {
    show.detail = true
  }

  function closeDetail() {
    show.detail = false
    refreshData()
  }

  function resetSearch() {
    queryParam.value = {}
    refreshData()
  }

  function refreshData() {
    resetForm()
    getList()
  }

  function closeForm() {
    resetForm()
    show.add = false
    show.edit = false
  }

  function resetForm() {
    form.value = {}
  }

  function listToTree(list, pid = null) {
    const tree = []
    for (const item of list) {
      if (item.pid === pid) {
        const children = listToTree(list, item.id)
        if (children.length) {
          item.children = children
        }
        tree.push(item)
      }
    }
    return tree
  }

  function iconHandler(text) {
    if (text) {
      return text.substring(text.indexOf('"') + 1, text.lastIndexOf('"'))
    }
    return 'appstore'
  }

  function downloadFile(filename, data, type) {
    const blob = new Blob([data], { type })
    const link = document.createElement('a')
    link.href = URL.createObjectURL(blob)
    link.download = filename
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    URL.revokeObjectURL(link.href)
  }

  function download(url, fileName = '') {
    const el = document.createElement('a')
    el.style.display = 'none'
    el.setAttribute('target', '_blank')
    el.setAttribute('download', fileName ? fileName : url)
    el.href = url
    document.body.appendChild(el)
    el.click()
    document.body.removeChild(el)
  }

  function removeChineseChar(text) {
    return text.replace(/[一-龥]/g, '')
  }

  // 列表页进入时自动加载数据（表单页无 url.list 则跳过）
  onMounted(() => {
    if (url.list) {
      getList()
    }
  })

  return {
    form,
    editData,
    formRef,
    dataSource,
    queryParam,
    spinning,
    scrollY,
    show,
    labelCol,
    wrapperCol,
    headers,
    pagination,
    getQueryPage,
    changeIndex,
    changeSize,
    setListHandler,
    getTableScroll,
    getList,
    submit,
    remove,
    showExtend,
    closeExtend,
    showAdd,
    closeAdd,
    showEdit,
    closeEdit,
    showDetail,
    closeDetail,
    resetSearch,
    refreshData,
    closeForm,
    resetForm,
    listToTree,
    iconHandler,
    downloadFile,
    download,
    removeChineseChar,
  }
}

export default useSystem

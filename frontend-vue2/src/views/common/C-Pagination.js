const CPagination = {
  pageSize: 10,
  pageSizeOptions: ['10', '20', '30', '40'],
  total: 10,
  size: 'middle',
  showSizeChanger: true,
  buildOptionText: (({ value }) => {
    return `${value}条/页`
  }),
  onChange: null,
  onShowSizeChange: null,
}

export function initCPagination(changeIndex, changeSize) {
  let CPagination = {
    pageSize: 10,
    pageSizeOptions: ['10', '20', '30', '40'],
    total: 10,
    current: 1,
    size: 'middle',
    showSizeChanger: true,
    buildOptionText: (({ value }) => {
      return `${value}条/页`
    }),
    onChange: null,
    onShowSizeChange: null,
  }
  CPagination.onChange = changeIndex
  CPagination.onShowSizeChange = changeSize
  return CPagination
}


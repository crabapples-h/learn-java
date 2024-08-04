export function initCPagination(changeIndex, changeSize) {
  return {
    pageSize: 10,
    pageSizeOptions: ['10', '20', '30', '40'],
    total: 10,
    current: 1,
    size: 'middle',
    showSizeChanger: true,
    buildOptionText: (({ value }) => {
      return `${value}条/页`
    }),
    onChange: changeIndex,
    onShowSizeChange: changeSize,
  }
}


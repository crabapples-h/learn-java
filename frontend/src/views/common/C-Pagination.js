let CPagination = {
    pageSize: 10,
    pageSizeOptions: ['10', '20', '30', '40'],
    total: 20,
    size: 'middle',
    showSizeChanger: true,
    buildOptionText: (({value}) => {
        return `${value}条/页`
    }),
    onChange: null,
    onShowSizeChange: null,
}

export function initCPagination(changeIndex, changeSize) {
    CPagination.onChange = changeIndex
    CPagination.onShowSizeChange = changeSize
    return CPagination
}


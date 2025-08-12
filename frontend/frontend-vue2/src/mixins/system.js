import { initCPagination } from '@/views/common/CPagination'
import { TreeSelect } from 'ant-design-vue'
import CButton from '@comp/c-button.vue'
import CPopButton from '@comp/c-pop-button.vue'
import formRules from '@/utils/formRules'
import locale from 'ant-design-vue/es/date-picker/locale/zh_CN'

export function getBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
}

export default {
    components: {
        CButton,
        CPopButton,
    },
    data() {
        return {
            locale,
            form: {},
            scrollY: 500,
            dataSource: [],
            SHOW_TYPE: TreeSelect.SHOW_CHILD,
            labelCol: {span: 5},
            wrapperCol: {span: 16},
            pagination: initCPagination(this.changeIndex, this.changeSize),
            headers: {
                'crabapples-token': this.$store.getters.TOKEN,
            },
            sysUser: sessionStorage.getItem("sysUser"),
            uploadUrl: '/api/uploadFile',
            uploadUrlV2: '/api/file/uploadV2',
            uploadUrlV3: '/api/file/uploadV3',
            show: {
                add: false,
                edit: false,
                detail: false,
                extend: false,
            },
            spinning: false,
            queryParam: {},

            formRules: formRules,
        }
    },
    watch: {
        dataSource() {
            this.scrollY = this.getTableScroll();
        }
    },
    created() {
    },
    activated() {
        this.initData()
    },
    mounted() {
    },
    methods: {
        iconHandler(text) {
            if (text) {
                return text.substring(text.indexOf("\"") + 1, text.lastIndexOf("\""))
            }
            return "appstore"
        },
        download(url, fileName = '') {
            const el = document.createElement('a');
            el.style.display = 'none';
            el.setAttribute('target', '_blank');
            /**
             * download的属性是HTML5新增的属性
             * href属性的地址必须是非跨域的地址，如果引用的是第三方的网站或者说是前后端分离的项目(调用后台的接口)，这时download就会不起作用。
             * 此时，如果是下载浏览器无法解析的文件，例如.exe,.xlsx..那么浏览器会自动下载，但是如果使用浏览器可以解析的文件，比如.txt,.png,.pdf....浏览器就会采取预览模式
             * 所以，对于.txt,.png,.pdf等的预览功能我们就可以直接不设置download属性(前提是后端响应头的Content-Type: application/octet-stream，如果为application/pdf浏览器则会判断文件为 pdf ，自动执行预览的策略)
             */
            el.setAttribute('download', fileName ? fileName : url);
            el.href = url;
            console.log(el);
            document.body.appendChild(el);
            el.click();
            document.body.removeChild(el);
        },
        removeChineseChar(text) {
            return text.replace(/[\u4E00-\u9FA5]/g, "");
        },
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
        changeIndex(pageIndex, pageSize) {
            this.pagination.current = pageIndex
            this.pagination.pageSize = pageSize
            this.getList()
        },
        changeSize(pageIndex, pageSize) {
            this.pagination.current = pageIndex
            this.pagination.pageSize = pageSize
            this.getList()
        },
        initData() {
            this.getList()
        },
        getList() {
            if (!!this.url.list) {
                let config = {
                    params: Object.assign(this.queryParam, this.getQueryPage())
                }
                this.$http.get(this.url.list, config).then(result => {
                    if (result.status !== 200) {
                        this.$message.error(result.message)
                        return
                    }
                    if (result.data !== null) {
                        this.dataSource = result.data.records || result.data
                        this.pagination.total = result.data.total || result.data.totalRow
                        this.pagination.current = result.data.current || result.data.pageNumber
                        this.pagination.pageSize = result.data.size || result.data.pageSize
                    }
                }).catch(function (error) {
                    console.error('出现错误:', error)
                })
            } else {
                console.log('组件[%s]的[url.list]为空', this.$route.path)
            }
        },
        submit() {
            const _this = this
            console.log(_this.form)
            this.$refs.ruleForm.validate(valid => {
                if (valid) {
                    _this.$http.post(this.url.save, _this.form).then(result => {
                        console.log(result)
                        if (result.status !== 200) {
                            _this.$message.error(result.message);
                            return;
                        }
                        _this.$message.success(result.message);
                        setTimeout(() => {
                            _this.closeForm()
                        }, 500)
                    }).catch(function (error) {
                        console.error('出现错误:', error);
                    }).finally(() => {
                    });
                }
            })
        },
        remove(e) {
            this.$http.delete(`${this.url.remove}/${e.id}`).then(result => {
                if (result.status !== 200) {
                    this.$message.error(result.message);
                    return;
                }
                this.$message.success(result.message);
            }).catch(function (error) {
                console.error('出现错误:', error);
            }).finally(() => {
                this.refreshData()
            });
        },

        showExtend() {
            this.show.extend = true
        },
        closeExtend() {
            this.show.extend = false
            this.$emit('closeExtend')
        },
        onExpand(expanded, row) {
            if (!expanded) {
                row.children = []
                return
            }
            let config = {params: {pid: row.id}}
            this.$http.get(this.url.childList, config).then(result => {
                if (result.status !== 200) {
                    this.$message.error(result.message)
                    return
                }
                if (result.data !== null) {
                    row.children = (result.data.records || result.data)
                }
            }).catch(function (error) {
                console.error('出现错误:', error)
            })
        },

        showAdd() {
            this.show.add = true
        },
        closeAdd() {
            this.show.add = false
            this.refreshData()
        },

        showEdit(e) {
            this.$refs.editForm.form = e
            this.show.edit = true
        },
        closeEdit() {
            this.show.edit = false
            this.refreshData()
        },
        showDetail(e) {
            console.log('showDetail()->', e)
            this.$refs.detailForm.datasource = e
            this.show.detail = true
        },
        closeDetail() {
            this.show.detail = false
            this.refreshData()
        },
        resetSearch() {
            this.queryParam = {}
            this.refreshData()
        },
        refreshData() {
            this.resetForm()
            this.getList()
        },
        closeForm() {
            this.resetForm()
            this.show.add = false
            this.show.edit = false
            this.$emit('close')
        },
        resetForm() {
            this.form = {}
        },
        getTableScroll(extraHeight = 74, id) {
            let tHeader = null
            if (id) {
                tHeader = document.getElementById(id) ? document.getElementById(id).getElementsByClassName("ant-table-thead")[0] : null
            } else {
                tHeader = document.getElementsByClassName("ant-table-thead")[0]
            }
            //表格内容距离顶部的距离
            let tHeaderBottom = 0
            if (tHeader) {
                tHeaderBottom = tHeader.getBoundingClientRect().bottom
            }
            const windowHeight = document.documentElement.clientHeight
            //窗体高度-表格内容顶部的高度-表格内容底部的高度
            return windowHeight - tHeaderBottom - extraHeight
        },
    }
}

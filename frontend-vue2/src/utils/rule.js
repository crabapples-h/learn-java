const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'change' },
    { min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change' },
    { whitespace: true, message: '请输入用户名', trigger: 'change' }
  ],
  name: [
    { required: true, message: '请输入名称', trigger: 'change' },
    { min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change' },
    { whitespace: true, message: '请输入名称', trigger: 'change' }
  ],
  age: [
    { required: true, message: '请输入年龄', trigger: 'change' },
  ],
  mail: [
    { required: true, message: '请输入邮箱', trigger: 'change' },
    { whitespace: true, message: '请输入邮箱', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入电话', trigger: 'change' },
    { whitespace: true, message: '请输入电话', trigger: 'change' },
    { min: 8, max: 16, message: '长度为8-16个字符', trigger: 'change' },
  ],
  role: [
    { required: true, message: '请选择角色', trigger: 'change' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'change' },
    { min: 8, max: 16, message: '长度为8-16个字符', trigger: 'change' },
    { whitespace: true, message: '请输入密码', trigger: 'change' }
  ],
  newPassword: [
    { required: true, message: '请输入密码', trigger: 'change' },
    { min: 8, max: 16, message: '长度为8-16个字符', trigger: 'change' },
    { whitespace: true, message: '请输入密码', trigger: 'change' }
  ],
  oldPassword: [
    { required: true, message: '请输入密码', trigger: 'change' },
    { min: 8, max: 16, message: '长度为8-16个字符', trigger: 'change' },
    { whitespace: true, message: '请输入密码', trigger: 'change' }
  ],
  againPassword: [
    { required: true, message: '请重复输入密码', trigger: 'change' },
    { min: 8, max: 16, message: '长度为8-16个字符', trigger: 'change' },
    { whitespace: true, message: '请重复输入密码', trigger: 'change' }
  ],
  menusType: [
    { required: true, message: '类型不能为空', trigger: 'change' },
  ],
}
export default rules

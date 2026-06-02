const readJson = key => {
  const value = localStorage.getItem(key)
  return value ? JSON.parse(value) : null
}

export default {
  getToken: () => localStorage.getItem('TOKEN'),
  setToken: token => localStorage.setItem('TOKEN', token),

  getUserBaseInfo: () => readJson('USER_BASE_INFO'),
  setUserBaseInfo: data => localStorage.setItem('USER_BASE_INFO', JSON.stringify(data)),

  getUserRoles: () => readJson('USER_ROLE_LIST'),
  setUserRoles: data => localStorage.setItem('USER_ROLE_LIST', JSON.stringify(data)),

  getUserMenusList: () => readJson('USER_MENU_LIST'),
  setUserMenusList: data => localStorage.setItem('USER_MENU_LIST', JSON.stringify(data)),

  getUserMenusTree: () => readJson('USER_MENU_TREE'),
  setUserMenusTree: data => localStorage.setItem('USER_MENU_TREE', JSON.stringify(data)),

  getPermissions: () => readJson('USER_PERMISSION_LIST'),
  setPermissions: data => localStorage.setItem('USER_PERMISSION_LIST', JSON.stringify(data)),

  logout: () => localStorage.removeItem('TOKEN'),

  getServerAddress: () => localStorage.getItem('SERVER_ADDRESS'),
  setServerAddress: data => localStorage.setItem('SERVER_ADDRESS', data),

  getFilePreviewAddress: () => localStorage.getItem('FILE_PREVIEW_ADDRESS'),
  setFilePreviewAddress: data => localStorage.setItem('FILE_PREVIEW_ADDRESS', data),
}

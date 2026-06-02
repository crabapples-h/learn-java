export function hasPermission(permissions = [], code) {
  if (!code) {
    return true
  }
  return permissions.includes(code)
}

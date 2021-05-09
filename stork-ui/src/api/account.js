import request from '@/utils/request'

const roles = {
  guest: '未激活用户',
  admin: '超级管理员',
  official: '领导',
  maintain: '维护人员',
  urgent: '应急人员',
  enforce: '监察人员',
  site: '网站管理员'
}

export function listAccounts(param) {
  return request({
    url: '/stork/api/account',
    method: 'get',
    params: param
  })
}

export function listAccountsByRole(role) {
  return request({
    url: '/stork/api/account?by=role',
    method: 'get',
    params: { role: role }
  })
}

export function addAccount(data) {
  return request({
    url: '/stork/api/account',
    method: 'post',
    data
  })
}

export function listRoles() {
  return roles
}

export function setRoles(accountId, roles) {
  return request({
    url: '/stork/api/account/' + accountId + '?op=set-role&roles=' + roles,
    method: 'post'
  })
}

/**
 * 代码转中文名
 */
export function code2Name(code) {
  return roles[code]
}

/**
 * 中文名转代码
 */
export function name2Code(name) {
  for (const i in roles) {
    if (roles[i] === name) {
      return i
    }
  }
  return undefined
}


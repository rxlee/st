import { get, post } from '@/api/_request.js'

export function login (info) {
  return post('/account/login', info)//, { baseURL: './' }
}

export function getUserInfo (token) {
  return get('account/info?token=' + token, null)//, { baseURL: './' }
}

export function getAllUsers () {
  return get('account')
}

export function roleName (role) {
  switch (role) {
    case 'sample-delivery': return '取样送样人员'
    case 'sample-test': return '样品检测人员'
    case 'river-chief': return '河长'
    case 'region-administra': return '镇长'
    case 'region-patrol': return '巡查员'
    case 'region-officer': return '镇办业务员'
    case 'manager': return '平台管理员'
    default: return '未知'
  }
}

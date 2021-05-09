import request from '@/utils/request'

export function step2Name(step) {
  switch (step) {
    case 0:
      return '立案申请'
    case 10:
      return '执法中'
    case 20:
      return '结案申请'
    case 30:
      return '已结案'
    default:
      return '错误'
  }
}

export function list(param) {
  return request({
    url: '/stork/api/enforce',
    method: 'get',
    params: param
  })
}
export function recent(limit) {
  return request({
    url: '/stork/api/enforce?about=recent',
    method: 'get',
    params: { limit: limit }
  })
}

export function add(data) {
  return request({
    url: '/stork/api/enforce',
    method: 'post',
    data
  })
}
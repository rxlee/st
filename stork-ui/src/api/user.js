import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/stork/api/account/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/stork/api/account/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/stork/api/account/logout',
    method: 'post'
  })
}

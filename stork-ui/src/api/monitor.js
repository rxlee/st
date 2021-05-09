import request from '@/utils/request'

export function test() {
  return request({
    url: '/stork/api/monitor',
    method: 'get'
  })
}

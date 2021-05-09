import request from '@/utils/request'

/**
 * 根据检测人员查询任务
 * @param {*} TestId
 */
export function listTaskByTest(TestId) {
  return request({
    url: '/stork/api/detect-process/' + TestId + '?by=test',
    method: 'get'
  })
}

export function chargeComplete(TestId, data) {
  return request({
    url: '/stork/api/detect-process/' + TestId + '?by=chargeComplete',
    method: 'post',
    data
  })
}

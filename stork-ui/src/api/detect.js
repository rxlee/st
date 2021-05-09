import request from '@/utils/request'

export function listUndetectedSampleRecorsInRegion(regionId) {
  return request({
    url: '/stork/api/sample-record/' + regionId + '?by=region&about=undetected',
    method: 'get'
  })
}
export function addCorp(data) {
  return request({
    url: '/stork/api/detect/corp',
    method: 'post',
    data
  })
}

/**
 * 给指定检测公司添加一名下属员工
 * @param {*} id
 * @param {*} data
 */
export function addStaff(corpId, data) {
  return request({
    url: '/stork/api/detect/corp/' + corpId + '/staff',
    method: 'post',
    data
  })
}
/**
 * 模拟采样
 * @param {*} newone
 */
export function addSimulatedSampleRecord(newone) {
  return request({
    url: '/stork/api/sample-record?mode=test&region=' + newone.region + '&station=' + newone.station,
    method: 'post'
  })
}
export function addContract(data) {
  return request({
    url: '/stork/api/detect/contract',
    method: 'post',
    data
  })
}

export function deleteStaff(corpId, staffid) {
  return request({
    url: '/stork/api/detect/corp/' + corpId + '/staff/' + staffid + '?op=remove',
    method: 'post'
  })
}

export function listAllCorps() {
  return request({
    url: '/stork/api/detect/corp',
    method: 'get'
  })
}
export function listStaff(id) {
  return request({
    url: '/stork/api/detect/corp/' + id + '/staff',
    method: 'get'
  })
}
export function listContract(id) {
  return request({
    url: '/stork/api/detect/contract/' + id + '?by=region',
    method: 'get'
  })
}

export function listDeliveriesInCorp(corpId) {
  return request({
    url: '/stork/api/detect/corp/' + corpId + '/staff?type=delivery',
    method: 'get'
  })
}
/**
 * 列出某辖区内可提供送检服务的人员
 * @param {*} corpId
 */
export function listDeliveriesInRegion(regionId) {
  return request({
    url: '/stork/api/detect/region/' + regionId + '/delivery',
    method: 'get'
  })
}

/**
 * 派发采样任务
 * @param {*} record
 * @param {*} delivery
 * @param {*} dispatcher
 */
export function dispatchTask(data) {
  return request({
    url: '/stork/api/detect-process',
    method: 'post',
    data
  })
}

export function listAllTask() {
  return request({
    url: '/stork/api/detect-process',
    method: 'get'
  })
}

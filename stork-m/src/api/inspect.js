import { get, post } from '@/api/_request.js'
/**
 * 添加巡查情况
 * @param {*} data 
 */
export function addInspect(data) {
  return post('/inspect', data)
}
/**
 * 根据辖区id查询巡查情况
 * @param {*} regionId 
 */
export function getInspectByRgionId(regionId) {
  return get('/inspect/' + regionId + '?about=outline')
}
/**
 * 审批巡查
 * @param {*} id 
 * @param {*} data 
 */
export function approveInspect(id, data) {
  return post('/inspect/' + id + '?op=approve', data)
}
/**
 * 
 * @param {*} id 
 * @param {*} data 
 */
export function inspectResult(id, data) {
  return post('/inspect/' + id + '?op=result', data)
}

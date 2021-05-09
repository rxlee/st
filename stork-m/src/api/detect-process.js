/* eslint-disable */
import { get, post } from '@/api/_request.js'
import { param } from 'jquery'

/**
 * 根据送样人员查询任务
 * @param {*} info 
 */
export function listTaskByDelivery(deliveryId) {
  return get('/detect-process/' + deliveryId + '?by=delivery')//, { baseURL: './' }
}

/**
 * 根据检测人员查询任务
 * @param {*} info 
 */
export function listTaskByTest(TestId) {
  return get('/detect-process/' + TestId + '?by=test')//, { baseURL: './' }
}

/**
 * 根据任务id取样成功
 * @param {*} info 
 */
export function sampleSuccess(Id) {
  return post('/detect-process/' + Id + '?by=sample')//, { baseURL: './' }
}

/**
 * 根据送样人员取样成功
 * @param {*} info 
 */
export function deliverySuccess(Id, data) {
  return post('/detect-process/' + Id + '?by=delivery', data)//, { baseURL: './' }
}


export function receive(Id) {
  return post('/detect-process/' + Id + '?by=accept')//, { baseURL: './' }
}

export function chargeComplete(Id) {
  return post('/detect-process/' + Id + '?by=chargeComplete')//, { baseURL: './' }
}

/**
 * 根据人员id查询所在辖区
 * @param {*} staffId 
 */
export function queryRegionByStaff(staffId) {
  return get('/region/' + staffId + '?by=staff')
}
/**
 * 通过送样人id查询检验人id
 * @param {*} staffId 
 */
export function queryTestByStaffId(staffId) {
  return get('/detect/corp/' + staffId + '/staff' + '?type=test')
}
/**
 * 根据辖区id查询待派发任务
 * @param {*} regionId 
 */
export function listUndetectedSampleRecorsInRegion(regionId) {
  return get('/sample-record/' + regionId + '?by=region&about=undetected')
}
/**
 * 列出某辖区内可提供送检服务的人员
 * @param {*} corpId
 */
export function listDeliveriesInRegion(regionId) {
  return get('/detect/region/' + regionId + '/delivery')
}

/**
 * 派发采样任务
 * @param {*} record 
 * @param {*} delivery 
 * @param {*} dispatcher 
 */
export function dispatchTask(data) {
  return post('/detect-process', data)
}

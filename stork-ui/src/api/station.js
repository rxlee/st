import request from '@/utils/request'

export function listStation() {
  return request({
    url: '/stork/api/station',
    method: 'get'
  })
}
export function listStationByRegionId(regionId) {
  return request({
    url: '/stork/api/station/' + regionId + '?by=region',
    method: 'get'
  })
}

export function addStation(data) {
  return request({
    url: '/stork/api/station',
    method: 'post',
    data
  })
}
export function queryStationById(id) {
  return request({
    url: '/stork/api/station/' + id,
    method: 'get'
  })
}

export function removeStation(id) {
  return request({
    url: '/stork/api/station/' + id + '?op=remove',
    method: 'post'
  })
}

export function maintainStation(id, start) {
  return request({
    url: '/stork/api/station/' + id + '?op=maintain&start=' + start,
    method: 'post'
  })
}

export function modifyStationProfile(id, data) {
  return request({
    url: '/stork/api/station/' + id + '?op=modify&about=profile',
    method: 'post',
    data
  })
}
/**
 *修改辖区
 * @param {*} id
 * @param {*} data
 */
export function updateRegion(id, data) {
  return request({
    url: '/stork/api/station/' + id + '?op=modify&about=region',
    method: 'post',
    data
  })
}

export function modifyStationLocateBatch(data) {
  return request({
    url: '/stork/api/station?op=modify&about=locate',
    method: 'post',
    data
  })
}

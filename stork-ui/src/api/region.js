import request from '@/utils/request'
export function listRegion() {
  return request({
    url: '/stork/api/region',
    method: 'get'
  })
}
export function findRegionById(regionId) {
  return request({
    url: '/stork/api/region/' + regionId,
    method: 'get'
  })
}
export function listRegionByName(name) {
  return request({
    url: '/stork/api/region/' + name + '?by=name',
    method: 'get'
  })
}
export function addRegion(data) {
  return request({
    url: '/stork/api/region',
    method: 'post',
    data
  })
}
export function listStaffInRegion(regionId) {
  return request({
    url: '/stork/api/region/' + regionId + '/staff',
    method: 'get'
  })
}
export function addStaffInRegion(regionId, data) {
  return request({
    url: '/stork/api/region/' + regionId + '/staff',
    method: 'post',
    data
  })
}
export function removeStaffInRegion(regionId, staffId) {
  return request({
    url: '/stork/api/region/' + regionId + '/staff/' + staffId + '?op=remove',
    method: 'post'
  })
}

export function queryRegionByStaff(staffId) {
  return request({
    url: '/stork/api/region/' + staffId + '?by=staff',
    method: 'get'
  })
}

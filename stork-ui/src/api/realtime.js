import request from '@/utils/request'

// 所有水站的最新数据
export function allNewest() {
  return request({
    url: '/stork/api/realtime/-1?about=newest&by=station',
    method: 'get'
  })
}

/**
 * 所有水站最近的综合评价
 */
export function allNewestEvas() {
  return request({
    url: '/stork/api/realtime?about=eva',
    method: 'get'
  })
}

/**
 * 查询组合的实时数据历史
 */
export function queryRealtimeHistory(stationId, from, to, index, size) {
  let url = '/stork/api/realtime?a=a'
  if (stationId != null) url += '&station=' + stationId
  if (from) url += '&from=' + from.getTime()
  if (to) url += '&to=' + to.getTime()
  if (index != null) url += '&pageIndex=' + index
  if (size != null) url += '&pageSize=' + size
  return request({
    url: url,
    method: 'get'
  })
}

/**
 * 查询实时数据历史
 */
export function queryRealtimes(stationId, from, to, device) {
  let url = '/stork/api/realtime/' + stationId + '?about=real&device=' + device
  if (from) url += '&from=' + from.getTime()
  if (to) url += '&to=' + to.getTime()
  return request({
    url: url,
    method: 'get'
  })
}
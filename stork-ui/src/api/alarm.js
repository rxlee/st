import request from '@/utils/request'

/**
 * 列出所有阈值配置
 */
export function listThresholds() {
  return request({
    url: '/stork/api/threshold',
    method: 'get'
  })
}

/**
 * 修改阈值
 * @param id threshold表id
 * @param data 阈值数据，包含top和bottom
 */
export function modifyThreshold(id, data) {
  return request({
    url: '/stork/api/threshold/' + id + '?op=modify',
    method: 'post',
    data
  })
}

/**
 * 查询报警历史
 */
export function queryAlarmHistory(stationId, from, to, index, size) {
  let url = '/stork/api/alarm?a=a'
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

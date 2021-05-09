/* eslint-disable */
import axios from 'axios'
import { Message } from "element-ui";

axios.defaults.baseURL = 'http://localhost:88/stork/api'
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'

axios.interceptors.response.use(resp => {
  let data = resp.data
  if (data.code === 20000) {
    return Promise.resolve({
      data: data.data,
      msg: data.message,
      code: data.code,
    })
  }
  Message.error(data.message || '未知错误')
  return Promise.reject(
    data.message || '网络错误'
  )
}, err => {
  let isCancel = axios.isCancel(err)
  if (isCancel) {
    return new Promise(() => { })
  }
  return Promise.reject(
    err.resp.data && err.resp.data.msg || '网络错误'
  )
})

export function post(url, data, otherConfig) {
  return axios.post(url, data, otherConfig)
}

export function get(url, data, otherConfig) {
  return axios.get(url, { params: data, ...otherConfig })
}

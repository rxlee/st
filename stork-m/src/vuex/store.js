import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
  uid: null,
  uname: null,
  uphone: null,
  urole: null
}

const mutations = {
  setUser (state, u) {
    state.uid = u.user.uid
    window.localStorage.setItem('uid', state.uid)
    state.uname = u.user.name
    window.localStorage.setItem('uname', state.uname)
    state.uphone = u.user.telephone
    window.localStorage.setItem('uphone', state.uphone)
    state.urole = u.user.roles[0]
    window.localStorage.setItem('urole', state.urole)
  }
}
const getters = {
  uid: state => {
    const key = 'uid'
    state[key] = window.localStorage.getItem(key)
    return state[key]
  },
  uname: state => {
    const key = 'uname'
    state[key] = window.localStorage.getItem(key)
    return state[key]
  },
  uphone: state => {
    const key = 'uphone'
    state[key] = window.localStorage.getItem(key)
    return state[key]
  },
  urole: state => {
    const key = 'urole'
    state[key] = window.localStorage.getItem(key)
    return state[key]
  }
}

export default new Vuex.Store({
  state,
  mutations,
  getters
})

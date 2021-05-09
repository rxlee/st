// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from '@/vuex/store'

import axios from 'axios' // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
import VueAxios from 'vue-axios' // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

import ElementUI from 'element-ui' // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
import 'element-ui/lib/theme-chalk/index.css' // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

import 'bootstrap/dist/css/bootstrap.css' // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
import 'bootstrap/dist/js/bootstrap.min.js' // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

import '@/style/global.css'

Vue.config.productionTip = false

Vue.use(VueAxios, axios) // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
Vue.use(ElementUI) // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})

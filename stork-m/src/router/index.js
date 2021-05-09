/* eslint-disable */
import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Page from '@/components/Page'
import Admin from '@/components/Admin'
import Delivery from '@/components/sample/Delivery'

Vue.use(Router)

export default new Router({
  routes: [
    { path: '/', name: 'Login', component: Login },
    { path: '/page', name: 'Page', component: Page, children: [
      { path: 'admin', name: 'Admin', component: Admin },
      { path: 'sample/delivery', name: 'Delivery', component: Delivery }
    ] }
  ]
})

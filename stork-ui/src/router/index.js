import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/* Router Modules */
import componentsRouter from './modules/components'
import chartsRouter from './modules/charts'
import tableRouter from './modules/table'
import nestedRouter from './modules/nested'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: { title: '????????????', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/profile',
    component: Layout,
    redirect: '/profile/index',
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: { title: '????????????', icon: 'user', noCache: true }
      }
    ]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  // ????????????
  {
    path: '/monitor',
    component: Layout,
    redirect: '/monitor/realtime',
    meta: { title: '????????????', icon: 'el-icon-upload', roles: ['maintain', 'urgent', 'official'] },
    children: [
      {
        path: 'realtime',
        component: () => import('@/views/a1monitor/realtime'),
        name: 'MonitorRealtime',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'urgent', 'official'] }
      },
      {
        path: 'history',
        component: () => import('@/views/a1monitor/history'),
        name: 'MonitorHistory',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'urgent', 'official'] }
      },
      {
        path: 'trend',
        component: () => import('@/views/a1monitor/trend'),
        name: 'MonitorTrend',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'urgent', 'official'] }
      },
      {
        path: 'alarm',
        component: () => import('@/views/a1monitor/alarm'),
        name: 'MonitorAlarm',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'urgent', 'official'] }
      }
    ]
  }, // END: ????????????
  // ????????????
  {
    path: '/station',
    component: Layout,
    redirect: '/station/station-profile',
    meta: { title: '????????????', icon: 'el-icon-s-shop', roles: ['maintain', 'official'] },
    children: [
      {
        path: 'station-profile',
        component: () => import('@/views/a4station/profile'),
        name: 'StationProfile',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'official'] }
      },
      {
        path: 'locate',
        component: () => import('@/views/a4station/locate'),
        name: 'StationLocate',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'official'] }
      },
      {
        path: 'threshold',
        component: () => import('@/views/a4station/threshold'),
        name: 'StationThreshold',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'official'] }
      }
    ]
  }, // END: ????????????
  // ????????????
  {
    path: '/detect',
    component: Layout,
    redirect: '/detect/task-list',
    meta: { title: '????????????', icon: 'el-icon-edit-outline', roles: ['maintain', 'official'] },
    children: [
      {
        path: 'task-list',
        component: () => import('@/views/a6detect/task-list'),
        name: 'DetectTaskList',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'official'] }
      },
      {
        path: 'task-dispatch',
        component: () => import('@/views/a6detect/task-dispatch'),
        name: 'DetectTaskDispatch',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['official'] }
      },
      {
        path: 'process',
        component: () => import('@/views/a6detect/process'),
        name: 'DetectProcess',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'official'] }
      },
      {
        path: 'corp',
        component: () => import('@/views/a6detect/corp'),
        name: 'DetectCorp',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'official'] }
      },
      {
        path: 'corp-staff',
        component: () => import('@/views/a6detect/staff'),
        name: 'DetectCorpStaff',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'official'] }
      },
      {
        path: 'contract',
        component: () => import('@/views/a6detect/contract'),
        name: 'DetectContract',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'official'] }
      }
    ]
  }, // END: ????????????
  // ????????????
  {
    path: '/region',
    component: Layout,
    redirect: '/region/query',
    meta: { title: '????????????', icon: 'el-icon-place', roles: ['maintain', 'official'] },
    children: [
      {
        path: 'region-profile',
        component: () => import('@/views/a7region/profile'),
        name: 'RegionProfile',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'official'] }
      },
      {
        path: 'region-locate',
        component: () => import('@/views/a7region/locate'),
        name: 'RegionLocate',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'official'] }
      },
      {
        path: 'region-station',
        component: () => import('@/views/a7region/station'),
        name: 'RegionStation',
        meta: { title: '??????????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'official'] }
      },
      {
        path: 'region-staff',
        component: () => import('@/views/a7region/staff'),
        name: 'RegionStaff',
        meta: { title: '??????????????????', icon: 'documentation', affix: true, noCache: true, roles: ['maintain', 'official'] }
      }
    ]
  }, // END: ????????????
  // ????????????
  {
    path: '/system',
    component: Layout,
    redirect: '/system/users',
    meta: { title: '????????????', icon: 'el-icon-s-tools', roles: ['official'] },
    children: [
      {
        path: 'users',
        component: () => import('@/views/a5system/users'),
        name: 'SystemUsers',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['official'] }
      },
      {
        path: 'permission',
        component: () => import('@/views/a5system/permission'),
        name: 'SystemPermission',
        meta: { title: '????????????', icon: 'documentation', affix: true, noCache: true, roles: ['official'] }
      }
    ]
  }, // END: ????????????
  {
    hidden: true,
    path: '/site',
    component: Layout,
    redirect: '/site/list',
    name: 'Site',
    alwaysShow: true, // will always show the root menu
    meta: {
      title: '????????????',
      icon: 'el-icon-s-help', roles: ['site', 'official']
    },
    children: [
      {
        path: 'create',
        component: () => import('@/views/a11site/create'),
        name: 'CreateArticle',
        meta: { title: '????????????', icon: 'edit', roles: ['site', 'official'] }
      },
      {
        path: 'edit/:id(\\d+)',
        component: () => import('@/views/a11site/edit'),
        name: 'EditArticle',
        meta: { title: '????????????', noCache: true, activeMenu: '/a11site/list', roles: ['site', 'official'] },
        hidden: true
      },
      {
        path: 'list',
        component: () => import('@/views/a11site/list'),
        name: 'ArticleList',
        meta: { title: '????????????', icon: 'list', noCache: true, roles: ['site', 'official'] }
      }
    ]
  },
  {
    hidden: true,
    path: '/permission',
    component: Layout,
    redirect: '/permission/page',
    alwaysShow: true, // will always show the root menu
    name: 'Permission',
    meta: {
      title: 'Permission',
      icon: 'lock',
      roles: ['admin', 'editor'] // you can set roles in root nav
    },
    children: [
      {
        path: 'page',
        component: () => import('@/views/permission/page'),
        name: 'PagePermission',
        meta: {
          title: 'Page Permission',
          roles: ['admin'] // or you can only set roles in sub nav
        }
      },
      {
        path: 'directive',
        component: () => import('@/views/permission/directive'),
        name: 'DirectivePermission',
        meta: {
          title: 'Directive Permission'
          // if do not set roles, means: this page does not require permission
        }
      },
      {
        path: 'role',
        component: () => import('@/views/permission/role'),
        name: 'RolePermission',
        meta: {
          title: 'Role Permission',
          roles: ['admin']
        }
      }
    ]
  },

  {
    hidden: true,
    path: '/icon',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/icons/index'),
        name: 'Icons',
        meta: { title: 'Icons', icon: 'icon', noCache: true }
      }
    ]
  },

  /** when your routing map is too long, you can split it into small modules **/
  // componentsRouter,
  // chartsRouter,
  // tableRouter,

  {
    hidden: true,
    path: '/example',
    component: Layout,
    redirect: '/example/list',
    name: 'Example',
    meta: {
      title: 'Example',
      icon: 'el-icon-s-help'
    },
    children: [
      {
        path: 'create',
        component: () => import('@/views/example/create'),
        name: 'CreateArticle',
        meta: { title: 'Create Article', icon: 'edit' }
      },
      {
        path: 'edit/:id(\\d+)',
        component: () => import('@/views/example/edit'),
        name: 'EditArticle',
        meta: { title: 'Edit Article', noCache: true, activeMenu: '/example/list' },
        hidden: true
      },
      {
        path: 'list',
        component: () => import('@/views/example/list'),
        name: 'ArticleList',
        meta: { title: 'Article List', icon: 'list' }
      }
    ]
  },

  {
    hidden: true,
    path: '/tab',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/tab/index'),
        name: 'Tab',
        meta: { title: 'Tab', icon: 'tab' }
      }
    ]
  },

  {
    hidden: true,
    path: '/error',
    component: Layout,
    redirect: 'noRedirect',
    name: 'ErrorPages',
    meta: {
      title: 'Error Pages',
      icon: '404'
    },
    children: [
      {
        path: '401',
        component: () => import('@/views/error-page/401'),
        name: 'Page401',
        meta: { title: '401', noCache: true }
      },
      {
        path: '404',
        component: () => import('@/views/error-page/404'),
        name: 'Page404',
        meta: { title: '404', noCache: true }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router

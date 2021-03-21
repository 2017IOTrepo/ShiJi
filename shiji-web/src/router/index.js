import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

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
    icon: 'svg-name'             the icon show in the sidebar
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
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/index_page',
    children: [
      {
        path: 'index_page',
        component: () => import('@/views/index_page/index.vue'),
        meta: {
          title: '主页',
          icon: 'dashboard'
        }
      }
    ]
  },
  {
    path: '/news',
    component: Layout,
    children: [
      {
        path: 'news',
        component: () => import('@/views/index_page/pages/HistoryNews.vue'),
        meta: {
          title: '学术新闻',
          icon: 'nested'
        }
      },
      {
        path: 'content',
        component: () => import('@/views/index_page/pages/NewsContent.vue'),
        hidden: true
      }
    ]
  },
  {
    path: '/books',
    component: Layout,
    children: [
      {
        path: 'http://119.3.166.63:8081',
        meta: {
          title: '古籍阅读',
          icon: 'excel'
        }
      }
    ]
  },
  {
    path: '/single_world_search',
    component: Layout,
    children: [
      {
        path: 'single_world_search',
        component: () => import('@/views/single_word_search/index.vue'),
        meta: {
          title: '单文本查询',
          icon: 'list'
        }
      },
      {
        path: 'search_content',
        component: () => import('@/views/single_word_search/SingleWordContent.vue'),
        hidden: true
      }
    ]
  },
  {
    path: '/404',
    component: () => import('@/views/404.vue'),
    hidden: true
  },
  {
    path: '/knowledage_graph',
    component: Layout,
    children: [
      {
        path: 'knowledage_graph',
        component: () => import('@/views/knowledge_graph/index.vue'),
        meta: {
          title: '知识图谱',
          icon: 'guide'
        }
      }
    ]
  },
  {
    path: '/data_search',
    component: Layout,
    redirect: 'data_search/data_search',
    // alwaysShow: true,
    meta: {
      title: '数据量化检索',
      icon: 'search'
    },
    children: [
      {
        path: 'data_query',
        component: () => import('@/views/data_search/pages/DataQuery.vue'),
        meta: {
          title: '数据查询'
        }
      },
      {
        path: 'time_query',
        component: () => import('@/views/data_search/pages/TimeQuery.vue'),
        meta: {
          title: '基于时间查询'
        }
      },
      {
        path: 'word_vector',
        component: () => import('@/views/data_search/pages/WordVector.vue'),
        meta: {
          title: '词向量查询'
        }
      },
      {
        path: 'loaction_query',
        component: () => import('@/views/data_search/pages/LocationQuery.vue'),
        meta: {
          title: '基于地理位置查询'
        }
      }
    ]
  },
  {
    path: '*',
    redirect: '/404',
    hidden: true
  }
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

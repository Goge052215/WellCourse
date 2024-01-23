import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    meta: {
      title: 'home',
      deepth: 0
    },
    component: () => import('../views/home.vue'),
  },
  {
    path: '/login',
    name: 'login',
    meta: {
      title: 'login',
      role: 'all'
    },
    component: () => import('../views/login.vue'),
  },
  {
    path: '/register',
    name: 'register',
    meta: {
      title: 'register',
      role: 'all'
    },
    component: () => import('../views/register.vue'),
  },
  {
    path: '/retrieve',
    name: 'retrieve',
    meta: {
      title: 'retrieve',
      role: 'all'
    },
    component: () => import('../views/retrieve.vue'),
  },
  {
    path: '/notice',
    name: 'notice',
    meta: {
      title: 'notice',
      deepth: 0
    },
    component: () => import('../views/notice.vue'),
  },
  {
    path: '/setting',
    name: 'setting',
    meta: {
      title: 'setting',
      deepth: 0
    },
    component: () => import('../views/setting.vue'),
  },
  {
    path: '/channel',
    name: 'channel',
    meta: {
      title: 'channel',
      keepAlive: true,
      deepth: 1
    },
    component: () => import('../views/channel.vue'),
  },
  {
    path: '/release',
    name: 'release',
    meta: {
      keepAlive: false,
      title: 'release',
      deepth: 0
    },
    component: () => import('../views/release.vue'),
  },
  {
    path: '/articleList',
    name: 'articleList',
    meta: {
      title: 'articleList',
      keepAlive: true,
      deepth: 2
    },
    component: () => import('../views/articleList.vue'),
  },
  {
    path: '/detail',
    name: 'detail',
    meta: {
      keepAlive: false,
      title: 'detail',
      deepth: 3
    },
    component: () => import('../views/detail.vue'),
  },
  {
    path: '/user/:id',
    name: 'user',
    meta: {
      title: 'user',
      deepth: 0
    },
    component: () => import('../views/user.vue'),
  },
  {
    path: '/userList',
    name: 'userList',
    meta: {
      title: 'userList',
      deepth: 0
    },
    component: () => import('../views/userList.vue'),
  },
  {
    path: '/forbiddenWords',
    name: 'forbiddenWords',
    meta: {
      title: 'forbiddenWords',
      deepth: 0
    },
    component: () => import('../views/forbiddenWords.vue'),
  },
  {
    path: '/article',
    name: 'article',
    meta: {
      title: 'article',
      deepth: 0
    },
    component: () => import('../views/article.vue'),
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/about.vue')
  }
]

const router = new VueRouter({
  routes
})

router.beforeEach(async (to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.role == 'all') {
    next()
  } else {
    if (token) {
      if(!store.state.info.useremail) {
        await store.dispatch('getInfo')
        next()
      } else {
        next()
      }
    } else {
      next({path: '/login'})
    }
  }
})

export default router

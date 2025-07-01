import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    name: 'Login',
    path: '/login',
    meta: {
      title: '用户登录页面',
      requiresAuth: false
    },
    component: () => import(/* webpackChunkName: "login" */ '@/views/Login.vue'),
    children: [
      {
        path: '',
        name: 'LoginForm',
        component: () => import(/* webpackChunkName: "login-form" */ '@/components/LoginForm.vue')
      }
    ]
  },
  {
    name: 'Register',
    path: '/register',
    meta: {
      title: '用户注册页面',
      requiresAuth: false
    },
    component: () => import(/* webpackChunkName: "register" */ '@/views/Register.vue'),
    children: [
      {
        path: '',
        name: 'RegisterForm',
        component: () => import(/* webpackChunkName: "register-form" */ '@/components/RegisterForm.vue')
      }
    ]
  },
  {
    name: 'Profile',
    path: '/profile',
    meta: {
      title: '用户信息页面',
      requiresAuth: true
    },
    component: () => import(/* webpackChunkName: "profile" */ '@/views/Profile.vue'),
    children: [
      {
        path: 'info',
        name: 'ProfileForm',
        component: () => import(/* webpackChunkName: "profile-form" */ '@/components/ProfileForm.vue')
      },
      {
        path: 'password',
        name: 'PasswordForm',
        component: () => import(/* webpackChunkName: "password-form" */ '@/components/PasswordForm.vue')
      }
    ]
  },
  {
    path: '*',
    name: 'NotFound',
    component: () => import(/* webpackChunkName: "not-found" */ '@/views/NotFound.vue'),
    meta: {
      title: '404 - 页面不存在'
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  document.title = to.meta.title || 'Requirements Business Integration Chain Optimization'

  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!store.getters.isAuthenticated) {
      next({
        name: 'Login',
        query: { redirect: to.fullPath }
      })
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
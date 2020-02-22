import Vue from 'vue'
import VueRouter from 'vue-router'

const Login = () => import(/* webpackChunkName: "login_home_welcome" */ '../components/Login.vue')
const Home = () => import(/* webpackChunkName: "login_home_welcome" */ '../components/Home.vue')
const Welcome = () => import(/* webpackChunkName: "login_home_welcome" */ '../components/Welcome.vue')
const User = () => import(/* webpackChunkName: "user_rights_roles" */ '../components/Users/User.vue')
const Rights = () => import(/* webpackChunkName: "user_rights_roles" */ '../components/Power/Rights.vue')
const Roles = () => import(/* webpackChunkName: "user_rights_roles" */ '../components/Power/Roles.vue')
const Params = () => import(/* webpackChunkName: "params_goods_add_reports" */ '../components/Goods/Params.vue')
const Goods = () => import(/* webpackChunkName: "params_goods_add_reports" */ '../components/Goods/Goods.vue')
const Add = () => import(/* webpackChunkName: "params_goods_add_reports" */ '../components/Goods/Add.vue')
const Reports = () => import(/* webpackChunkName: "params_goods_add_reports" */ '../components/Report/Reports.vue')
/* import Login from '../components/Login.vue'
import Home from '../components/Home.vue'
import Welcome from '../components/Welcome.vue'
import User from '../components/Users/User.vue'
import Rights from '../components/Power/Rights.vue'
import Roles from '../components/Power/Roles.vue'
import Params from '../components/Goods/Params.vue'
import Goods from '../components/Goods/Goods.vue'
import Add from '../components/Goods/Add.vue'
import Reports from '../components/Report/Reports.vue' */

Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  { path: '/home',
    component: Home,
    redirect: '/welcome',
    children: [
      { path: '/welcome', component: Welcome },
      { path: '/users', component: User },
      { path: '/rights', component: Rights },
      { path: '/roles', component: Roles },
      { path: '/params', component: Params },
      { path: '/goods', component: Goods },
      { path: '/goods/add', component: Add },
      { path: '/reports', component: Reports }
    ] }
]

const router = new VueRouter({
  routes
})

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  if (to.path === '/login') return next()
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) return next('/login')
  next()
})

export default router

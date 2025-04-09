import Vue from 'vue'
import VueRouter from 'vue-router'
import CustomerLoginPage from '@/views/customer/CustomerLoginPage.vue'
import CustomerHome from '@/views/customer/CustomerHome.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/customer/login'
  },
  {
    path: '/customer/login',
    name: 'CustomerLoginPage',
    component: CustomerLoginPage
  },
  {
    path: '/customer/home',
    name: 'CustomerHome',
    component: CustomerHome
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

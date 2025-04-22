import Vue from 'vue'
import VueRouter from 'vue-router'
import CustomerLoginPage from '@/views/customer/CustomerLoginPage.vue'
import CustomerHome from '@/views/customer/CustomerHome.vue'
import PlatformLoginPage from '@/views/platform-management/PlatformLoginPage.vue'
import PlatformHomePage from '@/views/platform-management/PlatformHomePage.vue'
import PlatformMovieInsertPage from '@/views/platform-management/child/movie-management/PlatformMovieInsertPage.vue'
import PlatformMainPage from '@/views/platform-management/child/PlatformMainPage.vue'
import CustomerHomePage from '@/views/customer/child/CustomerHomePage.vue'
import PlatformMovieListPage from '@/views/platform-management/child/movie-management/PlatformMovieListPage.vue'
import PlatformMovieUpdatePage from '@/views/platform-management/child/movie-management/PlatformMovieUpdatePage.vue'
import CustomerMoviePage from '@/views/customer/child/CustomerMoviePage.vue'
import CustomerMovieListPage from '@/views/customer/child/moviePageChild/CustomerMovieListPage.vue'
import CustomerMovieDetailPage from '@/views/customer/child/moviePageChild/CustomerMovieDetailPage.vue'
import PlatformMovieTypePage from '@/views/platform-management/child/movie-management/PlatformMovieTypePage.vue'
import PlatformUserListPage from '@/views/platform-management/child/user-management/PlatformUserListPage.vue'
import PlatformUserDetailPage from '@/views/platform-management/child/user-management/PlatformUserDetailPage.vue'
import PlatformMobileDisplayPage from '@/views/platform-management/child/other-management/PlatformMobileDisplayPage.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '',
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
    component: CustomerHome,
    children:[
      {
        path: '',
        name: 'CustomerHomePage',
        component: CustomerHomePage,
      },
      {
        path:'movie',
        name: 'CustomerMoviePage',
        component: CustomerMoviePage,
        children:[
          {
            path: '',
            name: 'CustomerMovieListPage',
            component: CustomerMovieListPage,
          },
          {
            path:'detail/:movieId',
            name: 'CustomerMovieDetailPage',
            component: CustomerMovieDetailPage,
          }
        ]
      }
    ]
  },
  {
    path: '/all/admin',
    redirect: '/platform/login'
  },
  {
    path: '/platform/login',
    name: 'PlatformLoginPage',
    component: PlatformLoginPage
  },
  {
    path: '/platform/home',
    name: 'PlatformHomePage',
    component: PlatformHomePage,
    children:[
      {
        path: '',
        name: 'PlatformMainPage',
        component: PlatformMainPage,
      },
      {
        path: 'insert-movie',
        name: 'PlatformMovieInsertPage',
        component: PlatformMovieInsertPage,
      },
      {
        path: 'list-movie',
        name: 'PlatformMovieListPage',
        component: PlatformMovieListPage,
      },
      {
        path: 'update-movie/:movieId?',
        name: 'PlatformMovieUpdatePage',
        component: PlatformMovieUpdatePage,
      },
      {
        path: 'type-movie',
        name: 'PlatformMovieTypePage',
        component: PlatformMovieTypePage,
      },
      {
        path: 'list-user',
        name: 'PlatformUserListPage',
        component: PlatformUserListPage,
      },
      {
        path: 'detail-user/:userId?',
        name: 'PlatformUserDetailPage',
        component: PlatformUserDetailPage,
      },
      {
        path: 'mobile-display',
        name: 'PlatformMobileDisplayPage',
        component: PlatformMobileDisplayPage,
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

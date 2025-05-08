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
import CinemaLoginPage from '@/views/cinema-management/CinemaLoginPage.vue'
import CinemaHomePage from '@/views/cinema-management/CinemaHomePage.vue'
import CinemaHomeChild from '@/views/cinema-management/child/CinemaHomeChild.vue'
import CinemaScreeningRoomAddPage from '@/views/cinema-management/child/ScreeningRoomPageChild/CinemaScreeningRoomAddPage.vue'
import CinemaScreeningRoomListPage from '@/views/cinema-management/child/ScreeningRoomPageChild/CinemaScreeningRoomListPage.vue'
import CinemaSliceArrangementAddPage from '@/views/cinema-management/child/SliceArrangementPageChild/CinemaSliceArrangementAddPage.vue'
import CinemaSliceArrangementListPage from '@/views/cinema-management/child/SliceArrangementPageChild/CinemaSliceArrangementListPage.vue'
import CustomerSelectScreeningRoomPage from '@/views/customer/child/seatPageChild/CustomerSelectScreeningRoomPage.vue'
import CustomerSelectSeatPage from '@/views/customer/child/seatPageChild/CustomerSelectSeatPage.vue'
import CustomerPayOrderPage from '@/views/customer/child/orderPageChild/CustomerPayOrderPage.vue'
import CustomerVerifyVotePage from '@/views/customer/child/orderPageChild/CustomerVerifyVotePage.vue'
import CustomerOrderListPage from '@/views/customer/child/orderPageChild/CustomerOrderListPage.vue'
import CustomerRatingListPage from '@/views/customer/child/moviePageChild/CustomerRatingListPage.vue'
import MovieListView from '@/components/global/MovieListView.vue'
import CinemaInfoManagementPage from '@/views/cinema-management/child/AnotherPageChild/CinemaInfoManagementPage.vue'
import CinemaAccountManagementPage from '@/views/platform-management/child/cinema-management/CinemaAccountManagementPage.vue'

Vue.use(VueRouter)

const routes = [
  // 用户  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
            path:'detail/:movieId?',
            name: 'CustomerMovieDetailPage',
            component: CustomerMovieDetailPage,
          },
          {
            path: 'select-screening-room', 
            name: 'CustomerSelectScreeningRoomPage',
            component: CustomerSelectScreeningRoomPage
          },
          {
            path:'select-seat/:sliceArrangementId?',
            name: 'CustomerSelectSeatPage',
            component: CustomerSelectSeatPage
          },
          {
            path: 'pay-order/:orderId?',
            name: 'CustomerPayOrderPage',
            component: CustomerPayOrderPage
          },
          {
            path:'verify-vote/:orderId?',
            name: 'CustomerVerifyVotePage',
            component: CustomerVerifyVotePage
          },
          {
            path: 'order-list',
            name: 'CustomerOrderListPage',
            component: CustomerOrderListPage
          },
          {
            path: 'search-list/:movieName',
            name: 'MovieListView',
            component: MovieListView
          }
        ]
      },
      {
        path: 'rating-list',
        name: 'CustomerRatingListPage',
        component: CustomerRatingListPage
      }
    ]
  },
  // 平台  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  {
    path: '/all/admin',
    redirect: '/platform/login'
  },
  {
    path: '/a',
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
      },
      {
        path: 'cinema-account-management',
        name: 'CinemaAccountManagementPage',
        component: CinemaAccountManagementPage,
      }
    ]
  },
  // 影院  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  {
    path: '/cinema/admin',
    redirect: '/cinema/login'
  },
  {
    path: '/c',
    redirect: '/cinema/login'
  },
  {
    path: '/cinema/login',
    name: 'CinemaLoginPage',
    component: CinemaLoginPage
  },{
    path: '/cinema/home',
    name: 'CinemaHomePage',
    component: CinemaHomePage,
    children:[
      {
        path: '',
        name: 'CinemaHomeChild',
        component: CinemaHomeChild,
      },
      {
        path: 'add-screening-room',
        name: 'CinemaScreeningRoomAddPage',
        component: CinemaScreeningRoomAddPage,
      },
      {
        path: 'list-screening-room',
        name: 'CinemaScreeningRoomListPage',
        component: CinemaScreeningRoomListPage,
      },
      {
        path: 'add-slice-arrangement',
        name: 'CinemaSliceArrangementAddPage',
        component: CinemaSliceArrangementAddPage,
      },
      {
        path: 'list-slice-arrangement',
        name: 'CinemaSliceArrangementListPage',
        component: CinemaSliceArrangementListPage
      },
      {
        path: 'info-management',
        name: 'CinemaInfoManagementPage',
        component: CinemaInfoManagementPage
      }
    ]
  }
]

const originalPush=VueRouter.prototype.push

VueRouter.prototype.push=function push(location){
  return originalPush.call(this,location).catch(err=>{
    if(err.name !== 'NavigationDuplicated') throw err
  })
}

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

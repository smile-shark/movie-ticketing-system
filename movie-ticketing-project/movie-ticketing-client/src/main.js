import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import apis from './axios/api'
import api from './axios/my'

Vue.config.productionTip = false
Vue.use(ElementUI)
// Vue.config.globalProperties.$apis = apis vue3的用法
Vue.prototype.$apis = apis // vue2的用法
Vue.prototype.$api = api // vue2的用法

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import apis from './axios/api'
import { myApi } from './axios/my'

Vue.config.productionTip = false
Vue.use(ElementUI)

// ✅ 正确方式：使用 ElementUI.Message
Vue.prototype.$message = function(options) {
  return Message({ // 使用直接导入的 Message
    ...options,
    offset: 60,
  });
};
['success', 'warning', 'error', 'info'].forEach((type) => {
  Vue.prototype.$message[type] = (options) => {
    return ElementUI.Message[type]({
      ...(typeof options === 'string' ? { message: options } : options),
      offset: 60, // 统一设置偏移量
    });
  };
});
// Vue.config.globalProperties.$apis = apis vue3的用法
Vue.prototype.$apis = apis // vue2的用法
Vue.prototype.$api = myApi // vue2的用法

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import routes from './router/index'
import store from './store'
import axios from 'axios'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.config.productionTip = false

Vue.use(VueRouter)
Vue.use(ElementUI, { size: 'small', zIndex: 3000 });

Vue.prototype.$axios = axios

const router = new VueRouter({
  routes
  // strict: process.env.NODE_ENV !== 'production'
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})

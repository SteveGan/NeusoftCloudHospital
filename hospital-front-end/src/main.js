import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import routes from './router/index'
import axios from 'axios'

Vue.config.productionTip = false

Vue.use(VueRouter)

Vue.prototype.$axios = axios

const router = new VueRouter({
  routes
  // strict: process.env.NODE_ENV !== 'production'
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

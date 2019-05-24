// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
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



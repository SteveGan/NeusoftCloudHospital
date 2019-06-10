import axios from 'axios'
import store from '@/store'
import {
  getToken
} from '@/utils/auth'
import {
  Message,
  MessageBox
} from 'element-ui'



// create an axios object
const service = axios.create({
  baseURL: process.env.BASE_API,
  timeout: 80000 // 请求超时时间
})

// request interceptors
service.interceptors.request.use(
  config => {
    //如果有token
    if (store.getters.token) {
      // 让每个请求携带自定义token 请根据实际情况自行修改
      config.headers['Authorization'] = getToken()
    }
    console.log(config.data)
    return config
  },
  error => {
    // log the error
    console.log(error)
    return Promise.reject(error)
  })

// response interceptors
service.interceptors.response.use(
  response => {
    /**
     * if response code is not 200, do something
     */
    const status = response.data.code
    if (status !== 200) {
      Message({
        message: res.message,
        type: 'error',
        duration: 3 * 1000
      })

      // 401:未登录;
      if (status === 401 || status === 403) {
        MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('FedLogOut').then(() => {
            // 为了重新实例化vue-router对象
            location.reload()
          })
        })
      }
      return Promise.reject('error')
    } else {
      return response
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 3 * 1000
    })
    return Promise.reject(error)
  }
)

export default service

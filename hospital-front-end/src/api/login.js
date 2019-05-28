//request是一个定义过 baseURL, 超时时间，拦截器的axios对象
import request from '@/utils/request'


export function login(userId, password){
  return request({
    url: '/user/login',
    method: 'post',
    data: {
      userId,
      password
    }
  })
}

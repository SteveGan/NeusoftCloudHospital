//request是一个定义过 baseURL, 超时时间，拦截器的axios对象
import request from '@/utils/request'

export function getInvoiceCode(){
  return request({
    url: '/registration/invoiceId',
    method: 'get'
  })
}
export function getRegistrationId() {
  return request({
    url: '/registration/registrationId',
    method: 'get'
  })
}
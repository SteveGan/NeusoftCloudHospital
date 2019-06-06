//request是一个定义过 baseURL, 超时时间，拦截器的axios对象
import request from '@/utils/request'

export default {
  listAllDepartments() {
    return request({
      url: '/basicinfo/departments',
      method: 'get'
    })
  },

  getNextInvoiceCode(){
    return request({
      url: '/registration/invoiceId',
      method: 'get'
    })
  },
  getNextRegistrationId() {
    return request({
      url: '/registration/registrationId',
      method: 'get'
    })
  },
  
  listAvailableDoctors(registrationForm) {
    return request({
      url: '/registration/doctors',
      method: 'get',
      params: registrationForm
    })
  },
  
  calculateTotalFee() {
    return request({
      url: '/registration/totalFee',
      method: 'get'
    })
  },
  
  registerApi(registrationForm){
    return request({
      url: '/registration/confirmation',
      method: 'post',
      data: {
        registrationForm
      }
    })
  }
}

//request是一个定义过 baseURL, 超时时间，拦截器的axios对象
import request from '@/utils/request'

export default {
  // 显示所有科室列表
  listAllDepartments() {
    return request({
      url: '/basicinfo/departments',
      method: 'get'
    })
  },

  // 得到当前挂号单的发票号
  getNextInvoiceCode(){
    return request({
      url: '/registration/invoiceId',
      method: 'get'
    })
  },

  // 得到当前挂号单的病历号
  getNextRegistrationId() {
    return request({
      url: '/registration/registrationId',
      method: 'get'
    })
  },
  
  // 查看所有可选医生
  listAvailableDoctors(registrationForm) {
    return request({
      url: '/registration/doctors',
      method: 'get',
      params: registrationForm
    })
  },
  
  // 根据挂号级别，是否购买病历本，计算应收金额
  calculateTotalFee(registrationForm) {
    return request({
      url: '/registration/totalFee',
      method: 'get',
      params: registrationForm
    })
  },
  
  // 挂号操作
  confirmation(registrationForm){
    return request({
      url: '/registration/confirmation',
      method: 'post',
      data: registrationForm
    })
  },

  // 显示所有挂号信息列表
  registrations(){
    return request({
      url: '/registration/registrations',
      method: 'get'
    })
  },
  
  // 退号
  withdrawal(transferData) {
    return request({
      url: '/registration-withdrawal/withdrawal',
      method: 'post',
      data: transferData
    })
  }
}

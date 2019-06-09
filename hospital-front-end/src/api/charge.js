//request是一个定义过 baseURL, 超时时间，拦截器的axios对象
import request from '@/utils/request'

export default {
  // 通过患者病历号，显示患者基本信息和收费项目列表
  getpaymentInfo(id) {
    return request({
      url: 'payment/paymentInfo/' + id,
      method: 'get'
    })
  },
}
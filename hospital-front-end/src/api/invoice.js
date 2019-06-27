//request是一个定义过 baseURL, 超时时间，拦截器的axios对象
import request from '@/utils/request'

export default {
  // 通过患者病历号，显示患者基本信息和收费项目列表
  insertInvoices(beginInvoiceCode, endInvoiceCode) {
    return request({
      url: 'invoice/invoices',
      method: 'get',
      params: {beginInvoiceCode, endInvoiceCode}
    })
  },

  // 查询发票分类信息
  invoiceInfo() {
    return request({
      url: 'invoice/invoiceInfo',
      method: 'get'
    })
  },

  // 查询发票分类信息
  listInvoice() {
    return request({
        url: 'invoice/list',
        method: 'get'
    })
  },

}
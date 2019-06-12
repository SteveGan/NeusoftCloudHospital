import request from '@/utils/request'

export default {
    // 1.2.1 根据id获取科目信息
    getPriceById(id) {
      return request({
        url: '/basicinfo/price/' + id,
        method: 'GET'
      })
    },

    // 1.2.2 新增科目信息
    addPrice(price) {
      return request({
        url: '/basicinfo/price',
        method: 'POST',
        data: price
      })
    },

    // 1.2.3 修改科目信息
    updatePrice(price) {
      return request({
        url: '/basicinfo/price',
        method: 'PUT',
        data: price
      })
    },

    // 1.2.4 删除科目信息
    deletePrice(id) {
      return request({
        url: '/basicinfo/price/' + id,
        method: 'DELETE'
      })
    },

    // 1.2.5 列出全部科目
    listAllPrices() {
      return request({
        url: '/basicinfo/prices',
        method: 'GET'
      })
    },

    // 1.2.6 分页列出科目
    listPricesByPage(num, size) {
      return request({
        url: '/prices/' + num + '/' + size,
        method: 'GET'
      })
    },

    // 1.2.7 树状图列出科目
    listPricesTree() {
      return request({
        url: '/prices/tree',
        method: 'GET'
      })
    }
}
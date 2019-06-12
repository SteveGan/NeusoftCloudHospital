import request from '@/utils/request'

export default {
    // 列出全部科目
    listAllPrices() {
      return request({
        url: '/basicinfo/costs',
        method: 'GET'
      })
    }
}
import request from '@/utils/request'

export default {
    // 
    getPaytypeById(id) {
      return request({
        url: '/basicinfo/paytype/' + id,
        method: 'GET'
      })
    },

    // 
    listAllPaytypes() {
      return request({
        url: '/basicinfo/paytypes',
        method: 'GET'
      })
    }
}
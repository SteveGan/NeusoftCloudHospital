import request from '@/utils/request'

export default {
    // 1.2.1 根据id获取科室信息
    getNonmedicineById(id) {
      return request({
        url: '/basicinfo/non-medicine/' + id,
        method: 'GET'
      })
    },

    // 1.2.2 新增科室信息
    addNonmedicine(nonMedicine) {
      return request({
        url: '/basicinfo/non-medicine',
        method: 'POST',
        data: nonMedicine
      })
    },

    // 1.2.3 修改科室信息
    updateNonmedicine(nonMedicine) {
      return request({
        url: '/basicinfo/non-medicine',
        method: 'PUT',
        data: nonMedicine
      })
    },

    // 1.2.4 删除科室信息
    deleteNonmedicine(id) {
      return request({
        url: '/basicinfo/non-medicine/' + id,
        method: 'DELETE'
      })
    },

    // 1.2.5 列出全部科室
    listAllNonmedicines() {
      return request({
        url: '/basicinfo/non-medicines',
        method: 'GET'
      })
    }
}
import request from '@/utils/request'

export default {
    // 1.4.1 根据id获取挂号级别信息
    getRegistrationLevelById(id) {
      return request({
        url: '/basicinfo/registration-level/' + id,
        method: 'GET'
      })
    },
}
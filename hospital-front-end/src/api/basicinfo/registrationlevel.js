import request from '@/utils/request'

export default {
    // 1.4.1 根据id获取挂号级别信息
    getRegistrationLevelById(id) {
      return request({
        url: '/basicinfo/registration-level/' + id,
        method: 'GET'
      })
    },

    // 1.2.2 新增挂号级别信息
    addRegistrationLevel(registrationLevel) {
      return request({
        url: '/basicinfo/registration-level',
        method: 'POST',
        data: registrationLevel
      })
    },

    // 1.2.3 修改挂号级别信息
    updateRegistrationLevel(registrationLevel) {
      return request({
        url: '/basicinfo/registration-level',
        method: 'PUT',
        data: registrationLevel
      })
    },

    // 1.2.4 删除挂号级别信息
    deleteRegistrationLevel(id) {
      return request({
        url: '/basicinfo/registration-level/' + id,
        method: 'DELETE'
      })
    },

    // 1.2.5 列出全部挂号级别
    listAllRegistrationLevels() {
      return request({
        url: '/basicinfo/registration-levels',
        method: 'GET'
      })
    }
}
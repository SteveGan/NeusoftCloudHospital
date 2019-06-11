import request from '@/utils/request'

export default {
    // 1.3.1 根据id获取用户信息
    getUserById(id) {
      return request({
        url: '/basicinfo/user/' + id,
        method: 'GET'
      })
    },

    // 1.3.2 新增用户信息
    addUser(user) {
      return request({
        url: '/basicinfo/user',
        method: 'POST',
        data: user
      })
    },

    // 1.3.3 修改用户信息
    updateUser(user) {
      return request({
        url: '/basicinfo/user',
        method: 'PUT',
        data: user
      })
    },

    // 1.3.4 删除用户信息
    deleteUser(id) {
      return request({
        url: '/basicinfo/user/' + id,
        method: 'DELETE'
      })
    },

    // 1.3.5 返回用户所有角色信息
    listAllUsersAndRoles() {
      return request({
        url: '/basicinfo/users-with-roles',
        method: 'GET'
      })
    }
}
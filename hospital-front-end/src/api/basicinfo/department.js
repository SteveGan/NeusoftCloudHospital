import request from '@/utils/request'

export default {
    // 1.2.1 根据id获取科室信息
    getDepartmentById(id) {
    return request({
      url: '/basicinfo/department/' + id,
      method: 'GET'
        })
    },

    // 1.2.2 新增科室信息
    addDepartment(department) {
        return request({
          url: '/basicinfo/department',
          method: 'POST',
          data: department
        })
    },

    // 1.2.3 修改科室信息
    updateDepartment(department) {
        return request({
          url: '/basicinfo/department',
          method: 'PUT',
          data: department
        })
    },

    // 1.2.4 删除科室信息
    deleteDepartment(id) {
        return request({
          url: '/basicinfo/department/' + id,
          method: 'DELETE',
          data: department
        })
    },

    // 1.2.5 列出全部科室
    listAllDepartments() {
        return request({
          url: '/basicinfo/departments',
          method: 'GET'
        })
    },

    // 1.2.6 分页列出科室
    listDepartmentsByPage(num, size) {
        return request({
          url: '/departments/' + num + '/' + size,
          method: 'GET'
        })
    },

    // 1.2.7 树状图列出科室
    listDepartmentsTree() {
        return request({
          url: '/departments/tree',
          method: 'GET'
        })
    }
}
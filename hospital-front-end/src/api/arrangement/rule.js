import request from '@/utils/request'


export default{
  // 获取所有门诊科室
  listDoctorDepartments() {
    return request({
      url: 'basicinfo/doctordepartments',
      method: 'GET'
    })
  },

  // 获取某科室中所有角色
  listAllRolesByDepartmentId(departmentId) {
    return request({
      url: 'basicinfo/departmentroles/' + departmentId,
      method: 'GET'
    })
  }
}
import request from '@/utils/request'

export default {
    // 临床科室工作量统计
    clinicianDepartmentStatistics(beginDateStr, endDateStr) {
      return request({
        url: '/department-statistics/clinician',
        method: 'GET',
        params: {beginDateStr, endDateStr}
      })
    },

    // 医技科室工作量统计
    technicianDepartmentStatistics(beginDateStr, endDateStr) {
        return request({
          url: '/department-statistics/technician',
          method: 'GET',
          params: {beginDateStr, endDateStr}
        })
    },

    // 门诊医生工作量统计（全部）
    clinicianDoctorsStatistics(beginDateStr, endDateStr) {
      return request({
        url: '/doctor-statistics/clinician',
        method: 'GET',
        params: {beginDateStr, endDateStr}
      })
    },

    // 门诊医生工作量统计（个人）
    clinicianDoctorStatistics(roleId, dateStr) {
      return request({
        url: '/doctor-statistics/clinician/' + roleId,
        method: 'GET',
        params: {dateStr}
      })
    },
}
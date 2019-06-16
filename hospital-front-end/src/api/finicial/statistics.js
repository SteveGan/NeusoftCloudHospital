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
}
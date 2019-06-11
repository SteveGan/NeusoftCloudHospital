//request是一个定义过 baseURL, 超时时间，拦截器的axios对象
import request from '@/utils/request'

export default {
  // （根据病历号或患者姓名或收费日期）获取所有等待列表
  listPatientByCaseIdOrName(projectDateStr, caseId, departmentId) {
    return request({
      url: '/techproject/patients',
      method: 'get',
      params: {projectDateStr, caseId, departmentId}
    })
  },
}
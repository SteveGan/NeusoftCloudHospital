//request是一个定义过 baseURL, 超时时间，拦截器的axios对象
import request from '@/utils/request'

export default {
  // （根据病历号或患者姓名或收费日期）获取所有等待列表
  listPatientByCaseIdOrName(chargeDateStr, caseId, departmentId) {
    return request({
      url: '/techproject/patients',
      method: 'get',
      params: {chargeDateStr, caseId, departmentId}
    })
  },

  // 选中患者，查看已申请的检查/检验项目详情
  listAllProjectsByCaseId(chargeDateStr, caseId, departmentId) {
    return request({
      url: '/techproject/patient-projects',
      method: 'get',
      params: {chargeDateStr, caseId, departmentId}
    })
  },

  // 登记
  checkInProject(project) {
    return request({
      url: '/techproject/checkin',
      method: 'put',
      data: project
    })
  },

  // 显示本科室已登记项目列表
  listCheckedInButNotRecordedProject(object){
    return request({
      url: '/techproject/registeredprojects',
      method: 'get',
      params: object
    })
  },

  // 录入项目结果
  result(object){
    return request({
      url: '/techproject/result',
      method: 'put',
      data: object
    })
  },

  // 确定某项目执行完毕
  confirmProject(object){
    return request({
      url: '/techproject/confirmation',
      method: 'put',
      data: object
    })
  },
}
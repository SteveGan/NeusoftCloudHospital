import request from '@/utils/request'

//根据caseId请求该病历数据
//param: caseId
export function getCaseContent(params){
  return request({
    url: '/doctorstation/patientcaseinfo/' + params.roleId + "/" + params.caseId,
    method: 'get'
  })
}

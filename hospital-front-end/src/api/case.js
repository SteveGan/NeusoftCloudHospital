import request from '@/utils/request'

//根据caseId请求该病历数据
//param: caseId
export function getCaseContent(params) {
  return request({
    url: '/doctorstation/patientcaseinfo/' + params.roleId + "/" + params.caseId,
    method: 'GET'
  })
}

//提交病历
//data: case
export function submitCase(patientCase) {
  return request({
    url: '/doctorstation/submission',
    method: 'PUT',
    data: patientCase
  })
}

//暂存病历
//data: case
export function saveCase(patientCase) {
  return request({
    url: '/doctorstation/preservation',
    method: 'PUT',
    data: patientCase
  })
}

//清空当前的case
//caseId: 要清空的case的id
export function clearCase(caseId) {
  return request({
    url: '/doctorstation/clear/' + caseId,
    method: "DELETE"
  })
}

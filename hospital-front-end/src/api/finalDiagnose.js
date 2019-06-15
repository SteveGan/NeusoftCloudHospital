import request from '@/utils/request'


//得到某个病历的最终诊断信息
export function listFinalDiagnoses(caseId) {
  return request({
    url: '/doctorstation/finaldiagnose/' + caseId,
    method: 'GET'
  })
}


//暂存/提交当前的最终诊断
export function saveFinalDiagnose(patientCase) {
  return request({
    url: '/doctorstation/finaldiagnose',
    method: 'PUT',
    data: patientCase
  })
}

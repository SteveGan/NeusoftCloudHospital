import request from '@/utils/request'


//暂存当前的最终诊断
export function saveFinalDiagnose(patientCase) {
  return request({
    url: '/doctorstation/finalDiagnose',
    method: 'PUT',
    data: patientCase
  })
}

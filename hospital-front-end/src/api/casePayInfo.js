import request from '@/utils/request'

//更具病历id找到当前病历下的所有缴费信息
export function listPayInfoByCaseId(caseId) {
  return request({
    url: '/payment/transactionLogs/' + caseId,
    method: 'GET'
  })
}

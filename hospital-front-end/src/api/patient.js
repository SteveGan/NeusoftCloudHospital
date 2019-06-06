import request from '@/utils/request'


//请求该医生当天所有待就诊患者与已就诊患者
//param: roleId
export function listAllPatients(params){
  return request({
    url: '/oauth/login',
    method: 'get',
    params: params
  })
}

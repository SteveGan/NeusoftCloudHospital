import request from '@/utils/request'

//请求该医生某身份下可用的病理模版
//roleId:该医生的角色id
export function listCaseTemplate(roleId) {
  return request({
    url: '/doctorstation/casetemplate/list/' + roleId,
    method: 'GET'
  })
}

//将新的模版发给后端
//caseTemplate: 新的模版对象
export function submitNewCaseTemplate(caseTemplate) {
  return request({
    url: '/doctorstation/casetemplate/preservation',
    method: 'POST',
    data: caseTemplate
  })
}

//将更改过的模版发给后端
//caseTemplate: 更新过的模版对象
export function updateCaseTemplate(caseTemplate) {
  return request({
    url: '/doctorstation/casetemplate/modification',
    method: 'PUT',
    data: caseTemplate
  })
}

import request from '@/utils/request'


//根据角色id查询所有的常用诊断（病）
export function listDiagnoseTemplates(roleId) {
  return request({
    url: '/template/diagnosetemplate/list/' + roleId,
    method: 'GET'
  })
}

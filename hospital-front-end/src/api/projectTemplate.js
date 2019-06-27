import request from '@/utils/request'

// 得到响应类型（检查/检验）的模版
export function listProjectTemplate(roleId, collectionType) {
  return request({
    url: '/template/collectiontemplate/list/' + roleId + '/' + collectionType,
    method: 'GET'
  })
}

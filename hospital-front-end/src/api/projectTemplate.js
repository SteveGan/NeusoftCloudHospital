import request from '@/utils/request'


// 得到响应类型（检查/检验）的模版
export function listProjectTemplate(roleId, collectionType) {
  return request({
    url: '/template/collectiontemplate/list/' + roleId + '/' + collectionType,
    method: 'GET'
  })
}


// 存储检查/检验/处置模版（组套）
export function saveProjectTemplate(data) {
  return request({
    url: '/template/collectiontemplate/preservation',
    method: 'POST',
    data: data
  })
}

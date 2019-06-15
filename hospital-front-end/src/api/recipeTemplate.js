import request from '@/utils/request'


//读取该医生该职位的所有可用处方模版
export function listRecipeTemplates(roleId, typeId) {
  return request({
    url: '/template/recipetemplate/list/' + roleId + "/" + typeId,
    method: 'GET'
  })
}

//创建模版
export function createRecipeTemplate(data) {
  return request({
    url: '/template/recipetemplate/preservation',
    method: 'POST',
    data: data
  })
}

//修改已经创建的模版
export function updateRecipeTemplate(data) {
  return request({
    url: '/template/recipetemplate/modification',
    method: 'PUT',
    data: data
  })
}

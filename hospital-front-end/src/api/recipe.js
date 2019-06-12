import request from '@/utils/request'

//根据病历号请求该病历号下的所有处方
//param: caseId
export function listCaseRecipes(caseId) {
  return request({
    url: '/recipe/list/' + caseId,
    method: 'GET'
  })
}

//保存该处方
//param: recipe
export function saveRecipe(recipe) {
  return request({
    url: '/recipe/preservation',
    method: 'PUT',
    data: recipe
  })
}


//开立该处方
//param: recipe
export function submitRecipe(recipe) {
  return request({
    url: '/recipe/submission',
    method: "PUT",
    data: recipe
  })
}


//查询所有的中药和西药
//param: drugType
export function listAllMedicines(drugType) {
  return request({
    url: '/basicinfo/medicinetype/' + drugType,
    method: "GET"
  })
}

//想后端请求新的recipe编号
export function getNewRecipeCode() {
  return request({
    url: '/recipe/application',
    method: "POST"
  })
}

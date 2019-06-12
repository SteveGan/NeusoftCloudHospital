import request from '@/utils/request'


//请求所有的检查/检验/处置申请单(collection)
//param: caseId, collectionType -> 是检查还是/检验/还是处置
export function listAllCollections(caseId, collectionType) {
  return request({
    url: '/doctorstation/collection/list/' + caseId + "/" + collectionType,
    method: 'GET'
  })
}


//请求新的检查/检验/处置申请单号吗
//param: collectionType -> 是检查还是/检验/还是处置
export function getNewCollectionId(collectionType) {
  return request({
    url: '/doctorstation/collection/application/' + collectionType,
    method: 'POST'
  })
}


//请求所有的检查项目/检验项目
//param: projectType -> 是检查0还是检验1
export function listAllProjects(projectType) {
  return request({
    url: '/project/list/type/' + projectType,
    method: 'GET'
  })
}


//请求某项目下的所有小项信息
//param: projectId 项目的id
export function listAllItems(projectId) {
  return request({
    url: '/project/items/' + projectId,
    method: 'GET'
  })
}


//请求暂存当前的一个collection
//param: collection
export function saveCollection(collection) {
  return request({
    url: '/doctorstation/collection/preservation',
    method: 'PUT',
    data: collection
  })
}


//请求开立一个collection
//param: collection
export function submitCollection(collection) {
  return request({
    url: '/doctorstation/collection/submission',
    method: 'PUT',
    data: collection
  })
}

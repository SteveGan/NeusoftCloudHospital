import request from '@/utils/request'


//请求所有的中医/西医疾病疾病
// 0 中医
// 1 西医
export function listAllDiseases(code){
  return request({
    url:'/basicinfo/diseasetype/' + code,
    method: 'get'
  })
}

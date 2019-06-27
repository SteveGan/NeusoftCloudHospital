import request from '@/utils/request'


//告诉后端呼叫下一位患者
export function updateOutpatientQueue(data) {
  return request({
    url: '/notification/outpatient/queue',
    method: 'post',
    data: data
  })
}

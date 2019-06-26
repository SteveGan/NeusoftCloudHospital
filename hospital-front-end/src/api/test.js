import request from '@/utils/request'


export function askRequest() {
  return request({
    url: '/testwebsocket',
    method: "POST",
    data: null
  })
}

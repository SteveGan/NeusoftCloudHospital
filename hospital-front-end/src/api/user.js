import request from '@/utils/request'

//设置用户的avatar
//data demo: {"id":3231, "avatarUrl": "some-link-url"}
export function setUserAvatar(data) {
  return request({
    url: '/user/avatar/modification',
    method: 'POST',
    data: data
  })
}

//更新用户的密码
//data demo: {"id": 1312, "oldPassword": "@#@!#", "newPassword": "#@@#!"}
export function updateUserPassword(data) {
  return request({
    url: '/user/password/modification',
    method: 'POST',
    data: data
  })
}

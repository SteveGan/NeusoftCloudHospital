import Cookies from 'js-cookie'

const TokenKey = 'loginToken'
const ExpireDays = {
  expires: 7
}

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token, ExpireDays)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

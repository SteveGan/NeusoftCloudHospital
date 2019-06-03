import {login} from '@/api/login'
import {getToken, setToken, removeToken} from '@/utils/auth'

const state = {
  token: getToken(),
  name: '',
  id: '',
  avatar: '',
  roles: []
}

const getters = {
}

const actions = {

  //登录
  Login ({commit}, {userId, password}) {
    return new Promise((resolve, reject) => {
      // alert(userId)
      // alert(passWord)
      login(userId, password).then(response => {
        const data = response.data
        if (data.success === true){
          const tokenStr = "一个假的token"
          setToken(tokenStr)
          commit('setToken', tokenStr)
          commit('setName', data.userName)
          commit('setId', data.userId)
          commit('setAvatar', data.avatar)
          commit('setRoles', data.roles)
          resolve('success')
        }else {
          commit('setToken', '')
          resolve('fail')
        }
      }).catch(error => {
        // alert("get error")
        reject(error)
      })
    })
  },

  //前端登出
  FedLogOut({commit}){
    return new Promise(resolve => {
      //将store中的token设为空
      commit('SET_TOKEN', {token: ''})
      removeToken()
      resolve()
    })
  }
}

const mutations = {
  setToken: (state, token) => {
    state.token = token
  },
  setName: (state, name) => {
    state.name = name
  },
  setId: (state, id) => {
    state.id = id
  },
  setAvatar: (state, avatar) => {
    state.avatar = avatar
  },
  setRoles: (state, roles) => {
    state.roles = roles
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}

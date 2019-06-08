import {login} from '@/api/login'
import {getToken, setToken, removeToken} from '@/utils/auth'

const state = {
  token: getToken(),
  name: '',
  id: '',
  avatar: '',
  roles: [],
  currentRole: {
    id:"3"
  }
}

const getters = {
  currentRoleId : state => {
    return state.currentRole.id
  }
}

const actions = {

  //登录
  Login ({commit}, {userId, password}) {
    return new Promise((resolve, reject) => {
      login(userId, password).then(response => {
        console.log(response)
        const data = response.data.data
        if (response.data.code === 200){
          const tokenStr = data.web_token
          setToken(tokenStr)
          // 存储该用户相关信息
          commit('setToken', tokenStr)
          commit('setName', data.datauserName)
          commit('setId', data.userId)
          commit('setAvatar', data.avatar)
          commit('setRoles', data.roles)
          resolve('success')
        } else {
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
  },
  setCurrentRole : (state, role) => {
    state.role = role
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}

import {
  login
} from '@/api/login'
import {
  getToken,
  setToken,
  removeToken
} from '@/utils/auth'

const state = {
  token: getToken(),
  name: '',
  id: '',
  avatar: '',
  roles: [],
  currentRole: {}
}

const getters = {
  currentRoleId: state => {
    return state.currentRole.id
  },
  currentDepartmentId: state => {
    return state.currentRole.deparmentId;
  },
  currentRoleDescription: state => {
    if (Object.keys(state.currentRole).length === 0) {
      return "请选择您的职位"
    } else {
      var description = "部门：" + state.currentRole.departmentName + "  职位：" + state.currentRole.positionName
      if (state.currentRole.titleName != null) {
        description = description + "  职称：" + state.currentRole.titleName
      }
      return description
    }
  },
  roles: state => {
    return state.roles;
  }
}

const actions = {

  //登录
  Login({
    commit
  }, {
    userId,
    password
  }) {
    return new Promise((resolve, reject) => {
      login(userId, password).then(response => {
        const data = response.data.data
        console.log(data)
        if (response.data.code === 200) {
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
  FedLogOut({
    commit
  }) {
    return new Promise(resolve => {
      //将store中的token设为空
      commit('SET_TOKEN', {
        token: ''
      })
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
  setCurrentRole: (state, role) => {
    state.currentRole = Object.assign({}, role)
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}

const HelloWorld = r => require.ensure([], () => r(require('@/components/common/HelloWorld')), 'HelloWorld')
const Login = r => require.ensure([], () => r(require('@/pages/Login/Login')), 'Login')


export default [
  {
    path: '/',
    name: 'HelloWorld',
    component: HelloWorld
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
]

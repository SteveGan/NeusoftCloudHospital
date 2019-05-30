const login = r => require.ensure([], () => r(require('@/pages/Login/Login')), 'login')
const Home = r => require.ensure([], () => r(require('@/pages/Home/Home')), 'Home')
const Main = r => require.ensure([], () => r(require('@/pages/Main/Main')), 'Main')
const Admin = r => require.ensure([], () => r(require('@/pages/Admin/Admin')), 'Admin')

export default [
  {
    path: '/home',
    name: 'Home',
    component: Home,
    redirect: '/home/main',
    children: [
      {
        path: 'main',
        name: 'Main',
        component: Main
      },
      {
        path: 'admin',
        name: 'Admin',
        component: Admin
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: login
  }
]

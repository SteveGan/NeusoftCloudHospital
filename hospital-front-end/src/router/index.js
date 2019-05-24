const HelloWorld = r => require.ensure([], () => r(require('@/components/common/HelloWorld')), 'HelloWorld')
const Raven = r => require.ensure([], () => r(require('@/components/common/Raven')), 'Raven')

export default [
  {
    path: '/',
    name: 'HelloWorld',
    component: HelloWorld
  },
  {
    path: '/raven',
    name: 'Raven',
    component: Raven
  }
]

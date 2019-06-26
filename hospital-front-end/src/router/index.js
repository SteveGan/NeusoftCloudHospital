import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter);

const login = r => require.ensure([], () => r(require('@/pages/Login/Login')), 'login')
const Waiting = r => require.ensure([], () => r(require('@/pages/Waiting/Waiting')), 'Waiting')
const Home = r => require.ensure([], () => r(require('@/pages/Home/Home')), 'Home')
const Main = r => require.ensure([], () => r(require('@/pages/Main/Main')), 'Main')
const Admin = r => require.ensure([], () => r(require('@/pages/Admin/Admin')), 'Admin')
const UserAdmin = r => require.ensure([], () => r(require('@/components/admin/UserAdmin/UserAdmin')), 'UserAdmin')
const ScheduleAdmin = r => require.ensure([], () => r(require('@/components/admin/ScheduleAdmin/ScheduleAdmin')), 'ScheduleAdmin')
const RegistrationLevelAdmin = r => require.ensure([], () => r(require('@/components/admin/RegistrationLevelAdmin/RegistrationLevelAdmin')), 'RegistrationLevelAdmin')
const PayTypeAdmin = r => require.ensure([], () => r(require('@/components/admin/PayTypeAdmin/PayTypeAdmin')), 'PayTypeAdmin')
const NonMedicineProjectAdmin = r => require.ensure([], () => r(require('@/components/admin/NonMedicineProjectAdmin/NonMedicineProjectAdmin')), 'NonMedicineProjectAdmin')
const DiseaseAdmin = r => require.ensure([], () => r(require('@/components/admin/DiseaseAdmin/DiseaseAdmin')), 'DiseaseAdmin')
const DepartmentAdmin = r => require.ensure([], () => r(require('@/components/admin/DepartmentAdmin/DepartmentAdmin')), 'DepartmentAdmin')
const ArrangementRuleAdmin = r => require.ensure([], () => r(require('@/components/admin/ArrangementRuleAdmin/ArrangementRuleAdmin')), 'ArrangementRuleAdmin')
const OutPatientDoctor = r => require.ensure([], () => r(require('@/pages/OutPatientDoctor/OutPatientDoctor')), 'OutPatientDoctor')
const Cashier = r => require.ensure([], () => r(require('@/pages/Cashier/Cashier')), 'Cashier')
const TechDoctor = r => require.ensure([], () => r(require('@/pages/TechDoctor/TechDoctor')), 'TechDoctor')
const FinicialAdmin = r => require.ensure([], () => r(require('@/pages/FinicialAdmin/FinicialAdmin')), 'FinicialAdmin')
const Dailycheck = r => require.ensure([], () => r(require('@/components/finicialadmin/DailyCheck/DailyCheck')), 'DailyCheck')
const DepartmentWorkloadAnalysis = r => require.ensure([], () => r(require('@/components/finicialadmin/DepartmentWorkloadAnalysis/DepartmentWorkloadAnalysis')), 'DepartmentWorkloadAnalysis')
const PriceAdmin = r => require.ensure([], () => r(require('@/components/finicialadmin/PriceAdmin/PriceAdmin')), 'PriceAdmin')
const UserWorkloadAnalysis = r => require.ensure([], () => r(require('@/components/finicialadmin/UserWorkloadAnalysis/UserWorkloadAnalysis')), 'UserWorkloadAnalysis')
const DrugStation = r => require.ensure([], () => r(require('@/pages/DrugStation/DrugStation')), 'DrugStation')
const TreatmentStation = r => require.ensure([], () => r(require('@/pages/TreatmentStation/TreatmentStation')), 'TreatmentStation')
const Caller = r => require.ensure([], () => r(require('@/pages/Caller/Caller')), 'Caller')

// define routes
const routes = [{
    path: '/home',
    name: 'Home',
    component: Home,
    redirect: '/home/main',
    children: [{
        path: 'main',
        name: 'Main',
        component: Main,
        meta: {
          requiresAuth: true
        }
      },
      {
        path: 'waiting',
        name: 'Waiting',
        component: Waiting,
        meta: {
          requiresAuth: true
        }
      },
      {
        path: 'admin',
        name: 'Admin',
        component: Admin,
        meta: {
          requiresAuth: true
        },
        children: [{
            path: 'user',
            name: 'User',
            component: UserAdmin,
            meta: {
              requiresAuth: true
            }
          },
          {
            path: 'schedule',
            name: 'ScheduleAdmin',
            component: ScheduleAdmin,
            meta: {
              requiresAuth: true
            }
          },
          {
            path: 'registrationlevel',
            name: 'RegistrationLevelAdmin',
            component: RegistrationLevelAdmin,
            meta: {
              requiresAuth: true
            }
          },
          {
            path: 'paytype',
            name: 'PayTypeAdmin',
            component: PayTypeAdmin,
            meta: {
              requiresAuth: true
            }
          },
          {
            path: 'nonmedicineproject',
            name: 'NonMedicineProjectAdmin',
            component: NonMedicineProjectAdmin,
            meta: {
              requiresAuth: true
            }
          },
          {
            path: 'disease',
            name: 'DiseaseAdmin',
            component: DiseaseAdmin,
            meta: {
              requiresAuth: true
            }
          },
          {
            path: 'department',
            name: 'DepartmentAdmin',
            component: DepartmentAdmin,
            meta: {
              requiresAuth: true
            }
          },
          {
            path: 'arrangementrule',
            name: 'ArrangementRuleAdmin',
            component: ArrangementRuleAdmin,
            meta: {
              requiresAuth: true
            }
          }
        ]
      },
      {
        path: 'outpatientdoctor',
        name: 'OutPatientDoctor',
        component: OutPatientDoctor,
        meta: {
          requiresAuth: true
        }
      },
      {
        path: 'techdoctor',
        name: 'TechDoctor',
        component: TechDoctor,
        meta: {
          requiresAuth: true
        }
      },
      {
        path: 'treatmentstation',
        name: 'TreatmentStation',
        component: TreatmentStation,
        meta: {
          requiresAuth: true
        }
      },
      {
        path: 'cashier',
        name: 'Cashier',
        component: Cashier,
        meta: {
          requiresAuth: true
        }
      },
      {
        path: 'finicialadmin',
        name: 'FinicialAdmin',
        component: FinicialAdmin,
        meta: {
          requiresAuth: true
        },
        children: [{
            path: 'dailycheck',
            name: 'Dailycheck',
            component: Dailycheck,
            meta: {
              requiresAuth: true
            }
          },
          {
            path: 'departmentworkloadanalysis',
            name: 'DepartmentWorkloadanalysis',
            component: DepartmentWorkloadAnalysis,
            meta: {
              requiresAuth: true
            }
          },
          {
            path: 'priceadmin',
            name: 'PriceAdmin',
            component: PriceAdmin,
            meta: {
              requiresAuth: true
            }
          }, {
            path: 'userworkloadanalysis',
            name: 'UserWorkloadAnalysis',
            component: UserWorkloadAnalysis,
            meta: {
              requiresAuth: true
            }
          }
        ]
      },
      {
        path: 'drugstation',
        name: 'DrugStation',
        component: DrugStation,
        meta: {
          requiresAuth: true
        }
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: login
  },
  {
    path: '/caller',
    name: 'Caller',
    component: Caller
  }
]

var router = new VueRouter({
  routes
})

// 每次跳转前检查登陆状态，如果未登陆，则路由至登陆页面
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // this route requires auth, check if logged in
    // if not, redirect to login page.
    console.log("验证是否有token")
    console.log(store.getters['user/token'])
    if (typeof (store.getters['user/token']) === "undefined") {
      next({
        path: '/login',
        query: {
          redirect: to.fullPath
        }
      })
    } else {
      next()
    }
  } else {
    next()
  }

})

export default router

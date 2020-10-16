# NeusoftCloudHospital
熙康云医院。爱与健康，连接你我。🏥

![](http://ww3.sinaimg.cn/large/006tNc79ly1g3cckd0xxhj30i7050gm1.jpg)

## 前言
`熙康云医院`项目致力于打造一个完整的医院信息服务系统，采用现阶段流行技术实现。

## 项目介绍

### 项目组成
- 熙康云医院官网
- 云医院医生端H5
- [云医院患者小程序](http://ww3.sinaimg.cn/large/006tNc79ly1g3y5wss61aj30nw0nwjww.jpg)
- 云医院候诊大屏


### 技术选型
#### 后端技术

技术 | 状态 | 说明 |
----|----|----
Spring Boot | 容器+MVC框架 |
Spring Security | 认证和授权框架 |
JWT | JWT登录支持 |
MyBatis | ORM框架  |
MyBatisGenerator | 数据层代码生成 |
PageHelper | MyBatis物理分页插件 |
MySQL | MySQL数据库 |
Druid | 数据库连接池 |
Swagger-UI | 文档生产工具 |
Log4j  | 日志 |
Springboot devtool | Springboot热部署等 |
Taobao IP Share | 淘宝IP分享计划(用于登录日志) |
线程池

- 安全机制
- 日志模块
- 《阿里巴巴Java开发手册》 && 《Google Java Style》
- RESTful
- 项目结构

#### 前端技术
##### 云医院医生端H5
技术 | 说明 | 官网
----|----|----
Vue.js | 前端框架 | [https://vuejs.org/](https://vuejs.org/)
Vue Router | 路由框架 | [https://router.vuejs.org/](https://router.vuejs.org/)
Vuex | 全局状态管理框架 | [https://vuex.vuejs.org/](https://vuex.vuejs.org/)
ElementUI | 前端UI框架 | [https://element.eleme.io/](https://element.eleme.io/)
Axios | 前端HTTP框架 | [https://github.com/axios/axios](https://github.com/axios/axios)
js-cookie | 轻量级浏览器cookies管理工具 | [https://github.com/js-cookie/js-cookie](https://github.com/js-cookie/js-cookie)
sm.ms | 免费图床API | [https://sm.ms/](https://sm.ms/)
Baidu text2video | 百度文字转语音API | [http://ai.baidu.com/docs#/TTS-API/top](http://ai.baidu.com/docs#/TTS-API/top)
vue-print-nb | Chrome打印调用Vue组件

- 对axios封装统一处理请求
- 组件化
- 语音播报患者

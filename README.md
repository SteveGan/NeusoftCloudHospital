# NeusoftCloudHospital
熙康云医院。爱与健康，连接你我。🏥

![](http://ww3.sinaimg.cn/large/006tNc79ly1g3cckd0xxhj30i7050gm1.jpg)

# 协作信息

## Jenkins
[Jenkins]()

## ProcessOn
- [ER图]()

# 前端规范
## UI组件库
- [Ant Design Vue](https://vue.ant.design/docs/vue/introduce/)
- [Echarts](https://echarts.baidu.com/)

# 数据库规约
## 建表规约
1. 参考《阿里巴巴Java开发手册》
2. 字段命名规则：自己表内字段不能重复表名，使用外键字段加上其他表名作为前缀。

## 长度限制
1. varchar长度设置为预估长度乘2向上取整。
2. 金钱设置为decimal(11,2)
3. id根据实际情况来，但必须加上unsigned无符号，tinyint（0 到 255）, smallint（0 到 65535）, int（0 到约 42.9 亿）

# 后端规范
- [POJO与Java bean](https://blog.csdn.net/u012393192/article/details/80808237)

## 包名规则及含义
- controller：控制层接口
- service：服务层
- model：数据模型类(参数模型，数据传输模型等）
  - mapper：mybatis接口
- util：公共类，定义常量类，组件
- exception：自定义异常

## 分层领域模型规约
- DO(Data Object):此对象与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。
- DTO(Data Transfer Object):数据传输对象，Service 或 Manager 向外传输的对象。
- BO(Business Object):业务对象，由 Service 层输出的封装业务逻辑的对象。
- AO(ApplicationObject):应用对象，在Web层与Service层之间抽象的复用对象模型， 极为贴近展示层，复用度不高。
- VO(View Object):显示层对象，通常是 Web 向模板渲染引擎层传输的对象。
- Query:数据查询对象，各层接收上层的查询请求。注意超过 2 个参数的查询封装，禁止
使用 Map 类来传输。

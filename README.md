# NeusoftCloudHospital
熙康云医院。爱与健康，连接你我。🏥


# 协作信息
## 服务器
- IP: 
- User: 
- Password: 

## 数据库
- User: 
- Password: 

## Jenkins
[Jenkins]()

## ProcessOn
- [ER图]()

# 前端规范
## UI组件库
- [Ant Design Vue](https://vue.ant.design/docs/vue/introduce/)
- [Echarts]()

# 数据库规约
## 建表规约
1. 参考阿里Java手册
2. 字段命名规则：自己表内字段不能重复表名，使用外键字段加上其他表名作为前缀。

## 长度限制
1. varchar长度设置为预估长度乘2向上取整。
2. 金钱设置为decimal(11,2)
3. id根据实际情况来，但必须加上unsigned无符号，tinyint（0 到 255）, smallint（0 到 65535）, int（0 到约 42.9 亿）

# 后端规范
## 包名规则及含义

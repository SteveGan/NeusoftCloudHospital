## 1.登录：
POST:
```json
{
	"userId": "20165041",
	"password": "1234"
}
```
RESPONSE：
```json
{
    "roles": [
        {
            "id": 20000001,
            "userId": 10000001,
            "positionId": 2,
            "positionName": "门诊医生",
            "departmentId": 16,
            "departmentName": "神经外科",
            "titleId": 1,
            "titleName": "主任医师",
            "gmtCreate": null,
            "gmtModified": null
        },
        {
            "id": 20000002,
            "userId": 10000001,
            "positionId": 6,
            "positionName": "医院管理员",
            "departmentId": 137,
            "departmentName": "设备科",
            "titleId": 1,
            "titleName": "主任医师",
            "gmtCreate": null,
            "gmtModified": null
        }
    ],
    "success": true,
    "avatar": "http://ww2.sinaimg.cn/large/006tNc79ly1g3hfng0sldj30ei0oidha.jpg",
    "userName": "Amy",
    "userId": 10000001
}
```

2. 查询所有用户：

POST，无请求内容

RESPONSE:
```json
{
  "users": [
    {
      "roles": [
              {
                  "id": 20000001,
                  "userId": 10000001,
                  "positionId": 2,
                  "positionName": "门诊医生",
                  "departmentId": 16,
                  "departmentName": "神经外科",
                  "titleId": 1,
                  "titleName": "主任医师",
                  "gmtCreate": null,
                  "gmtModified": null
              },
              {
                  "id": 20000002,
                  "userId": 10000001,
                  "positionId": 6,
                  "positionName": "医院管理员",
                  "departmentId": 137,
                  "departmentName": "设备科",
                  "titleId": 1,
                  "titleName": "主任医师",
                  "gmtCreate": null,
                  "gmtModified": null
              }
          ],
          "avatar": "http://ww2.sinaimg.cn/large/006tNc79ly1g3hfng0sldj30ei0oidha.jpg",
          "userName": "Amy",
          "userId": 10000001
    },
    {
    
    }
  ]
}
```
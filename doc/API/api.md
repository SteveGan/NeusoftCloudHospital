## 1.登录：
POST:
```json
{
	"userId": "20165041",
	"pass": "1234"
}
```
RESPONSE：
```json
{
	"success":"true或者false",
	"userId":"用户的id",
	"userName": "Steve",
	"userPic": "用户头像的地址",
	"role": [
		{
			"department": {
				"id":"部门id",
				"name":"部门名"
			},
			"position":{
				"id":"职位id",
				"name":"职位名称"
			},
			"title":{
				"id":"职称id",
				"name":"职称名称"
			}
		},
		{
			"...": "..."
		}
	]
}
```
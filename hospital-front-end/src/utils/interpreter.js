// 编码 与 String 解释器

//将病历的编码转换为字符串
export function caseStatusCodeToString(statusCode){
  var status = ''
  // 1: 待诊 2: 暂存 3: 已诊（未确诊）4:确诊 5: 诊毕
  if(statusCode === 1){
    status = "待诊"
  }else if(statusCode === 2){
    status = "暂存"
  }else if(statusCode === 3){
    status = "已诊"
  }else if(statusCode === 4){
    status = "确诊"
  }else if(statusCode === 5){
    status = "诊毕"
  }else{
    status = "未知"
  }
  return status
}

//将性别编码转为字符
export function genderCodeToString(genderCode){
  var gender = ''
  if(genderCode === 0){
    gender = "女"
  }else{
    gender = "男"
  }
  return gender
}

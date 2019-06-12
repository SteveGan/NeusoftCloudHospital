// 编码 与 String 解释器

//将病历的编码转换为字符串
export function caseStatusCodeToString(statusCode) {
  var status = ''
  // 1: 待诊 2: 暂存 3: 已诊（未确诊）4:确诊 5: 诊毕
  if (statusCode === 1) {
    status = "待诊"
  } else if (statusCode === 2) {
    status = "暂存"
  } else if (statusCode === 3) {
    status = "已诊"
  } else if (statusCode === 4) {
    status = "确诊"
  } else if (statusCode === 5) {
    status = "诊毕"
  } else {
    status = "未知"
  }
  return status
}

//将性别编码转为字符
export function genderCodeToString(genderCode) {
  var gender = ''
  if (genderCode === 0) {
    gender = "女"
  } else {
    gender = "男"
  }
  return gender
}

//将检查/检验/处置项目的状态码转为字符
export function projectStatusCodeToString(statusCode) {
  var status = ''
  if (statusCode === 1) {
    status = '未开立'
  } else if (statusCode === 2) {
    status = '已开立'
  } else if (statusCode === 3) {
    status = '已登记'
  } else if (statusCode === 4) {
    status = '执行完毕'
  } else {
    status = '未知'
  }
  return status;
}

//将药品的typeCode转为字符串
export function medicineTypeCodeToString(typeCode) {
  var type = ''
  if (typeCode == 1) {
    type = '中草药'
  } else {
    type = '西药'
  }
}


//将药品类型字符串变为code
export function medicineTypeToCode(type) {
  var code;
  if (type === "中草药") {
    code = 1;
  } else {
    code = 2;
  }
  return code;
}

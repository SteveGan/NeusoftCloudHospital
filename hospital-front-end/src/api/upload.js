//request是一个定义过 baseURL, 超时时间，拦截器的axios对象

export default {
  // （根据病历号或患者姓名或收费日期）获取所有等待列表
  upload(imageFile) {
    return new axios({
        method: 'post',
        url: '/api/upload',
        data: {
            smfile: imageFile,
            ssl: true,
            format: "json"
          }
      })
  },
}
// pages/case/detail/detail.js
Page({

  data: {
    registrationId: "",
    resultInfo: {},
    basicInfo: {},
    inspection: [],
    examination: [],
    recipe: [],
    treatment: []
  },

  onLoad: function (options) {
    this.setData({
      registrationId: options.registrationId
    })
    console.log(options)
  },

  onShow: function () {
    var that = this;
    // 请求查询用户历史病历信息
    wx.request({
      url: "http://www.stevegan.com:1923/patient/resultinfo/" + that.data.registrationId,
      method: 'GET',
      dataType: 'json',
      success: function (res) {
        that.setData({
          resultInfo: res.data.data,
          basicInfo: res.data.data.基本信息,
          inspection: res.data.data.检查结果,
          examination: res.data.data.检验结果,
          recipe: res.data.data.处方信息,
          treatment: res.data.data.处置信息,
        })
        console.log(that.data.inspection)
      }
    })
  },

  onShareAppMessage: function () {

  }
})
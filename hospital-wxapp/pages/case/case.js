// pages/case/case.js
Page({

  data: {
    userId: "",
    caseList: []
  },

  onLoad: function (options) {
    // 本地存储读取用户id
    this.setData({
      userId: wx.getStorageSync('userId')
    });
  },

  onShow: function () {
    var that = this;

    // 请求查询用户历史病历信息
    wx.request({
      url: "http://www.stevegan.com:1923/patient/registrations/" + that.data.userId,
      method: 'GET',
      dataType: 'json',
      success: function (res) {
        console.log(res);
        that.setData({
          caseList: res.data.data
        })
      }
    })
  },

  onShareAppMessage: function () {

  }
})
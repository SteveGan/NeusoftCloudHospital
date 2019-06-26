// pages/case/case.js
Page({

  data: {
    userId: "",
    caseList: [],


    registrationLevelCast: {
      1: "普通号",
      2: "专家号",
      3: "急诊号"
    },

    payTypeCast: {
      1: "自费",
      2: "医保",
      3: "新农合"
    }
  },

  onLoad: function (options) {
    // 本地存储读取用户id
    this.setData({
      userId: wx.getStorageSync('userId')
    });
  },

  onShow: function () {
    var that = this;
    wx.showLoading({
      title: '全速加载中',
    })

    // 请求查询用户历史病历信息
    wx.request({
      url: "http://www.stevegan.com:1923/patient/registrations/" + that.data.userId,
      method: 'GET',
      dataType: 'json',
      success: function (res) {
        console.log(res);
        for(var i=0; i<res.data.data.length; i++){
          res.data.data[i].registrationLevelName = that.data.registrationLevelCast[res.data.data[i].registrationLevelId]
          res.data.data[i].payTypeName = that.data.payTypeCast[res.data.data[i].payType]
        }
        that.setData({
          caseList: res.data.data
        })
      }, complete() {
        wx.hideLoading();
      }
    })
  },

  onShareAppMessage: function () {

  }
})
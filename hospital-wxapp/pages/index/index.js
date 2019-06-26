//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    waitingList: [],
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),

    isLoggedIn: true,
    ifShow: false
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {

  },
  onShow: function () {
    var that = this;
    // 判断登录状态决定显示内容
    if (app.isLoggedIn()) {
      this.setData({
        isLoggedIn: true
      });
    } else {
      this.setData({
        isLoggedIn: false
      });
    };
    // 请求查询用户历史病历信息
    wx.request({
      url: "http://localhost:1923/patient/registration/" + wx.getStorageSync('userId'),
      method: 'GET',
      dataType: 'json',
      success: function (res) {
        console.log(res);
        that.setData({
          waitingList: res.data.data,
          ifShow: true
        })
      }
    })
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})

//login.js
//获取应用实例
var app = getApp();
Page({
  data: {
    remind: '加载中',
    userid_focus: false,
    idCard: "",
    angle: 0
  },
  bind: function() {
    var that = this;
    if (!that.data.idCard ) {
      app.showErrorModal('账号不能为空。', '请检查输入');
      return false;
    }
    app.showLoadToast('绑定中');
    wx.request({
      method: 'POST',
      url: 'http://www.stevegan.com:1923/wxapp/bind',
      data: ({
        idCard: that.data.idCard
      }),
      success: function(res) {
        console.log(res.data.data);
        if (res.data.data !== false) {
          //清除缓存
          wx.removeStorageSync('userId');
          wx.setStorage({
              key: 'userId',
              data: res.data.data
          }),
          wx.switchTab({
            url: '../index/index',
            success: function(e) {
              var page = getCurrentPages().pop();
              if (page == undefined || page == null)
                return;
              page.onReady();
            }
          })
        } else {
          wx.hideToast();
          app.showErrorModal("您的身份证号不存在，请核对后重试。", '绑定失败');
        }
      },
      fail: function(res) {
        wx.hideToast();
        app.showErrorModal("服务器繁忙，请稍后再试。", '绑定失败');
      }
    });
  },
  useridInput: function(e) {
    this.setData({
      idCard: e.detail.value
    });
    if (e.detail.value.length >= 18) {
      wx.hideKeyboard();
    }
  },
  inputFocus: function(e) {
    if (e.target.id == 'cardId') {
      this.setData({
        'userid_focus': true
      });
    }
  },
  inputBlur: function(e) {
    if (e.target.id == 'cardId') {
      this.setData({
        'userid_focus': false
      });
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {
    var that = this;
    var shareObj = {
      title: "熙康云医院 | 爱与健康，连接你我",
      path: '/pages/index/index',
      imageUrl: '',
    };
    return shareObj;
  },
});
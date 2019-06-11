//login.js
//获取应用实例
var app = getApp();
Page({
  data: {
    remind: '加载中',
    help_status: false,
    userid_focus: false,
    passwd_focus: false,
    passwd: '',
    angle: 0,
    showVideo: true
  },
  onReady: function() {
    var that = this;
    var value = wx.getStorageSync('stuID');
    that.setData({
      userid: value
    });
    setTimeout(function() {
      that.setData({
        remind: ''
      });
    }, 1000);
    wx.onAccelerometerChange(function(res) {
      var angle = -(res.x * 30).toFixed(1);
      if (angle > 14) {
        angle = 14;
      } else if (angle < -14) {
        angle = -14;
      }
      if (that.data.angle !== angle) {
        that.setData({
          angle: angle
        });
      }
    });
  },
  onLoad: function() {
    var that = this;
    var value = wx.getStorageSync('stuID');
    that.setData({
      userid: value
    });
    console.log(value)
    if (app.globalData.isiOS) {
      this.setData({
        showVideo: false
      })
    }
  },

  bind: function() {
    var that = this;
    if (!that.data.userid || !that.data.passwd) {
      app.showErrorModal('账号及密码均不能为空。', '请检查输入');
      return false;
    }
    app.showLoadToast('绑定中');
    wx.request({
      method: 'POST',
      url: 'https://neuvwo.com/mini/api/login',
      data: ({
        stuID: that.data.userid,
        stuPass: that.data.passwd
      }),
      success: function(res) {
        console.log(res.data.data);
        if (res.data.data == 'success') {
          wx.showToast({
            title: '请稍候',
          })
          //清除缓存
          wx.removeStorageSync('stuPass_eone');
          wx.setStorage({
            key: 'stuPass_eone',
            data: that.data.passwd,
          })
          wx.setStorage({
              key: 'stuID',
              data: that.data.userid
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
          app.showErrorModal("您的一网通登录凭据不正确，请核对后重试。", '绑定失败');
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
      userid: e.detail.value
    });
    if (e.detail.value.length >= 8) {
      wx.hideKeyboard();
    }
  },
  passwdInput: function(e) {
    this.setData({
      passwd: e.detail.value
    });
  },
  inputFocus: function(e) {
    if (e.target.id == 'userid') {
      this.setData({
        'userid_focus': true
      });
    } else if (e.target.id == 'passwd') {
      this.setData({
        'passwd_focus': true
      });
    }
  },
  inputBlur: function(e) {
    if (e.target.id == 'userid') {
      this.setData({
        'userid_focus': false
      });
    } else if (e.target.id == 'passwd') {
      this.setData({
        'passwd_focus': false
      });
    }
  },
  tapHelp: function(e) {
    if (e.target.id == 'help') {
      this.hideHelp();
    }
  },
  showHelp: function(e) {
    this.setData({
      'help_status': true
    });
  },
  hideHelp: function(e) {
    this.setData({
      'help_status': false
    });
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {
    var that = this;　　 // 设置菜单中的转发按钮触发转发事件时的转发内容
    var shareObj = {
      title: "weNEU | 你想要的，都在这里",
      path: '/pages/index/index',
      imageUrl: '',
    };
    return shareObj;
  },
});
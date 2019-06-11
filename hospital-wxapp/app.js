//app.js
App({
  Login: true,
  globalData: {
    StatusBar: null,
    Custom: null,
    CustomBar: null,
    userInfo: null,
    isiOS: false
  },
  pageBackgroundColor: '#f1f1f1',  
  onLaunch: function () {
    var that = this;
    // 读取本地学号密码缓存，并根据缓存有无判断是否已登录
    try {
      wx.getStorage({
        key: 'userId',
        success: function (res) {
          console.log("[INFO]当前登录用户患者id: " + res.data);
        },
        fail: function () {
          that.Login = false;
          console.log("[INFO]未登录")
        }
      })
    } catch (e) {
      console.warn('[INFO]登录检验-获取学号缓存失败');
      this.Login = false;
    }

    // 获取系统显示高度信息
    wx.getSystemInfo({
      success: e => {
        console.log(e)
        if (e.system.indexOf('iOS') != -1) {
          this.globalData.isiOS = true
        }
        this.globalData.StatusBar = e.statusBarHeight;
        let custom = wx.getMenuButtonBoundingClientRect();
        this.globalData.Custom = custom;
        this.globalData.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
      }
    })

    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
  },
  globalData: {
    userInfo: null
  }
})
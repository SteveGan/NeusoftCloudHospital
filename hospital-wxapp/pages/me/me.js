import Dialog from '../../dist_vant/dialog/dialog';
var app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    userId: "",
    userInfo: {},

    show: false,
    isLoggedIn: false
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onShow: function(options) {
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
    // 本地存储读取用户id
    this.setData({
      userId: wx.getStorageSync('userId')
    });
    // 请求查询用户信息
    wx.request({
      url: "http://www.stevegan.com:1923/patient/info/" + that.data.userId,
      method: 'GET',
      dataType: 'json',
      success: function (res) {
        console.log(res);
        const data = res.data.data;
        if(data.gender == 1){
          data.genderCast = "男";
        } else {
          data.genderCast = "女";
        }
        that.setData({
          userInfo: res.data.data
        })
      }
    })  
  },

  // 重新登录
  login() {
    Dialog.confirm({
      title: '重置账户',
      message: '你确定要重置账户吗？当您需要再次使用本服务时需重新进行绑定操作。'
    }).then(() => {
      console.log('[INFO] 确定重置账户')
      wx.clearStorage()
      wx.switchTab({
        url: '/pages/me/me',
        success: function(e) {
          var page = getCurrentPages().pop();
          page.onLoad();
          page.onShow();
          page.onReady();
        }
      })
    }).catch(() => {
      console.log('已取消重置')
    });
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
})
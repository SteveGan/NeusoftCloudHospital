// pages/user/user.js
var appInstance = getApp();
var GetUser = require('../../utils/getuserinfo.js');
Page({
  
  data: {
    authSetting: false,
    userinfo: '',
    SuggestShopping:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onShow: function (options) {
    if (appInstance.count(appInstance.globalData.userInfo)==0){
      wx.navigateTo({
        url: '/pages/login/login'
      })
    }else{
      GetUser.getYeboMember(this, appInstance, appInstance.globalData.openid)
      getSuggestShoppingList(this, appInstance)
    }
  },
  onCopy: function (res) {
    var invitationcode = res.currentTarget.dataset.invitationcode;
    wx.setClipboardData({
      data: invitationcode,
      success: function (res) {
        wx.showToast({
          title: '复制成功',
          icon: 'success',
          duration: 2000
        })
        console.log(res)
      }
    })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    return {
      title:"医博汇",
      path: '/pages/index/index',
      success: function (res) {
        console.log(res)
        // 转发成功
      }
    }
  }
})
function getSuggestShoppingList(that, appInstance){
  wx.request({
    url: 'https://xcx.yzw0525.com/api/GetSuggestShopping',
    data: {
      offset: 0,
      limit: 4,
      suggesttype: "hot",
    },
    method: "POST",
    header: {
      'content-type': 'application/json',
      'Verification': appInstance.globalData.VERIFICATION,
    },
    success: function (res) {
      var resData = res.data;
      if (resData.errCode == 200) {
        var newlist = resData.item;
        that.setData({
          SuggestShopping: newlist
        })
      }
    },
    fail: function () {
      that.setData({
        isMore: true,
      })
    }
  })
}
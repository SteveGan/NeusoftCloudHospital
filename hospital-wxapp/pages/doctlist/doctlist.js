// doctlist.js
var appInstance = getApp();
var LoadDoct = require('onLoadDoct.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    doctlist:{},
    listinfo:{
      "gc": {
        "banner": "https://xcx.yzw0525.com/images/doctlist_gc.jpg",
        "title": "肛肠科推荐专家"
      },
      "wc": {
        "banner": "https://xcx.yzw0525.com/images/doctlist_wc.jpg",
        "title": "中医胃肠科推荐专家"
      }
    },
    diagnosisType:"gc",
    offset:0,
    is_more: true,
    is_loadnull: false,
    imgpath: appInstance.globalData.urlpath
  },
  onMakePhone:function(){
    wx.makePhoneCall({
      phoneNumber: appInstance.globalData.yebotel
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    that.setData({
      diagnosisType: options.diagnosisType
    })
    LoadDoct.onLoadDoctList(options, that, appInstance)
  },
  onLoadMoreDoctList:function(){
    var that = this;
    LoadDoct.onLoadDoctList(that.data.diagnosisType, that, appInstance)
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})
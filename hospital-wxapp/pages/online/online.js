// online.js
var appInstance = getApp();
var LoadDoct = require('../doctlist/onLoadDoct.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    doctlist: {},
    today: '-',
    index:0,
    offset:0,
  },
  bindDateChange: function (e) {
    this.setData({
      today: e.detail.value
    })
  },
  bindDoctorChange: function (e) {
    this.setData({
      index: e.detail.value
    })
  },
  onMakePhone: function () {
    wx.makePhoneCall({
      phoneNumber: appInstance.globalData.yebotel
    })
  },
  MyfromSubmit:function(evt){
    var postData = evt.detail.value;
    var formId = evt.detail.formId;
    postData.formId = formId
    
    LoadDoct.insertRegister(postData, appInstance)
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options.doctid)
    var that = this;
    if (options.doctid != undefined){
      
      that.setData({
        index: options.doctid
      })
      LoadDoct.onLoadDoctList(options.docttype, that, appInstance)
    }else{
      LoadDoct.onLoadDoctList('',that, appInstance)
    }
    
    console.log(this.data.doctlist)
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
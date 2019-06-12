// pages/case/detail/detail.js
Page({

  data: {
    registrationId: ""
  },

  onLoad: function (options) {
    this.setData({
      registrationId: options.registrationId
    })
    console.log(options)
  },

  onShow: function () {

  },

  onShareAppMessage: function () {

  }
})
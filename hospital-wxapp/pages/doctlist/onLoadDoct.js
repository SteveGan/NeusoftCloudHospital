function onLoadDoctList(diagnosisType, that, appInstance) {
  wx.showLoading({
    title: '加载中',
  })
  wx.request({
    url: 'https://xcx.yzw0525.com/api/getdoctlist', 
    data: {
      docttype: diagnosisType,
      offset: that.data.offset,
      limit: appInstance.globalData.limit
    },
    method: "POST",
    header: {
      'content-type': 'application/json',
      'Verification': appInstance.globalData.VERIFICATION,
    },
    success: function (res) {
      wx.hideLoading()
      console.log(res.data)
      var resData = res.data;
      if (resData.errCode == 200) {
        if (resData.item.length > 0) {
          var newdoctlist = resData.item;
          var doctlist = that.data.doctlist;
          if (doctlist.length > 0) {
            that.data.doctlist = newdoctlist.concat(that.data.doctlist)
          } else {
            that.data.doctlist = newdoctlist
          }
          that.setData({
            doctlist: that.data.doctlist,
            offset: that.data.offset + newdoctlist.length
          })
        }else{
          that.setData({
            is_more : false,
            is_loadnull: true
          })
          wx.showToast({
            title: "暂无数据",
            image: '/icon/icon_tips.svg',
            duration: 2000
          })
        }
      } else {
        wx.showToast({
          title: resData.errmsg,
          image: '/icon/icon_tips.svg',
          duration: 2000
        })
      }

    }
  })
}
function insertRegister(postData, appInstance){
  postData.openid = wx.getStorageSync('openid');
  wx.showLoading({
    title: '预约中...',
  })
  wx.request({
    url: 'https://xcx.yzw0525.com/api/insertRegister',
    data: postData,
    method: "POST",
    header: {
      'content-type': 'application/x-www-form-urlencoded',
      'Verification': appInstance.globalData.VERIFICATION,
    },
    success: function (res) {
      // wx.hideLoading()
      console.log(res.data)
      var resData = res.data;
      if (resData.errCode == 200) {
        wx.showToast({
          title: "提交成功...",
          icon: 'waiting',
          duration: 2000
        })
        setTimeout(function () {
          wx.redirectTo({
            url: '/pages/index/index'
          })
        }, 2000)
      } else {
        wx.showToast({
          title: resData.errmsg,
          image: '/icon/icon_tips.svg',
          duration: 2000
        })
      }

    }
  })
}
module.exports.onLoadDoctList = onLoadDoctList
exports.insertRegister = insertRegister
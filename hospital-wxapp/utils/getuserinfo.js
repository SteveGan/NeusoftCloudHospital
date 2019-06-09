function getuserinfo(code, res, that){
  wx.request({
    url: 'https://xcx.yzw0525.com/api/GetUserInfo',
    data: {
      code: code,
      userinfo: res,
    },
    method: "POST",
    header: {
      'content-type': 'application/x-www-form-urlencoded',
      'Verification': that.globalData.VERIFICATION,
    },
    success: function (res) {
      var resData = res.data;
      if (resData.errCode==200){
        that.globalData.openid = resData.openid
        that.globalData.userInfo = resData.userinfo
        wx.setStorageSync('openid', resData.openid)
        wx.setStorageSync('userinfo', resData.userinfo)
        
      }else{
        wx.showToast({
          title: resData.errmsg,
          icon: 'waiting',
          duration: 2000
        })
      }
    }
  })
}
function getYeboMember(that, appInstance,openid) {
  wx.request({
    url: 'https://xcx.yzw0525.com/api/getYeboMember',
    data: {
      openid: openid,
    },
    method: "POST",
    header: {
      'content-type': 'application/x-www-form-urlencoded',
      'Verification': appInstance.globalData.VERIFICATION,
    },
    success: function (res) {
      var resData = res.data;
      if (resData.errCode == 200) {
        appInstance.globalData.userInfo = resData.userinfo
        try{
          wx.setStorageSync('userinfo', resData.userinfo)
        }catch(e){
          console.log(e)
        }
        
        that.setData({
          userinfo: resData.userinfo,
          authSetting:true
        })
      } else {
        wx.showToast({
          title: resData.errmsg,
          icon: 'waiting',
          duration: 2000
        })
      }
    }
  })
}
function getcomment(that, appInstance){
  wx.showLoading({
    title: '加载中',
  })
  wx.request({
    url: 'https://xcx.yzw0525.com/api/getcomment',
    data: {
      offset: that.data.offset,
      limit: appInstance.globalData.limit
    },
    method: "POST",
    header: {
      'content-type': 'application/x-www-form-urlencoded',
      'Verification': appInstance.globalData.VERIFICATION,
    },
    success: function (res) {
     // console.log(res.data)
      var resData = res.data;
      if (resData.errCode == 200) {
        if (resData.item.length > 0) {
          var newcommentlist = resData.item;
          var commentlist = that.data.commentlist;
          if (commentlist != null) {
            that.data.commentlist = newcommentlist.concat(that.data.commentlist)
          } else {
            that.data.commentlist = newcommentlist
          }
          that.setData({
            commentlist: that.data.commentlist,
            offset: that.data.offset + newcommentlist.length
          })
          
        } else {
          that.setData({
            is_more: false,
            is_loadnull:true
          })
        }
        wx.hideLoading()
      } else {
        wx.showToast({
          title: resData.errmsg,
          icon: 'waiting',
          duration: 2000
        })
      }

    }
  })
}
module.exports.getuserinfo = getuserinfo
exports.getYeboMember = getYeboMember
exports.getcomment = getcomment

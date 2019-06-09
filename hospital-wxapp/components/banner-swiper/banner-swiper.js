// components/banner-swiper/banner-swiper.js
Component({
  lifetimes: {
    attached() {
      this.showBanner();
    }
  },
  options: {
    addGlobalClass: true
  },
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    cardCur: 0,
    banners: [], //首页广告位滚动条幅
  },

  /**
   * 组件的方法列表
   */
  methods: {

    showBanner: function() {
      var that = this;
      wx.request({
        url: 'https://intp.5io2.com/cache/banner.json',
        method: 'GET',
        dataType: 'json',
        success: function(res) {
          console.log(res)
          that.setData({
            banners: res.data
          });
        }
      });
    },
    bannerTo(e) {
      wx.navigateTo({
        url: e.currentTarget.dataset.url,
      })
    },
    cardSwiper(e) { // 处理高光卡片滑动事件
      this.setData({
        cardCur: e.detail.current
      })
    }
  }
})
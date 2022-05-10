// components/library-borrow-record-card/library-borrow-record-card.js
Component({
  options: {
    addGlobalClass: true
  },
  lifetimes: {
    attached() {
      this.loadBorrowRecord()
    }
  },
  /**
   * 组件的属性列表
   */
  properties: {
    showDetails: {
      type: Boolean,
      value: false
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    userid: '',
    passwd: '',
    borrowRecordList: [],
    isLoading: false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    showDetailRecord: function () {
      console.log(this.properties.showDetails)
      this.setData({
        showDetails: this.properties.showDetails ? false:true
      });
    },
    loadBorrowRecord: function () {
      this.setData({
        isLoading: true
      })
      var $this = this;
      let stuId = wx.getStorageSync('stuID');
      $this.setData({
        userid: stuId,
        passwd: stuId.substring(stuId.length - 6)
      });
      // wx.showLoading({
      //   title: '正在加载...',
      // })
      wx.request({
        url: 'https://neuvwo.com/mini/api/getCurrentBorrow',
        data: {
          'stuID': $this.data.userid,
          'libPass': $this.data.passwd,
        },
        method: 'POST',
        dataType: 'json',
        success: function (res) {
          console.log(res.data);
          $this.setData({
            'borrowRecordList': res.data.data
          });
        },
        complete: function (res) {
          // wx.hideLoading();
          $this.setData({
            isLoading: false
          })
        }
      })
    }
  }
})

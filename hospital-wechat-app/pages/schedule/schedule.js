// pages/schedule/schedule.js

Page({

  data: {
    departments: [],
    showedDepartments: [],
    departmentId: 2,
    departmentName: "神经内科",
    selectedDate: "",

    show: false,
    show2: false,

    doctors: [],

    // 日期选择
    currentDate: new Date().getTime(),
    formatter(type, value) {
      if (type === 'year') {
        return `${value}年`;
      } else if (type === 'month') {
        return `${value}月`;
      }
      return value;
    }
  },

  search() {
    var that = this
    wx.request({
      url: "http://www.stevegan.com:1923/basicinfo/arrangements/" + this.data.departmentId + "?startDate=" + that.data.selectedDate+ "&endDate=" + this.data.selectedDate,
      method: 'GET',
      dataType: 'json',
      success: function (res) {
        console.log(res.data.data);
        const data = res.data.data[that.data.selectedDate];
        that.setData({
          doctors: data
        })
      }, complete() {
        wx.hideLoading();
      }
    })
  },

  onConfirm(event) {
    const { picker, value, index } = event.detail;

    this.setData({
      departmentName: value,
      show: false,
      departmentId: index + 1
    })
  },

  onInput(event) {
    var date = new Date(event.detail);
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
    var res = (Y + M + D);
    this.setData({
      currentDate: event.detail,
      selectedDate: res
    });
  },

  onConfirm2(event) {
    this.setData({
      show2: false
    })
  },

  onCancel(){
    this.setData({
      show: false,
      show2: false,
    })
  },

  listDepartmentss() {
    var that = this;
    // 请求查询用户历史病历信息
    wx.request({
      url: "http://www.stevegan.com:1923/basicinfo/departments",
      method: 'GET',
      dataType: 'json',
      success: function (res) {
        const data = res.data.data;
        var tempArr = [];
        for (var i = 0; i < data.length; i++){
          tempArr.push(data[i].name);
        }

        that.setData({
          departments: data,
          showedDepartments: tempArr
        })
      }, complete() {
        wx.hideLoading();
      }
    })
  },

  showDatePicker() {
    this.setData({
      show2: true
    })
  },

  showPicker() {
    this.setData({
      show: true
    })
  },

  onLoad: function (options) {
    this.listDepartmentss()
  },

  
  onShareAppMessage: function () {

  }
})
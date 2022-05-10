// components/top-bar/top-bar.js
const app = getApp()
Component({
  options: {
    addGlobalClass: true
  },
  /**
   * 组件的属性列表
   */
  properties: {
    hasBack: {
      type: Boolean,
      value: false
    },
    isTransparent: {
      type: Boolean,
      value: false
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar
  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})

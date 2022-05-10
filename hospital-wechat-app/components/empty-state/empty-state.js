// components/empty-state-card/empty-state-card.js
Component({
  options: {
    addGlobalClass: true
  },
  /**
   * 组件的属性列表
   */
  properties: {
    title: {
      type: String,
      value: '没有内容'
    },
    description: {
      type: String,
      value: ''
    },
    icon: {
      type: String,
      value: 'info'
    },
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})

// pages/index/index.js
Page({
  /**
   * 页面的初始数据
   */
  data: {
    typeList : [
      {
        name : '第一项',
        opened : false,
        id : 1,
        options : [
          { name: 'No.1', id: 1 },
          { name: 'No.2', id: 2 },
          { name: 'No.3', id: 3 },
          { name: 'No.4',id : 4 },
        ]
      },
      {
        name: '第二项',
        opened: false,
        id: 2,
        options: [
          { name: 'No.1', id: 1 },
          { name: 'No.2', id: 2 },
          { name: 'No.3', id: 3 },
          { name: 'No.4', id: 4 },
        ]
      }
    ],
    flag : true,
    text: ""
  },
  handleClick(e){
    let dataset =  e.currentTarget.dataset
    let index = dataset.index
    let target = this.data.typeList[index]
    this.setData({
      [`typeList[${index}].opened`] : !target.opened
    })
  },
  showAction(){
    const _this = this
    wx.showActionSheet({
      itemList : ['填写信息','查看文档'],
      itemColor : '#000',
      success(res){
        _this.selectTurn(res.tapIndex)
      }
    })
  },
  selectTurn(index){
    let url = null
    if(index == 0){ //填写信息
      url = '../form/form'
    }else if(index == 1){//查看文档
      url = '../contract/contract'
    }
    wx.navigateTo({
      url,
    })

  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let _this = this
    return 
    wx.request({
      url: 'https://yip.mynatapp.cc/allType',
      success(res){
        if(res.statusCode == 200){
          _this.setData({
            typeSet : res.data
          })
        }     
      },
      fail(err){
        wx.showToast({
          title: '',
          duration : 1500
        })
      }
    })
    return 

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
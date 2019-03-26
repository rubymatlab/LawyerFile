// pages/contract/contract.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    content : '',
  },
  onloadFile(e){
    const _this = this
    return 
    wx.downloadFile({
      url: 'https://yip.mynatapp.cc/contract/download.docx',
      header : {},
      success(res){
        let tempFilePath = res.tempFilePath
        console.log(res)
        wx.openDocument({
          filePath: tempFilePath,
          fileType: 'docx',
          success(res) {
            console.log(res, 'success')
          },
          fail(err) {
            wx.showToast({
              title:"出错了",
            })
          }
        })
      },
      fail(err){
        console.log(err)
      }
    })
  },
  updateData(content){
    let reg = /[\n]+/gm
    content = content.replace(reg, '\n')
    let reg1 = /\$\{(.+)\}/gm
    content = content.replace(reg1, '_')
    this.setData({
      content,
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let id = options.id
    let _this = this


    let content = `民事合同

甲方： __________\${甲方}_________ （姓名、性别、年龄、职业或者职务、住址） 

乙方： _________\${乙方}__________ （姓名、性别、年龄、职业或者职务、住址）

甲乙双方依据 _____________ （法律、行政法规的名称），经过平等协商，签订本合同。 
第一条  合同标的的内容（例如，租赁房屋合同，则写明甲方出租 _________ 房产的基本情况； 如果是律师代理诉讼合同， 则写明代理诉讼的案件名称） 
第二条  双方的权利义务 
第三条  质量、数量等内容 
第四条  价款或者酬金 
第五条  违约责任
第六条 ……（双方约定的其他内容） 
第七条 合同生效的时间及条件（可以是自双方签字之日 起生效，也可以约定另外的生效时间）
第八条 本合同一式 ___ 份，当事人各执 ___ 份。 
甲方： ___\${甲方}_____ （签名或者盖章）
乙方： ____\${乙方}____ （签名或者盖章）
_________ 年 _______月 ______ 日
说明
合同是当事人之间达成的旨在明确民事权利义务的协议。合同是一种很重要的民事法律行为。所谓民事法律行为，是指公民或者法人设立、变更、终止民事权利和民事义务的合法行为。例如公民之间订立房屋租赁合同行为，就是一种民事法律行为，依据合同，在租赁人和承租人之间产生了租赁房屋合同法律关系。民事法律行为从成立时起具有法律约束力。行为人非依法律规定或者取得对方同意，不得擅自变更或者解除。`
    this.updateData(content)
    
    return 
    wx.request({
      url: 'https://yip.mynatapp.cc/contract/seecontent/' + id,
      data: {},
      method: 'GET',
      success(res) {
        if (res.statusCode == 200) {
          _this.updateData(res.data.content)
        }
      },
      fail(err) {
        console.log(err.data.msg)
      }
    })
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
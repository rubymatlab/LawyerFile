<template>

	<view class="uni-padding-wrap">
		<form @submit="formSubmit" @reset="formReset">
			<view v-for="(fieldvalue,index) in fieldArray" :key="index">
				<view class="uni-title">{{fieldvalue}}</view>
				<view class="uni-list">
					<view class="uni-list-cell">
						<input class="uni-input" :name="fieldvalue" placeholder="请填写" />
					</view>
				</view>
			</view>
			<view v-if="fieldArray!==null && fieldArray.length > 0" class="btn-row">
				<button class="primary" formType="submit" type="primary">提交预览</button>
			</view>
			<view v-if="fieldArray!==null && fieldArray.length > 0" class="btn-row">
				<button class="primary" formType="reset" type="primary">清空</button>
			</view>
		</form>
		<view v-if="fieldArray===null || fieldArray.length === 0">
			<text class="title">模板没有编辑项，请在word文档插入输入域</text>
		</view>
	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	
	export default {
		data() {
			return {
				fileid: null,
				fieldArray: []
			}
		},
		computed: mapState(['accessToken','userName']),
		onLoad: function(options) {
			this.fileid = options.id;
			uni.showLoading({
				title: '加载中'
			});
			uni.request({
				url: this.GLOBAL + '/rest/basFileController/getContent/' + this.fileid,
				method: 'GET',
				data: {},
				header: {
					'content-type': 'application/json',
					'X-Auth-Token': this.accessToken
				},
				success: (res) => {
					uni.hideLoading();
					if (res.statusCode === 200) {
						this.fieldArray = res.data.data;
					}
				}
			});
		},
		methods: {
			formSubmit: function(e) {
				//console.log(JSON.stringify(e.detail.value));
				uni.showLoading({
					title: '提交中'
				});
				uni.request({
					url: this.GLOBAL + '/rest/basFileController/postFileUrl',
					method: 'POST',
					data: {
						detailvalue:e.detail.value,
						id:this.fileid,
						createby:this.userName
					},
					header: {
						'content-type': 'application/json',
						'X-Auth-Token': this.accessToken
					},
					success: (res) => {
						uni.hideLoading();
						if (res.statusCode === 200) {
							let vardata = res.data.data;
							uni.downloadFile({
								url: this.GLOBAL + '/'+vardata,
								success: (res) => {
									uni.hideLoading();
									wx.openDocument({
									  filePath: res.tempFilePath ,
									  success: function (res) {
										uni.showToast({
											icon: 'none',
											title: "打开文档成功",
										});
									  }
									});
								},
								fail: (err) => {
									uni.showToast({
										icon: 'none',
										title: "错误消息:"+err,
									});
								}
							})
						}
					}
				});
			},
			formReset: function(e) {
				this.chosen = ''
			}

		}
	}
</script>

<style>
	@import url("../../components/uParse/src/wxParse.css");

	.title {
		color: #8f8f94;
		margin-top: 50upx;
		font-size: 50upx;
	}
</style>

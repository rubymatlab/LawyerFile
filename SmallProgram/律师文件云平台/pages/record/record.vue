<template>
	<view>
		<view v-if="hasLogin" class="uni-padding-wrap uni-common-mt">
			<view class="text" v-for="(vwdetail,index) in data" :key="index">
				<image class="uni-record-image" hover-class="uni-product-hover" :src="imgFileUrl" @click="tapDownload(vwdetail)">
				</image>
				<view style="display: flex;flex-direction: column;">
					<text>{{vwdetail.bfName}}</text><text>{{vwdetail.createDate}}</text>
				</view>
				<view class="tag-view" hover-class="uni-product-hover" @click="tapDownloadDetail(vwdetail)">
					<uni-tag text="下载" type="primary" :circle="true"></uni-tag>
				</view>
				<switch v-if="vwdetail.bfdStore==='Y'" checked @change="switchChange($event,vwdetail)" />
				<switch v-else @change="switchChange($event,vwdetail)" />
			</view>
			<view class="uni-loadmore" v-if="showLoadMore">{{loadMoreText}}</view>
		</view>
		<view v-if="!hasLogin" class="hello">
			<view class="title">
				<text>您好 游客。</text>
				<text>在 “我的” 中点击 “登录” 可以 “登录您的账户”。</text>
			</view>
		</view>
	</view>
</template>
<script>
	import uniTag from '@/components/uni-tag.vue';
	import {
		mapState
	} from 'vuex'


	export default {
		components: {
			uniTag
		},
		data() {
			return {
				data: [],
				loadMoreText: "加载中...",
				showLoadMore: false,
				max: 0,
				nodata: false,
				imgFileUrl: '../../static/img/word.png'
			}
		},
		computed: mapState(['accessToken', 'userName', 'hasLogin']),
		onLoad() {
			//this.initData();
		},
		onShow() {
			if (this.hasLogin) {
				this.initData();
			}
		},
		onReachBottom() {
			//console.log("onReachBottom");
			this.showLoadMore = true;
			/* setTimeout(() => { */
				this.setDate();
			/* }, 300); */
		},
		onPullDownRefresh() {

			console.log('onPullDownRefresh');
			this.initData();
		},
		methods: {
			initData() {
				/* setTimeout(() => { */
					this.max = 0;
					this.data = [];
					this.max += 10;
					//数据初始化
					this.nodata = false;
					this.loadMoreText = "";
					this.requestData(this.max / 10);
					uni.stopPullDownRefresh();
				/* }, 300); */
			},
			setDate() {
				this.max += 10;
				this.requestData(this.max / 10);
				//this.data = this.data.concat(data);
			},
			requestData(pageNo) {
				let pageSize = 10;
				if (!this.nodata) {
					uni.showLoading({
						title: '加载中'
					});
					uni.request({
						url: this.GLOBAL + '/rest/vwBasFileDetailController/listdetail',
						method: 'POST',
						data: {
							pageNo: pageNo,
							pageSize: pageSize,
							userName: this.userName
						},
						header: {
							'content-type': 'application/json',
							'X-Auth-Token': this.accessToken
						},
						success: (res) => {
							if (res.statusCode === 200) {
								let json = JSON.stringify(res.data);
								let jsonObj = JSON.parse(json);
								for (var i = 0; i < jsonObj.data.length; i++) {
									this.data.push(jsonObj.data[i]);
								}
								if (jsonObj.data === null || jsonObj.data.length < 10) {
									this.loadMoreText = "没有更多数据了!"
									this.nodata = true;
								}
								uni.hideLoading();
							} else {
								uni.showToast({
									icon: 'none',
									title: res.data,
								});
							}
						}
					});
				}
			},
			tapDownload(item) {
				uni.showLoading({
					title: '下载中'
				});
				uni.downloadFile({
					url: this.GLOBAL + '/' + item.bfPath,
					success: (res) => {
						uni.hideLoading();
						wx.openDocument({
							filePath: res.tempFilePath,
							success: function(res) {
								/* uni.showToast({
									icon: 'none',
									title: "打开文档成功",
								}); */
							}
						});
					}
				})
			},
			tapDownloadDetail(item) {
				let id=item.id;
				uni.showLoading({
					title: '下载中'
				});
				uni.request({
					url: this.GLOBAL + '/rest/basFileController/postFileUrlDetail',
					method: 'POST',
					data: {
						id: id
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
								url: this.GLOBAL + '/' + vardata,
								success: (res) => {
									uni.hideLoading();
									wx.openDocument({
										filePath: res.tempFilePath,
										success: function(res) {
											/* uni.showToast({
												icon: 'none',
												title: "打开文档成功",
											}); */
										}
									});
								}
							}) 
						}
					}
				})
			},
			switchChange(e,item) {
				let id = item.id;
				if (e.detail.value)
					this.requestPutData(id, 'Y', "收藏成功");
				else
					this.requestPutData(id, 'N', "取消收藏");
			},
			requestPutData(id, bfdstore, content) {
				uni.request({
					url: this.GLOBAL + '/rest/basFileDetailController/' + id,
					method: 'PUT',
					data: {
						id: id,
						bfdStore: bfdstore
					},
					header: {
						'content-type': 'application/json',
						'X-Auth-Token': this.accessToken
					},
					success: (res) => {
						if (res.statusCode === 200) {
							uni.hideLoading();
							uni.showToast({
								icon: 'none',
								title: content,
							});
						} else {
							uni.showToast({
								icon: 'none',
								title: res.data,
							});
						}
					}
				});
			}
		}
	}
</script>

<style>
	.hello {
		display: flex;
		flex: 1;
		flex-direction: column;
	}

	.title {
		color: #8f8f94;
		margin-top: 50upx;
		font-size: 50upx;
	}

	.text {
		margin: 16upx 0;
		width: 100%;
		background-color: #fff;
		height: 120upx;
		line-height: 120upx;
		display: flex;
		text-align: left;
		vertical-align: middle;
		color: #555;
		font-size: 20upx;
		border-radius: 8upx;
		box-shadow: 2px 2px 5px #333333;
	}

	.uni-record-image {
		height: 110upx;
		width: 110upx;
	}

	.tag-view {
		width: 20%;
		height: 50upx;
		margin-top: 30upx;
		margin-left: 2upx;
	}
</style>

<template>

	<view class="uni-tab-bar">
		<!-- <view v-if="hasLogin" class="title">
				您好 {{userName}}，您已成功登录。
			</view> -->
		<scroll-view v-if="hasLogin" id="tab-bar" class="uni-swiper-tab" scroll-x :scroll-left="scrollLeft">
			<view v-for="(tab,index) in tabBars" :key="tab.id" :class="['swiper-tab-list',tabIndex==index ? 'active' : '']" :id="tab.id"
			 :data-current="index" @click="tapTab(index)">{{tab.name}}</view>
		</scroll-view>

		<view v-if="hasLogin" class="uni-product-list">
			<view class="uni-product" v-for="(product,index) in productList" :key="index">
				<view class="uni-list-cell-navigate_number" hover-class="uni-product-hover">
					<image v-if="renderImage" class="uni-product-image" :src="product.image" :id="product.id" @click="tapImage"></image>
					<uni-badge :text="product.count" type="danger"></uni-badge>
				</view>
				<view class="uni-product-title">{{product.title}}</view>
			</view>
			<cover-image v-if="productListFile.length>0" style="width: 50upx; height: 50upx;" src="../../static/img/arrow.png"></cover-image>
			<view class="uni-product" v-for="(product,index) in productListFile" :key="index">
				<view class="image-view" hover-class="uni-product-hover">
					<image v-if="renderImage" class="uni-product-image" :src="product.image" :id="product.id" @click="tapDownload"></image>
				</view>
				<view hover-class="uni-product-hover" @click="tapRedict(product.id)">
					<uni-tag :text="product.title" type="primary" :circle="true"></uni-tag>
				</view>
			</view>
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
	import uniBadge from "@/components/uni-badge.vue";
	import {
		mapState
	} from 'vuex'
	export default {
		components: {
			uniTag,
			uniBadge
		},
		data() {
			return {
				loadingText: {
					contentdown: "上拉显示更多",
					contentrefresh: "正在加载...",
					contentnomore: "没有更多数据了"
				},
				scrollLeft: 0,
				isClickChange: false,
				tabIndex: 0,
				tabBars: [],
				/*存储的数据*/
				jsonFileArray: [],
				jsonfolderData: [],
				jsonWordData: [],
				/*目录*/
				title: 'product-list',
				productList: [],
				productListFile: [],
				renderImage: true,
				imgfolderUrl: '../../static/img/folder.png',
				/*文件*/
				imgFileUrl: '../../static/img/word.png',
				/*箭头*/
				imagArrow: '../../static/img/arrow.png',
				isOnShow: false
			}
		},
		computed: mapState(['forcedLogin', 'hasLogin', 'userName', 'accessToken']),
		onLoad() {
			if (!this.hasLogin) {
				uni.showModal({
					title: '未登录',
					content: '您未登录，需要登录后才能继续',
					showCancel: !this.forcedLogin,
					success: (res) => {
						if (res.confirm) {
							if (this.forcedLogin) {
								uni.reLaunch({
									url: '../login/login'
								});
							} else {
								uni.navigateTo({
									url: '../login/login'
								});
							}
						}
					}
				});
			}
		},
		onShow() {
			if (this.hasLogin && !this.isOnShow) {
				uni.request({
					url: this.GLOBAL + '/rest/basFileController/list/1/1000',
					method: 'GET',
					data: {},
					header: {
						'content-type': 'application/json',
						'X-Auth-Token': this.accessToken
					},
					success: (res) => {
						if (res.statusCode === 200) {
							let json = JSON.stringify(res.data);
							let jsonObj = JSON.parse(json);
							this.jsonFileArray = [];
							this.tabBars = [];
							//初始化数据
							for (var i = 0; i < jsonObj.data.length; i++) {
								this.jsonFileArray.push(jsonObj.data[i]);
								if (!jsonObj.data[i].bfParentid) {
									this.tabBars.push({
										name: jsonObj.data[i].bfName,
										id: jsonObj.data[i].id
									})
								}
							}
							this.loadIndex(0);
							this.loadData();
							this.productListFile = [];
						} else {
							uni.showToast({
								icon: 'none',
								title: res.data,
							});
						}
					}
				});
				this.isOnShow = true;
			}

		},
		methods: {
			/*顶部导航*/
			getElSize(id) { 
				return new Promise((res, rej) => {
					uni.createSelectorQuery().select('#' + id).fields({
						size: true,
						scrollOffset: true
					}, (data) => {
						res(data);
					}).exec();
				});
			},
			async tapTab(index) { 
				if (this.tabIndex === index) {
					return false;
				} else {
					let tabBar = await this.getElSize("tab-bar"),
						tabBarScrollLeft = tabBar.scrollLeft; //点击的时候记录并设置scrollLeft
					this.scrollLeft = tabBarScrollLeft;
					this.isClickChange = true;
					this.tabIndex = index;

					this.loadIndex(index);
					this.loadData();
					this.productListFile = [];
				}
			},
			randomfn() {
				let ary = [];
				for (let i = 0, length = this.tabBars.length; i < length; i++) {
					let aryItem = {
						loadingType: 0,
						data: []
					};
					for (let j = 1; j <= 10; j++) {
						aryItem.data.push(this['data' + Math.floor(Math.random() * 5)]);
					}
					ary.push(aryItem);
				}
				return ary;
			},
			/*文件夹*/
			loadIndex(index) {
				/*如果父节点等于当前ID，则这把文件夹添加进来*/
				this.jsonfolderData = [];
				this.jsonFileArray.forEach(item => {
					if (item.bfParentid === this.tabBars[index].id)
						this.jsonfolderData.push({
							image: this.imgfolderUrl,
							title: item.bfName,
							id: item.id,
							count: 0
						});
				});
				//统计每个文件夹下的文件
				this.jsonfolderData.forEach(item => {
					let count = 0;
					this.jsonFileArray.forEach(item1 => {
						if (item1.bfParentid == item.id)
							count++;
					});
					item.count=count;
				})

			},
			loadData(action = 'refresh') {
				const data = this.jsonfolderData;
				if (action === 'refresh') {
					this.productList = [];
				}
				data.forEach(item => {
					this.productList.push(item);
				});

				if (data.length === 0)
					uni.showToast({
						icon: 'none',
						title: "没有文件夹",
					});
			},
			/*word文件*/
			loadWordIndex(id) {
				/*如果文件的父ID等于文件夹ID，则这把文件添加进来*/
				this.jsonWordData = [];
				this.jsonFileArray.forEach(item => {
					if (item.bfParentid === id)
						this.jsonWordData.push({
							image: this.imgFileUrl,
							title: item.bfName,
							id: item.id
						});
				});
			},
			loadDataFile(action = 'refresh') {
				const data = this.jsonWordData;
				if (action === 'refresh') {
					this.productListFile = [];
				}
				data.forEach(item => {
					this.productListFile.push(item);
				});

				if (data.length === 0)
					uni.showToast({
						icon: 'none',
						title: "没有文件",
					});
			},
			tapImage(e) {
				this.loadWordIndex(e.currentTarget.id);
				this.loadDataFile();
			},
			tapDownload(e) {
				uni.showLoading({
					title: '下载中'
				});
				this.jsonFileArray.forEach(item => {
					if (item.id === e.currentTarget.id)
						uni.downloadFile({
							url: this.GLOBAL + '/' + item.bfPath,
							success: (res) => {
								uni.hideLoading();
								wx.openDocument({
									filePath: res.tempFilePath,
									success: function(res) {
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
									title: "错误消息:" + err,
								});
							}
						})
				});
			},
			tapRedict(currentId) {
				if (!this.forcedLogin) {
					uni.navigateTo({
						url: '../preview/preview?id=' + currentId,
					});
				}
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

	/* #ifdef MP-ALIPAY */
	.swiper-tab-list {
		display: inline-block;
	}

	/* #endif */
	.uni-list-cell-navigate_number {
		height: 160upx;
		width: 190upx;
		margin: 12upx 0;
		position: relative;
		display: flex;
		align-items: flex-start;
	}
</style>

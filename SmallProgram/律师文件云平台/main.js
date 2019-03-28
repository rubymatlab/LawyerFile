import Vue from 'vue'
import App from './App'

import store from './store'

Vue.config.productionTip = false

Vue.prototype.$store = store
/*添加全局变量*/
Vue.prototype.GLOBAL='https://zhyongfeng.mynatapp.cc/lawyerfile';

/* Vue.prototype.accessToken=''; */

App.mpType = 'app'

const app = new Vue({
    store,
    ...App
})
app.$mount()

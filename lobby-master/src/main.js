import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './global.less'
import './font.css'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import lang from 'element-ui/lib/locale/lang/en'
import locale from 'element-ui/lib/locale'
import { dateFilter } from '@/utils'
import UserHead from '@/components/userHead'
import PostStatus from '@/components/postStatus'
import LoadingState from '@/components/loadingState'
import DisableBtn from '@/components/disableBtn'
import UploadImg from '@/components/uploadImg'
import '@wangeditor/editor/dist/css/style.css';
import '@/wangeditor'
import { badwordsList } from '@/utils/badwords'
import Filter from "bad-words";

const filter = new Filter();
filter.addWords(...badwordsList)
Vue.prototype.$badwordsFilter = filter;

locale.use(lang)
Vue.component("UserHead", UserHead)
Vue.component("PostStatus", PostStatus)
Vue.component("LoadingState", LoadingState)
Vue.component("DisableBtn", DisableBtn)
Vue.component("UploadImg", UploadImg)

Vue.config.productionTip = false

Vue.use(ElementUI);

Vue.filter('dateFilter', dateFilter)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

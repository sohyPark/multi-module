// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import axios from 'axios'
import VueCookie from 'vue-cookie';
import SortedTablePlugin from "vue-sorted-table";


Vue.config.productionTip = false
Vue.use(BootstrapVue);
Vue.use(VueCookie);
Vue.use(SortedTablePlugin);
Vue.prototype.$axios = axios;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})


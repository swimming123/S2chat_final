import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import jQuery from 'jquery'
import 'turn.js'

// jQuery를 전역 변수로 설정
window.$ = window.jQuery = jQuery

createApp(App).use(store).use(router).mount('#app');


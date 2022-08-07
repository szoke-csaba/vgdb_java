import { createApp } from 'vue'
import App from '@/App'
import router from '@/router/router'
import store from '@/store'

import '@/assets/scss/main.scss'

import 'bootstrap'
import '@/assets/js/main'
import { FontAwesomeIcon } from '@/assets/js/font-awesome'

createApp(App)
    .use(router)
    .use(store)
    .component('font-awesome-icon', FontAwesomeIcon)
    .mount('#app')

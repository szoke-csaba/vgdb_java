import { createApp } from 'vue'
import App from '@/App'
import { createHead } from "@vueuse/head"
import router from '@/router/router'
import store from '@/store'
import { FontAwesomeIcon } from '@/assets/js/font-awesome'
import '@/assets/js/main'
import '@/assets/scss/main.scss'

const head = createHead()

createApp(App)
    .use(router)
    .use(store)
    .use(head)
    .component('font-awesome-icon', FontAwesomeIcon)
    .mount('#app')

import {
    createRouter, createWebHistory
} from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'home-page',
        component: () => import('./components/HomePage')
    },
    {
        path: '/games',
        name: 'games',
        component: () => import('./components/GameList')
    },
    {
        path: '/games/:id',
        name: 'update-game',
        component: () => import('./components/UpdateGame')
    },
    {
        path: '/games/add',
        name: 'add-game',
        component: () => import('./components/AddGame')
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router

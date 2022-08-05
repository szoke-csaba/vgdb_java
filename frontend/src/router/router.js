import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import('@/views/HomePage')
    },
    {
        path: '/games',
        name: 'games',
        component: () => import('@/views/GameList')
    },
    {
        path: '/games/:id',
        name: 'update-game',
        component: () => import('@/views/UpdateGame')
    },
    {
        path: '/games/add',
        name: 'add-game',
        component: () => import('@/views/AddGame')
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router

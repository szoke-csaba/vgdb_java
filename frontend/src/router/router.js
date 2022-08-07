import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import('@/views/Home')
    },
    {
        path: '/games',
        name: 'games',
        component: () => import('@/views/game/GameList')
    },
    {
        path: '/games/:id',
        name: 'update-game',
        component: () => import('@/views/game/UpdateGame')
    },
    {
        path: '/games/add',
        name: 'add-game',
        component: () => import('@/views/game/AddGame')
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/auth/Login')
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('@/views/auth/Register')
    },
    {
        path: '/profile',
        name: 'profile',
        component: () => import('@/views/auth/Profile')
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const publicPages = ['/', '/login', '/register', '/games']
    const authRequired = !publicPages.includes(to.path)
    const loggedIn = localStorage.getItem('user')

    if (authRequired && !loggedIn) {
        next({ name: 'login' })
    } else {
        next()
    }
})

export default router

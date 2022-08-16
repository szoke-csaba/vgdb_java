import {createRouter, createWebHistory} from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import('@/views/Home')
    },
    {
        path: '/games',
        name: 'games',
        component: () => import('@/views/GameList')
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/auth/Login'),
        meta: { authToHome: true }
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('@/views/auth/Register'),
        meta: { authToHome: true }
    },
    {
        path: '/profile',
        name: 'profile',
        component: () => import('@/views/auth/Profile'),
        meta: { requiresAuth: true }
    },
    {
        path: '/admin',
        name: 'admin-home',
        component: () => import('@/views/admin/Home'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/games',
        name: 'admin-games',
        component: () => import('@/views/admin/game/GameList'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/games/:id',
        name: 'update-game',
        component: () => import('@/views/admin/game/UpdateGame'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/games/add',
        name: 'add-game',
        component: () => import('@/views/admin/game/AddGame'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'not-found',
        component: () => import('@/views/NotFound')
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const user = JSON.parse(localStorage.getItem('user'))

    if (to.meta.requiresAuth && !user) {
        next({ name: 'login' })
    } else if (to.meta.authToHome && user) {
        next({ name: 'home' })
    } else if (to.meta.requiresAdmin && (!user || user.role !== 'ROLE_ADMIN')) {
        next({ name: 'not-found' })
    } else {
        next()
    }
})

export default router

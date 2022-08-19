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
        component: () => import('@/views/admin/game/AddAndUpdateGame'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/games/add',
        name: 'add-game',
        component: () => import('@/views/admin/game/AddAndUpdateGame'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/tags',
        name: 'admin-tags',
        component: () => import('@/views/admin/tag/TagList'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/tags/:id',
        name: 'update-tag',
        component: () => import('@/views/admin/tag/UpdateTag'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/tags/add',
        name: 'add-tag',
        component: () => import('@/views/admin/tag/AddTag'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/genres',
        name: 'admin-genres',
        component: () => import('@/views/admin/genre/GenreList'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/genres/:id',
        name: 'update-genre',
        component: () => import('@/views/admin/genre/UpdateGenre'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/genres/add',
        name: 'add-genre',
        component: () => import('@/views/admin/genre/AddGenre'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/platforms',
        name: 'admin-platforms',
        component: () => import('@/views/admin/platform/PlatformList'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/platforms/:id',
        name: 'update-platform',
        component: () => import('@/views/admin/platform/UpdatePlatform'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/platforms/add',
        name: 'add-platform',
        component: () => import('@/views/admin/platform/AddPlatform'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/developers',
        name: 'admin-developers',
        component: () => import('@/views/admin/developer/DeveloperList'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/developers/:id',
        name: 'update-developer',
        component: () => import('@/views/admin/developer/UpdateDeveloper'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/developers/add',
        name: 'add-developer',
        component: () => import('@/views/admin/developer/AddDeveloper'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/publishers',
        name: 'admin-publishers',
        component: () => import('@/views/admin/publisher/PublisherList'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/publishers/:id',
        name: 'update-publisher',
        component: () => import('@/views/admin/publisher/UpdatePublisher'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/publishers/add',
        name: 'add-publisher',
        component: () => import('@/views/admin/publisher/AddPublisher'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/users',
        name: 'admin-users',
        component: () => import('@/views/admin/user/UserList'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/users/:id',
        name: 'update-user',
        component: () => import('@/views/admin/user/UpdateUser'),
        meta: { requiresAdmin: true, layout: 'admin' }
    },
    {
        path: '/admin/users/add',
        name: 'add-user',
        component: () => import('@/views/admin/user/AddUser'),
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

import AuthService from '@/services/auth'

const user = JSON.parse(localStorage.getItem('user'))
const initialState = user
    ? {
        user: user
    }
    : {
        user: null
    }

export const Auth = {
    namespaced: true,
    state: initialState,
    actions: {
        login({ commit }, user) {
            return AuthService.login(user).then(
                user => {
                    commit('loginSuccess', user)
                    return Promise.resolve(user)
                },
                error => {
                    return Promise.reject(error)
                }
            )
        },
        logout({ commit }) {
            AuthService.logout()
            commit('logout')
        },
        // eslint-disable-next-line no-unused-vars
        register({ commit }, user) {
            return AuthService.register(user).then(
                response => {
                    return Promise.resolve(response.data)
                },
                error => {
                    return Promise.reject(error)
                }
            )
        }
    },
    mutations: {
        loginSuccess(state, user) {
            state.user = user
        },
        logout(state) {
            state.user = null
        },
    },
    getters: {
        isAdmin (state) {
            return state.user && state.user.role === 'ROLE_ADMIN'
        }
    }
}

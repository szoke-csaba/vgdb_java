import http from '@/http-common'
import authHeader from '@/services/auth-header'

class Game {
    getAll(page, pageSize) {
        return http.get('/games', {
            params: {
                page: page,
                pageSize: pageSize
            }
        })
    }

    get(id) {
        return http.get(`/games/${id}`)
    }

    create(data) {
        return http.post('/games', data, { headers: authHeader() })
    }

    update(id, data) {
        return http.put(`/games/${id}`, data, { headers: authHeader() })
    }

    delete(id) {
        return http.delete(`/games/${id}`, { headers: authHeader() })
    }
}

export default new Game()

import http from '@/http-common'
import authHeader from '@/services/auth-header'

class Game {
    getAll() {
        return http.get('/games')
    }

    get(id) {
        return http.get(`/games/${id}`, { headers: authHeader() })
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

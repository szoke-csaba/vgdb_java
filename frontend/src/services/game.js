import http from '@/http-common'
import authHeader from '@/services/auth-header'

class Game {
    getAll(page, pageSize, query, sort) {
        return http.get('/games', {
            params: {
                page: page,
                pageSize: pageSize,
                query: query,
                sort: sort,
            }
        })
    }

    get(id) {
        return http.get(`/games/${id}`)
    }

    create(data) {
        return http.post('/games', data, { headers: authHeader() })
    }

    uploadThumbnail(data) {
        return http.post('/games/upload-thumbnail', data, { headers: authHeader(true) })
    }

    uploadScreenshots(data) {
        return http.post('/games/upload-screenshots', data, { headers: authHeader(true) })
    }

    update(id, data) {
        return http.put(`/games/${id}`, data, { headers: authHeader() })
    }

    delete(id) {
        return http.delete(`/games/${id}`, { headers: authHeader() })
    }
}

export default new Game()

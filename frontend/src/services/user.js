import http from '@/http-common'
import authHeader from "@/services/auth-header"

class User {
    getAll(page, pageSize) {
        return http.get('/users', {
            params: {
                page: page,
                pageSize: pageSize
            },
            headers: authHeader()
        })
    }

    get(id) {
        return http.get(`/users/${id}`, { headers: authHeader() })
    }

    create(data) {
        return http.post('/register', data)
    }

    update(id, data) {
        return http.put(`/users/${id}`, data, { headers: authHeader() })
    }

    delete(id) {
        return http.delete(`/users/${id}`, { headers: authHeader() })
    }

    vote(gameId, vote) {
        return http.post(`/users/vote/${gameId}`, {
            vote,
        }, { headers: authHeader() })
    }

    getGameVote(gameId) {
        return http.get(`/users/vote/${gameId}`, { headers: authHeader() })
    }
}

export default new User()

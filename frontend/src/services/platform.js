import http from '@/http-common'
import authHeader from "@/services/auth-header"

class Platform {
    searchByName(query) {
        return http.get(`/platforms/search/${query}`, { headers: authHeader() })
    }

    getAll(page, pageSize) {
        return http.get('/platforms', {
            params: {
                page: page,
                pageSize: pageSize
            },
            headers: authHeader()
        })
    }

    get(id) {
        return http.get(`/platforms/${id}`, { headers: authHeader() })
    }

    create(data) {
        return http.post('/platforms', data, { headers: authHeader() })
    }

    update(id, data) {
        return http.put(`/platforms/${id}`, data, { headers: authHeader() })
    }

    delete(id) {
        return http.delete(`/platforms/${id}`, { headers: authHeader() })
    }
}

export default new Platform()

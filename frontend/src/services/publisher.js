import http from '@/http-common'
import authHeader from "@/services/auth-header"

class Publisher {
    searchByName(query) {
        return http.get(`/publishers/search/${query}`, { headers: authHeader() })
    }

    getAll(page, pageSize) {
        return http.get('/publishers', {
            params: {
                page: page,
                pageSize: pageSize
            },
            headers: authHeader()
        })
    }

    get(id) {
        return http.get(`/publishers/${id}`, { headers: authHeader() })
    }

    create(data) {
        return http.post('/publishers', data, { headers: authHeader() })
    }

    update(id, data) {
        return http.put(`/publishers/${id}`, data, { headers: authHeader() })
    }

    delete(id) {
        return http.delete(`/publishers/${id}`, { headers: authHeader() })
    }
}

export default new Publisher()

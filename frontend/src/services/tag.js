import http from '@/http-common'
import authHeader from "@/services/auth-header"

class Tag {
    searchByName(query) {
        return http.get(`/tags/search/${query}`, { headers: authHeader() })
    }

    getAll(page, pageSize) {
        return http.get('/tags', {
            params: {
                page: page,
                pageSize: pageSize
            },
            headers: authHeader()
        })
    }

    get(id) {
        return http.get(`/tags/${id}`, { headers: authHeader() })
    }

    create(data) {
        return http.post('/tags', data, { headers: authHeader() })
    }

    update(id, data) {
        return http.put(`/tags/${id}`, data, { headers: authHeader() })
    }

    delete(id) {
        return http.delete(`/tags/${id}`, { headers: authHeader() })
    }
}

export default new Tag()

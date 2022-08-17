import http from '@/http-common'
import authHeader from "@/services/auth-header";

class Developer {
    getAll(page, pageSize) {
        return http.get('/developers', {
            params: {
                page: page,
                pageSize: pageSize
            },
            headers: authHeader()
        })
    }

    get(id) {
        return http.get(`/developers/${id}`, { headers: authHeader() })
    }

    create(data) {
        return http.post('/developers', data, { headers: authHeader() })
    }

    update(id, data) {
        return http.put(`/developers/${id}`, data, { headers: authHeader() })
    }

    delete(id) {
        return http.delete(`/developers/${id}`, { headers: authHeader() })
    }
}

export default new Developer()

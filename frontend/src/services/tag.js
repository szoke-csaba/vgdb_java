import http from '@/http-common'
import authHeader from "@/services/auth-header";

class Tag {
    searchByName(query) {
        return http.get(`/tags/${query}`, { headers: authHeader() })
    }
}

export default new Tag()

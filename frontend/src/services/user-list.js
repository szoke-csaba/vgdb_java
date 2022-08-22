import http from '@/http-common'
import authHeader from "@/services/auth-header"

class UserList {
    changeListType(gameId, type) {
        return http.post(`/user-lists/${gameId}`, {
            type,
        }, { headers: authHeader() })
    }

    getListType(gameId) {
        return http.get(`/user-lists/${gameId}`, { headers: authHeader() })
    }
}

export default new UserList()

import http from '@/http-common'
import authHeader from "@/services/auth-header"

class Vote {
    addVote(gameId, vote) {
        return http.post(`/votes/${gameId}`, {
            vote,
        }, { headers: authHeader() })
    }

    getVote(gameId) {
        return http.get(`/votes/${gameId}`, { headers: authHeader() })
    }
}

export default new Vote()

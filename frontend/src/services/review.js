import http from '@/http-common'
import authHeader from '@/services/auth-header'

class Review {
  post (gameId, text) {
    return http.post(`/reviews/${gameId}`, {
      text
    }, { headers: authHeader() })
  }
}

export default new Review()

import http from '@/http-common'
import authHeader from '@/services/auth-header'

class Genre {
  searchByName (query) {
    return http.get(`/genres/search/${query}`, { headers: authHeader() })
  }

  getAll (page, pageSize) {
    return http.get('/genres', {
      params: {
        page,
        pageSize
      },
      headers: authHeader()
    })
  }

  get (id) {
    return http.get(`/genres/${id}`, { headers: authHeader() })
  }

  create (data) {
    return http.post('/genres', data, { headers: authHeader() })
  }

  update (id, data) {
    return http.put(`/genres/${id}`, data, { headers: authHeader() })
  }

  delete (id) {
    return http.delete(`/genres/${id}`, { headers: authHeader() })
  }
}

export default new Genre()

import http from '@/http-common'
import { Buffer } from 'buffer'

class Auth {
  register (user) {
    return http.post('/auth/register', {
      email: user.email,
      password: user.password
    })
  }

  login (user) {
    return http
      .post('/auth/login', {}, {
        headers: {
          Authorization: 'Basic ' + Buffer.from(user.email + ':' + user.password).toString('base64')
        },
        withCredentials: true
      })
      .then(response => {
        localStorage.setItem('user', JSON.stringify(response.data))

        return response.data
      })
  }

  logout () {
    localStorage.removeItem('user')
  }
}

export default new Auth()

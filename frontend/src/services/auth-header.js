export default function authHeader (withFile = false) {
  const user = JSON.parse(localStorage.getItem('user'))

  if (user && user.token) {
    const headers = {
      Authorization: 'Bearer ' + user.token
    }

    if (withFile) {
      headers['Content-Type'] = 'multipart/form-data'
    }

    return headers
  } else {
    return {}
  }
}

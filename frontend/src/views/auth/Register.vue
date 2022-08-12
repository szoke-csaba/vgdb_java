<template>
  <div class="d-flex justify-content-center">
    <div class="col-md-4">
      <div class="card card-container p-4">
        <h1 class="mb-3 text-center">Register</h1>
        <Form @submit="handleRegister" :validation-schema="schema">
          <div>
            <div class="form-group mb-3">
              <label for="email" class="form-label">Email</label>
              <Field name="email" type="email" class="form-control" />
              <ErrorMessage name="email" class="error-feedback" />
            </div>
            <div class="form-group mb-3">
              <label for="password" class="form-label">Password</label>
              <Field name="password" type="password" class="form-control" />
              <ErrorMessage name="password" class="error-feedback" />
            </div>
            <div class="form-group">
              <button class="btn btn-primary btn-block" :disabled="loading">
                <span v-show="loading" class="spinner-border spinner-border-sm"></span>
                Register
              </button>
            </div>
          </div>
        </Form>
        <div class="form-group">
          <div v-if="message" class="alert alert-danger mt-3 mb-0">
            {{ message }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { ErrorMessage, Field, Form } from 'vee-validate'
  import * as yup from 'yup'

  export default {
    name: 'RegisterPage',
    components: {
      Form,
      Field,
      ErrorMessage,
    },
    data() {
      const schema = yup.object().shape({
        email: yup
            .string()
            .required('Email is required!')
            .email('Email is invalid!')
            .max(20, 'Must be maximum 20 characters!'),
        password: yup
            .string()
            .required('Password is required!')
            .min(4, 'Must be at least 4 characters!')
            .max(20, 'Must be maximum 20 characters!'),
      })

      return {
        loading: false,
        message: '',
        schema,
      }
    },
    computed: {
      loggedIn() {
        return this.$store.state.auth.status.loggedIn
      },
    },
    methods: {
      handleRegister(user) {
        this.message = ''
        this.loading = true

        this.$store.dispatch('auth/register', user).then(
            () => {
              this.$router.push({name: 'login'})
            },
            (error) => {
              this.loading = false
              this.message =
                  (error.response &&
                      error.response.data &&
                      error.response.data.message) ||
                  error.message ||
                  error.toString()
            }
        )
      },
    },
  }
</script>

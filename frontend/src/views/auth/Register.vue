<template>
  <div class="text-sm breadcrumbs mb-5">
    <ul>
      <li><router-link :to="{ name: 'home' }">Home</router-link></li>
      <li>Register</li>
    </ul>
  </div>
  <div class="card flex-shrink-0 max-w-sm shadow-2xl bg-base-100 mx-auto">
    <div class="card-body">
      <h1 class="card-title">Register</h1>
      <ValidationForm @submit="handleRegister" :validation-schema="schema">
        <div class="form-control">
          <label for="email" class="label">
            <span class="label-text">Email</span>
          </label>
          <Field id="email" name="email" type="email" class="input input-bordered" />
          <ErrorMessage name="email" class="error-feedback text-red-400" />
        </div>
        <div class="form-control">
          <label for="password" class="label">
            <span class="label-text">Password</span>
          </label>
          <Field id="password" name="password" type="password" class="input input-bordered" />
          <ErrorMessage name="password" class="error-feedback text-red-400" />
        </div>
        <div class="form-control mt-6">
          <button class="btn btn-primary" :class="{ loading: loading }" :disabled="loading">Register</button>
        </div>
      </ValidationForm>
      <div class="alert alert-error shadow-lg" v-if="message">
        <div>
          <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <span>{{ message }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { ErrorMessage, Field, Form as ValidationForm } from 'vee-validate'
  import * as yup from 'yup'
  import { computed } from 'vue'
  import { useHead } from '@vueuse/head'

  export default {
    name: 'RegisterPage',
    components: {
      ValidationForm,
      Field,
      ErrorMessage
    },
    data () {
      const schema = yup.object().shape({
        email: yup
          .string()
          .email('Email is invalid!')
          .required('Email is required!')
          .max(20, 'Must be maximum 20 characters!'),
        password: yup
          .string()
          .required('Password is required!')
          .min(4, 'Must be at least 4 characters!')
          .max(20, 'Must be maximum 20 characters!')
      })

      return {
        loading: false,
        message: '',
        schema
      }
    },
    methods: {
      handleRegister (user) {
        this.message = ''
        this.loading = true

        this.$store.dispatch('auth/register', user).then(
          () => {
            this.$router.push({ name: 'login' })
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
      }
    },
    mounted () {
      useHead({
        title: computed(() => 'Register | ' + process.env.VUE_APP_TITLE)
      })
    }
  }
</script>

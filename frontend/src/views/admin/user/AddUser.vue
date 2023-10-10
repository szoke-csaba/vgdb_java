<template>
  <div class="card flex-shrink-0 max-w-lg shadow-2xl bg-base-100 mx-auto">
    <div class="p-8 pb-0 flex items-center">
      <router-link :to="{ name: 'admin-users' }" class="text-4xl">
        <font-awesome-icon icon="fa-solid fa-arrow-left" />
      </router-link>
      <h1 class="text-4xl pl-5">Add user</h1>
    </div>
    <div class="card-body">
      <ValidationForm @submit="saveUser" :validation-schema="schema">
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
        <div class="form-control">
          <label for="rank" class="label">
            <span class="label-text">Rank</span>
          </label>
          <Field as="select" name="role" id="rank" class="select select-bordered">
            <option value="ROLE_USER">User</option>
            <option value="ROLE_ADMIN">Admin</option>
          </Field>
          <ErrorMessage name="role" class="error-feedback text-red-400" />
        </div>
        <div class="form-control mt-6">
          <button class="btn btn-primary" :class="{ loading: loading }" :disabled="loading">Add</button>
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
  import User from '@/services/user'
  import { ErrorMessage, Field, Form as ValidationForm } from 'vee-validate'
  import * as yup from 'yup'
  import { computed } from "vue"
  import { useHead } from "@vueuse/head"

  export default {
    components: {
      ValidationForm,
      Field,
      ErrorMessage,
    },
    data() {
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
            .max(20, 'Must be maximum 20 characters!'),
        role: yup
            .string()
            .required('Rank is required!'),
      })

      return {
        loading: false,
        message: '',
        schema,
      }
    },
    methods: {
      saveUser(user) {
        this.message = ''
        this.loading = true

        User.create(user)
            .then(() => {
              this.$router.push({name: 'admin-users'})
            })
            .catch(error => {
              this.loading = false
              this.message =
                  (error.response &&
                      error.response.data &&
                      error.response.data.message) ||
                  error.message ||
                  error.toString()
            })
      },
    },
    mounted() {
      useHead({
        title: computed(() => 'Admin - Add user | ' + process.env.VUE_APP_TITLE),
      })
    }
  }
</script>

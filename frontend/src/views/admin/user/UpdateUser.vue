<template>
  <div class="card overflow-visible flex-shrink-0 max-w-lg shadow-2xl bg-base-100 mx-auto" v-if="currentUser">
    <div class="p-8 pb-0 flex items-center">
      <router-link :to="{ name: 'admin-users' }" class="text-4xl">
        <font-awesome-icon icon="fa-solid fa-arrow-left" />
      </router-link>
      <h1 class="text-4xl pl-5">Update user</h1>
    </div>
    <div class="card-body">
      <ValidationForm @submit="updateUser" :validation-schema="schema">
        <div class="form-control">
          <label for="email" class="label">
            <span class="label-text">Email</span>
          </label>
          <Field id="email" name="email" type="email" class="input input-bordered" v-model="currentUser.email" />
          <ErrorMessage name="email" class="error-feedback text-red-400" />
        </div>
        <div class="form-control">
          <label for="password" class="label">
            <span class="label-text">Password</span>
          </label>
          <Field v-model="password" id="password" name="password" type="password" class="input input-bordered" />
          <ErrorMessage name="password" class="error-feedback text-red-400" />
        </div>
        <div class="form-control">
          <label for="rank" class="label">
            <span class="label-text">Rank</span>
          </label>
          <Field as="select" v-model="currentUser.role" name="role" id="rank" class="select select-bordered">
            <option value="ROLE_USER">User</option>
            <option value="ROLE_ADMIN">Admin</option>
          </Field>
          <ErrorMessage name="role" class="error-feedback text-red-400" />
        </div>
        <div class="form-control mt-6">
          <button class="btn btn-primary" :class="{ loading: loading }" :disabled="loading">Update</button>
        </div>
      </ValidationForm>
      <div class="mt-3 shadow-lg alert" :class="success ? 'alert-success' : 'alert-error'" v-if="message">
        <div>
          <svg v-if="success" xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <svg v-if="!success" xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <span>{{ message }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import User from "@/services/user"
  import { ErrorMessage, Field, Form as ValidationForm } from 'vee-validate'
  import * as yup from 'yup'

  export default {
    components: {
      ValidationForm,
      Field,
      ErrorMessage
    },
    data() {
      const schema = yup.object().shape({
        email: yup
            .string()
            .email()
            .required('Email is required!')
            .max(50, 'Must be maximum 50 characters!'),
        password: yup
            .string()
            .notRequired()
            .min(4, 'Must be at least 4 characters!')
            .nullable()
            .max(20, 'Must be maximum 20 characters!')
            .transform((value) => value ? value : null),
        role: yup
            .string()
            .required('Rank is required!'),
      })

      return {
        currentUser: null,
        loading: false,
        success: false,
        message: '',
        schema,
        isLoading: false,
        password: ''
      }
    },
    methods: {
      getUser(id) {
        User.get(id)
            .then(response => {
              this.currentUser = response.data
              document.title = 'Admin - ' + this.currentUser.email + ' | ' + process.env.VUE_APP_TITLE
            })
            .catch(e => {
              this.success = false
              this.message = e
            })
      },
      updateUser() {
        this.loading = true
        this.success = false
        this.message = ''
        this.currentUser.password = this.password

        User.update(this.currentUser.id, this.currentUser)
            .then(() => {
              this.loading = false
              this.success = true
              this.password = ''
              this.message = 'The user was updated successfully!'
            })
            .catch(error => {
              this.loading = false
              this.success = false
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
      this.getUser(this.$route.params.id)
    }
  }
</script>

<template>
  <div class="card overflow-visible flex-shrink-0 max-w-lg shadow-2xl bg-base-100 mx-auto" v-if="currentDeveloper">
    <div class="p-8 pb-0 flex items-center">
      <router-link :to="{ name: 'admin-developers' }" class="text-4xl">
        <font-awesome-icon icon="fa-solid fa-arrow-left" />
      </router-link>
      <h1 class="text-4xl pl-5">Update developer</h1>
    </div>
    <div class="card-body">
      <ValidationForm @submit="updateDeveloper" :validation-schema="schema">
        <div class="form-control">
          <label for="name" class="label">
            <span class="label-text">Name</span>
          </label>
          <Field id="name" name="name" type="text" class="input input-bordered" v-model="currentDeveloper.name" />
          <ErrorMessage name="name" class="error-feedback text-red-400" />
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
  import Developer from '@/services/developer'
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
        name: yup
            .string()
            .required('Name is required!')
            .max(50, 'Must be maximum 50 characters!'),
      })

      return {
        currentDeveloper: null,
        loading: false,
        success: false,
        message: '',
        schema,
        isLoading: false
      }
    },
    methods: {
      getDeveloper(id) {
        Developer.get(id)
            .then(response => {
              this.currentDeveloper = response.data
              document.title = 'Admin - ' + this.currentDeveloper.name + ' | ' + process.env.VUE_APP_TITLE
            })
            .catch(e => {
              this.success = false
              this.message = e
            })
      },
      updateDeveloper() {
        this.loading = true
        this.success = false
        this.message = ''

        Developer.update(this.currentDeveloper.id, this.currentDeveloper)
            .then(() => {
              this.loading = false
              this.success = true
              this.message = 'The developer was updated successfully!'
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
      this.getDeveloper(this.$route.params.id)
    }
  }
</script>

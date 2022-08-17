<template>
  <div class="card flex-shrink-0 max-w-lg shadow-2xl bg-base-100 mx-auto">
    <div class="p-8 pb-0 flex items-center">
      <router-link :to="{ name: 'admin-tags' }" class="text-4xl">
        <font-awesome-icon icon="fa-solid fa-arrow-left" />
      </router-link>
      <h1 class="text-4xl pl-5">Add tag</h1>
    </div>
    <div class="card-body">
      <Form @submit="saveTag" :validation-schema="schema">
        <div class="form-control">
          <label for="name" class="label">
            <span class="label-text">Name</span>
          </label>
          <Field id="name" name="name" type="text" class="input input-bordered" />
          <ErrorMessage name="name" class="error-feedback text-red-400" />
        </div>
        <div class="form-control mt-6">
          <button class="btn btn-primary" :class="{ loading: loading }" :disabled="loading">Add</button>
        </div>
      </Form>
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
  import Tag from '@/services/tag'
  import { ErrorMessage, Field, Form } from 'vee-validate'
  import * as yup from 'yup'
  import { computed } from "vue"
  import { useHead } from "@vueuse/head"

  export default {
    components: {
      Form,
      Field,
      ErrorMessage,
    },
    data() {
      const schema = yup.object().shape({
        name: yup
            .string()
            .required('Name is required!')
            .max(50, 'Must be maximum 50 characters!'),
      })

      return {
        loading: false,
        message: '',
        schema,
      }
    },
    methods: {
      saveTag(tag) {
        this.message = ''
        this.loading = true

        Tag.create(tag)
            .then(() => {
              this.$router.push({name: 'admin-tags'})
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
        title: computed(() => 'Admin - Add tag | ' + process.env.VUE_APP_TITLE),
      })
    }
  }
</script>

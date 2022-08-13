<template>
  <div class="text-sm breadcrumbs mb-5">
    <ul>
      <li><router-link :to="{ name: 'home' }">Home</router-link></li>
      <li><router-link :to="{ name: 'games' }">Games</router-link></li>
      <li><router-link :to="{ name: 'add-game' }">Add game</router-link></li>
    </ul>
  </div>
  <div class="card flex-shrink-0 max-w-sm shadow-2xl bg-base-100 mx-auto">
    <div class="card-body">
      <h1 class="card-title">Add game</h1>
      <Form @submit="saveGame" :validation-schema="schema">
        <div class="form-control">
          <label for="title" class="label">
            <span class="label-text">Title</span>
          </label>
          <Field id="title" name="title" type="text" class="input input-bordered" />
          <ErrorMessage name="title" class="error-feedback text-red-400" />
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
import Game from '@/services/game'
import { ErrorMessage, Field, Form } from 'vee-validate'
import * as yup from 'yup'

export default {
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      title: yup
          .string()
          .required('Title is required!')
          .max(50, 'Must be maximum 50 characters!'),
    })

    return {
      loading: false,
      message: '',
      schema,
    }
  },
  methods: {
    saveGame(game) {
      this.message = ''
      this.loading = true

      Game.create(game)
        .then(() => {
          this.$router.push({name: 'games'})
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
}
</script>

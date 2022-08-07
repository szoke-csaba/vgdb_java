<template>
  <div class="d-flex justify-content-center">
    <div class="col-md-4">
      <div class="card card-container p-4">
        <h1 class="mb-3 text-center">Add game</h1>
        <Form @submit="saveGame" :validation-schema="schema">
          <div class="form-group mb-3">
            <label for="title" class="form-label">Title</label>
            <Field name="title" type="title" class="form-control" />
            <ErrorMessage name="title" class="error-feedback" />
          </div>
          <div class="form-group">
            <button class="btn btn-primary btn-block" :disabled="loading">
              <span v-show="loading" class="spinner-border spinner-border-sm"></span>
              <span>Add</span>
            </button>
          </div>
          <div class="form-group">
            <div v-if="message" class="alert alert-danger mt-3 mb-0" role="alert">
              {{ message }}
            </div>
          </div>
        </Form>
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
  mounted() {
    if (!this.$store.getters['auth/isAdmin']) {
      this.$router.push({name: 'home'})
    }
  }
}
</script>

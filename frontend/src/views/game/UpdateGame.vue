<template>
    <div class="d-flex justify-content-center" v-if="currentGame">
      <div class="col-md-4">
        <div class="card card-container p-4">
          <h1 class="mb-3 text-center">Update game</h1>
          <Form @submit="updateGame" :validation-schema="schema">
            <div class="form-group mb-3">
              <label for="title" class="form-label">Title</label>
              <Field name="title" type="title" class="form-control" v-model="currentGame.title" />
              <ErrorMessage name="title" class="error-feedback" />
            </div>
            <div class="form-group">
              <button class="btn btn-primary btn-block" :disabled="loading">
                <span v-show="loading" class="spinner-border spinner-border-sm"></span>
                <span>Update</span>
              </button>
            </div>
            <div class="form-group" v-if="message">
              <div class="mt-3 mb-0 alert" :class="success ? ' alert-success' : 'alert-danger'" role="alert">
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
      currentGame: null,
      loading: false,
      success: false,
      message: '',
      schema,
    }
  },
  methods: {
    getGame(id) {
      Game.get(id)
          .then(response => {
            this.currentGame = response.data
          })
          .catch(e => {
            this.success = false
            this.message = e
          })
    },
    updateGame() {
      this.loading = true
      this.success = false
      this.message = ''

      Game.update(this.currentGame.id, this.currentGame)
          .then(() => {
            this.loading = false
            this.success = true
            this.message = 'The game was updated successfully!'
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
    this.getGame(this.$route.params.id)
  }
}
</script>

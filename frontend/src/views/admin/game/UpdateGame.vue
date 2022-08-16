<template>
  <div class="card flex-shrink-0 max-w-lg shadow-2xl bg-base-100 mx-auto" v-if="currentGame">
    <div class="p-8 pb-0 flex items-center">
      <router-link :to="{ name: 'admin-games' }" class="text-4xl">
        <font-awesome-icon icon="fa-solid fa-arrow-left" />
      </router-link>
      <h1 class="text-4xl pl-5">Update game</h1>
    </div>
    <div class="card-body">
      <Form @submit="updateGame" :validation-schema="schema">
        <div class="form-control">
          <label for="title" class="label">
            <span class="label-text">Title</span>
          </label>
          <Field id="title" name="title" type="text" class="input input-bordered" v-model="currentGame.title" />
          <ErrorMessage name="title" class="error-feedback text-red-400" />
        </div>
        <div class="form-control">
          <label for="tag" class="label">
            <span class="label-text">Tags</span>
          </label>
          <VueMultiselect
              v-model="selectedTags"
              id="tag"
              label="name"
              track-by="id"
              placeholder="Type to search"
              open-direction="bottom"
              :options="tags"
              :multiple="true"
              :searchable="true"
              :loading="isLoading"
              :internal-search="false"
              :clear-on-select="false"
              :close-on-select="false"
              :options-limit="100"
              :limit="10"
              :limit-text="limitText"
              :max-height="600"
              :hide-selected="true"
              @search-change="asyncFind"
          >
            <template #noResult>No tags found.</template>
            <template #noOptions>No tags found.</template>
          </VueMultiselect>
        </div>
        <div class="form-control mt-6">
          <button class="btn btn-primary" :class="{ loading: loading }" :disabled="loading">Update</button>
        </div>
      </Form>
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
  import Game from '@/services/game'
  import Tag from '@/services/tag'
  import { ErrorMessage, Field, Form } from 'vee-validate'
  import * as yup from 'yup'
  import VueMultiselect from 'vue-multiselect'

  export default {
    components: {
      Form,
      Field,
      ErrorMessage,
      VueMultiselect
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
        selectedTags: [],
        tags: [],
        isLoading: false
      }
    },
    methods: {
      limitText (count) {
        return `and ${count} other tags`
      },
      asyncFind (query) {
        if (!query) {
          return
        }

        this.isLoading = true

        Tag.searchByName(query)
            .then(response => {
              this.isLoading = false
              this.tags = response.data
          })
          .catch(e => {
            this.isLoading = false
            this.success = false
            this.message = e
          })
      },
      getGame(id) {
        Game.get(id)
            .then(response => {
              this.currentGame = response.data
              this.selectedTags = this.currentGame.tags
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

        this.currentGame.tags = this.selectedTags

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

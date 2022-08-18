<template>
  <div class="card overflow-visible flex-shrink-0 max-w-lg shadow-2xl bg-base-100 mx-auto">
    <div class="p-8 pb-0 flex items-center">
      <router-link :to="{ name: 'admin-games' }" class="text-4xl">
        <font-awesome-icon icon="fa-solid fa-arrow-left" />
      </router-link>
      <h1 class="text-4xl pl-5">Add game</h1>
    </div>
    <div class="card-body">
      <Form @submit="saveGame" :validation-schema="schema" enctype="multipart/form-data">
        <div class="form-control">
          <label for="title" class="label">
            <span class="label-text">Title</span>
          </label>
          <Field id="title" name="title" type="text" class="input input-bordered" />
          <ErrorMessage name="title" class="error-feedback text-red-400" />
        </div>
        <div class="form-control">
          <label for="releaseDate" class="label">
            <span class="label-text">Release date</span>
          </label>
          <Field id="releaseDate" name="releaseDate" type="date" class="input input-bordered" />
          <ErrorMessage name="releaseDate" class="error-feedback text-red-400" />
        </div>
        <div class="form-control">
          <label for="thumbnailFile" class="label">
            <span class="label-text">Thumbnail</span>
          </label>
          <Field id="thumbnailFile" name="thumbnailFile" type="file" accept="image/png, image/jpeg" />
          <ErrorMessage name="thumbnailFile" class="error-feedback text-red-400" />
        </div>
        <div class="form-control">
          <label for="tags" class="label">
            <span class="label-text">Tags</span>
          </label>
          <VueMultiselect
              v-model="selectedTags"
              id="tags"
              label="name"
              track-by="id"
              placeholder="Type to search"
              open-direction="bottom"
              :options="tags"
              :multiple="true"
              :searchable="true"
              :loading="loading"
              :internal-search="false"
              :clear-on-select="false"
              :close-on-select="false"
              :options-limit="100"
              :limit="10"
              :limit-text="limitText"
              :max-height="200"
              :hide-selected="true"
              @search-change="asyncFindTags"
          >
            <template #noResult>No tags found.</template>
            <template #noOptions>No tags found.</template>
          </VueMultiselect>
        </div>
        <div class="form-control">
          <label for="genres" class="label">
            <span class="label-text">Genres</span>
          </label>
          <VueMultiselect
              v-model="selectedGenres"
              id="genres"
              label="name"
              track-by="id"
              placeholder="Type to search"
              open-direction="bottom"
              :options="genres"
              :multiple="true"
              :searchable="true"
              :loading="loading"
              :internal-search="false"
              :clear-on-select="false"
              :close-on-select="false"
              :options-limit="100"
              :limit="10"
              :limit-text="limitText"
              :max-height="200"
              :hide-selected="true"
              @search-change="asyncFindGenres"
          >
            <template #noResult>No genres found.</template>
            <template #noOptions>No genres found.</template>
          </VueMultiselect>
        </div>
        <div class="form-control">
          <label for="developers" class="label">
            <span class="label-text">Developers</span>
          </label>
          <VueMultiselect
              v-model="selectedDevelopers"
              id="developers"
              label="name"
              track-by="id"
              placeholder="Type to search"
              open-direction="bottom"
              :options="developers"
              :multiple="true"
              :searchable="true"
              :loading="loading"
              :internal-search="false"
              :clear-on-select="false"
              :close-on-select="false"
              :options-limit="100"
              :limit="10"
              :limit-text="limitText"
              :max-height="200"
              :hide-selected="true"
              @search-change="asyncFindDevelopers"
          >
            <template #noResult>No developers found.</template>
            <template #noOptions>No developers found.</template>
          </VueMultiselect>
        </div>
        <div class="form-control">
          <label for="publishers" class="label">
            <span class="label-text">Publishers</span>
          </label>
          <VueMultiselect
              v-model="selectedPublishers"
              id="publishers"
              label="name"
              track-by="id"
              placeholder="Type to search"
              open-direction="bottom"
              :options="publishers"
              :multiple="true"
              :searchable="true"
              :loading="loading"
              :internal-search="false"
              :clear-on-select="false"
              :close-on-select="false"
              :options-limit="100"
              :limit="10"
              :limit-text="limitText"
              :max-height="200"
              :hide-selected="true"
              @search-change="asyncFindPublishers"
          >
            <template #noResult>No publishers found.</template>
            <template #noOptions>No publishers found.</template>
          </VueMultiselect>
        </div>
        <div class="form-control">
          <label for="platforms" class="label">
            <span class="label-text">Platforms</span>
          </label>
          <VueMultiselect
              v-model="selectedPlatforms"
              id="platforms"
              label="name"
              track-by="id"
              placeholder="Type to search"
              open-direction="bottom"
              :options="platforms"
              :multiple="true"
              :searchable="true"
              :loading="loading"
              :internal-search="false"
              :clear-on-select="false"
              :close-on-select="false"
              :options-limit="100"
              :limit="10"
              :limit-text="limitText"
              :max-height="200"
              :hide-selected="true"
              @search-change="asyncFindPlatforms"
          >
            <template #noResult>No platforms found.</template>
            <template #noOptions>No platforms found.</template>
          </VueMultiselect>
        </div>
        <div class="form-control">
          <label for="rawgId" class="label">
            <span class="label-text">Rawg id</span>
          </label>
          <Field id="rawgId" name="rawgId" type="number" class="input input-bordered" value="0" />
          <ErrorMessage name="rawgId" class="error-feedback text-red-400" />
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
  import Tag from '@/services/tag'
  import Genre from '@/services/genre'
  import Developer from '@/services/developer'
  import Publisher from '@/services/publisher'
  import Platform from '@/services/platform'
  import { ErrorMessage, Field, Form } from 'vee-validate'
  import * as yup from 'yup'
  import { computed } from "vue"
  import { useHead } from "@vueuse/head"
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
        releaseDate: yup
            .date(),
        rawgId: yup
            .number('Must be a number!')
            .required('Rawg id is required!')
            .min(0),
      })

      return {
        loading: false,
        message: '',
        schema,
        selectedTags: [],
        tags: [],
        selectedGenres: [],
        genres: [],
        selectedDevelopers: [],
        developers: [],
        selectedPublishers: [],
        publishers: [],
        selectedPlatforms: [],
        platforms: [],
      }
    },
    methods: {
      limitText (count) {
        return `and ${count} other`
      },
      asyncFindTags (query) {
        if (!query) {
          return
        }

        this.loading = true

        Tag.searchByName(query)
            .then(response => {
              this.loading = false
              this.tags = response.data
            })
            .catch(e => {
              this.loading = false
              this.success = false
              this.message = e
            })
      },
      asyncFindGenres (query) {
        if (!query) {
          return
        }

        this.loading = true

        Genre.searchByName(query)
            .then(response => {
              this.loading = false
              this.genres = response.data
            })
            .catch(e => {
              this.loading = false
              this.success = false
              this.message = e
            })
      },
      asyncFindDevelopers (query) {
        if (!query) {
          return
        }

        this.loading = true

        Developer.searchByName(query)
            .then(response => {
              this.loading = false
              this.developers = response.data
            })
            .catch(e => {
              this.loading = false
              this.success = false
              this.message = e
            })
      },
      asyncFindPublishers (query) {
        if (!query) {
          return
        }

        this.loading = true

        Publisher.searchByName(query)
            .then(response => {
              this.loading = false
              this.publishers = response.data
            })
            .catch(e => {
              this.loading = false
              this.success = false
              this.message = e
            })
      },
      asyncFindPlatforms (query) {
        if (!query) {
          return
        }

        this.loading = true

        Platform.searchByName(query)
            .then(response => {
              this.loading = false
              this.platforms = response.data
            })
            .catch(e => {
              this.loading = false
              this.success = false
              this.message = e
            })
      },
      saveGame(game) {
        this.message = ''
        this.loading = true

        game.tags = this.selectedTags
        game.genres = this.selectedGenres
        game.developers = this.selectedDevelopers
        game.publishers = this.selectedPublishers
        game.platforms = this.selectedPlatforms

        Game.create(game)
          .then(() => {
            this.$router.push({name: 'admin-games'})
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
        title: computed(() => 'Admin - Add game | ' + process.env.VUE_APP_TITLE),
      })
    }
  }
</script>

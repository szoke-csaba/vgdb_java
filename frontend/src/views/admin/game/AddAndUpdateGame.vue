<template>
  <div class="card overflow-visible flex-shrink-0 max-w-lg shadow-2xl bg-base-100 mx-auto" v-if="addPage || (!addPage && currentGame)">
    <div class="p-8 pb-0 flex items-center">
      <router-link :to="{ name: 'admin-games' }" class="text-4xl">
        <font-awesome-icon icon="fa-solid fa-arrow-left" />
      </router-link>
      <h1 class="text-4xl pl-5">{{ mainTitle }} game</h1>
    </div>
    <div class="card-body">
      <div class="tabs">
        <a class="tab tab-bordered" @click="activeTab = 1" :class="{ 'tab-active': activeTab === 1 }">Main</a>
        <a class="tab tab-bordered" @click="activeTab = 2" :class="{ 'tab-active': activeTab === 2 }">Relations</a>
        <a class="tab tab-bordered" @click="activeTab = 3" :class="{ 'tab-active': activeTab === 3 }">Images</a>
      </div>
      <Form @submit="submitForm" :validation-schema="schema">
        <div v-show="activeTab === 1">
          <div class="form-control">
            <label for="title" class="label">
              <span class="label-text">Title</span>
            </label>
            <Field id="title" name="title" type="text" class="input input-bordered" v-if="addPage" />
            <Field id="title" name="title" type="text" class="input input-bordered"
                   v-if="!addPage" v-model="currentGame.title" />
            <ErrorMessage name="title" class="error-feedback text-red-400" />
          </div>
          <div class="form-control">
            <label for="releaseDate" class="label">
              <span class="label-text">Release date</span>
            </label>
            <Field id="releaseDate" name="releaseDate" type="date" class="input input-bordered" v-if="addPage" />
            <Field id="releaseDate" name="releaseDate" type="date" class="input input-bordered"
                   v-if="!addPage" v-model="currentGame.releaseDate" />
            <ErrorMessage name="releaseDate" class="error-feedback text-red-400" />
          </div>
          <div class="form-control">
            <label for="rawgId" class="label">
              <span class="label-text">Rawg id</span>
            </label>
            <Field id="rawgId" name="rawgId" type="number" class="input input-bordered"
                   value="0" v-if="addPage" />
            <Field id="rawgId" name="rawgId" type="number" class="input input-bordered"
                   v-if="!addPage" v-model="currentGame.rawgId" />
            <ErrorMessage name="rawgId" class="error-feedback text-red-400" />
          </div>
        </div>
        <div v-show="activeTab === 2">
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
                :loading="ajaxLoading"
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
                :loading="ajaxLoading"
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
                :loading="ajaxLoading"
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
                :loading="ajaxLoading"
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
              :loading="ajaxLoading"
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
        </div>
        <div v-show="activeTab === 3">
          <div class="form-control">
            <label for="thumbnail" class="label">
              <span class="label-text">Thumbnail</span>
            </label>
            <Field id="thumbnail" name="thumbnail" type="file" class="mb-3" accept="image/png, image/jpeg" />
            <img :src="currentGame.thumbnailAbsolute" :alt="currentGame.title + ' thumbnail'"
                 v-if="!addPage && !currentGame.thumbnailAbsolute.startsWith('https://placeimg.com')">
            <ErrorMessage name="thumbnail" class="error-feedback text-red-400" />
          </div>
          <div class="form-control">
            <label for="screenshots" class="label">
              <span class="label-text">Screenshots</span>
            </label>
            <Field id="screenshots" name="screenshots" type="file" class="mb-3" accept="image/png, image/jpeg" multiple />
            <div v-if="!addPage && currentGame.screenshots">
              <img v-show="index === activeSS" v-for="(screenshot, index) in currentGame.screenshots" :src="screenshot.absoluteUrl"
                   :key="index" :alt="currentGame.title + ' screenshot ' + (index + 1)">
              <div class="flex justify-center w-full py-2 gap-2">
                <a @click="activeSS = index" class="btn btn-xs" v-for="(screenshot, index) in currentGame.screenshots" :key="index">
                  {{ index + 1 }}
                </a>
              </div>
            </div>
            <ErrorMessage name="screenshots" class="error-feedback text-red-400" />
          </div>
        </div>
        <div class="form-control mt-6">
          <button class="btn btn-primary" :class="{ loading: loading }" :disabled="loading">{{ mainTitle }}</button>
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
        rawgId: yup
            .number('Must be a number!')
            .required('Rawg id is required!')
            .min(0),
      })

      return {
        currentGame: null,
        success: false,
        addPage: true,
        mainTitle: 'Add',
        loading: false,
        ajaxLoading: false,
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
        uploadThumbnail: {
          gameId: 0,
          thumbnail: null
        },
        screenshots: null,
        activeTab: 1,
        activeSS: 0,
        gameId: 0,
      }
    },
    methods: {
      limitText (count) {
        return `and ${count} other`
      },
      asyncFindTags (query) {
        this.asyncFind(query, Tag, 'tags')
      },
      asyncFindGenres (query) {
        this.asyncFind(query, Genre, 'genres')
      },
      asyncFindDevelopers (query) {
        this.asyncFind(query, Developer, 'developers')
      },
      asyncFindPublishers (query) {
        this.asyncFind(query, Publisher, 'publishers')
      },
      asyncFindPlatforms (query) {
        this.asyncFind(query, Platform, 'platforms')
      },
      asyncFind (query, service, result) {
        if (!query) {
          return
        }

        this.ajaxLoading = true

        service.searchByName(query)
            .then(response => {
              this.ajaxLoading = false
              this[result] = response.data
            })
            .catch(e => {
              this.ajaxLoading = false
              this.message = e
            })
      },
      submitForm(game) {
        this.loading = true
        this.success = false
        this.message = ''

        game.tags = JSON.parse(JSON.stringify(this.selectedTags))
        game.genres = JSON.parse(JSON.stringify(this.selectedGenres))
        game.developers = JSON.parse(JSON.stringify(this.selectedDevelopers))
        game.publishers = JSON.parse(JSON.stringify(this.selectedPublishers))
        game.platforms = JSON.parse(JSON.stringify(this.selectedPlatforms))

        this.uploadThumbnail.thumbnail = game.thumbnail
        this.screenshots = game.screenshots
        game.thumbnail = null
        game.screenshots = null

        if (this.addPage) {
          this.saveGame(game)
        } else {
          this.updateGame(game)
        }
      },
      saveGame(game) {
        Game.create(game)
          .then(response => {
            this.gameId = response.data

            if (!this.uploadThumbnail.thumbnail) {
              return
            }

            this.uploadThumbnail.gameId = this.gameId

            Game.uploadThumbnail(this.uploadThumbnail)
          })
          .then(() => {
            if (!this.screenshots) {
              return
            }

            let formData = new FormData()
            formData.append("gameId", this.gameId)

            for (let i = 0; i < this.screenshots.length; i++) {
              formData.append("screenshots", this.screenshots[i])
            }

            Game.uploadScreenshots(formData)
          })
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
      updateGame(game) {
        this.uploadThumbnail.gameId = this.currentGame.id

        Game.update(this.currentGame.id, game)
            .then(() => {
              if (!this.uploadThumbnail.thumbnail) {
                return
              }

              Game.uploadThumbnail(this.uploadThumbnail)
            })
            .then(() => {
              if (!this.screenshots) {
                return
              }

              let formData = new FormData()
              formData.append("gameId", this.currentGame.id)

              for (let i = 0; i < this.screenshots.length; i++) {
                formData.append("screenshots", this.screenshots[i])
              }

              Game.uploadScreenshots(formData)
            })
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
      getGame(id) {
        Game.get(id)
            .then(response => {
              this.currentGame = response.data
              this.selectedTags = this.currentGame.tags
              this.selectedGenres = this.currentGame.genres
              this.selectedDevelopers = this.currentGame.developers
              this.selectedPublishers = this.currentGame.publishers
              this.selectedPlatforms = this.currentGame.platforms

              document.title = 'Admin - ' + this.currentGame.title + ' | ' + process.env.VUE_APP_TITLE
            })
            .catch(e => {
              this.success = false
              this.message = e
            })
      },
    },
    mounted() {
      if (this.$route.params.id) {
        this.addPage = false
        this.mainTitle = 'Update'
        this.getGame(this.$route.params.id)
      } else {
        useHead({
          title: computed(() => 'Admin - Add game | ' + process.env.VUE_APP_TITLE),
        })
      }
    }
  }
</script>

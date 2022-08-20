<template>
  <div class="flex mb-5 items-center">
    <h1 class="text-4xl mr-3">Genres</h1>
    <router-link :to="{ name: 'add-genre' }" class="btn btn-sm btn-primary" title="Add">
      <font-awesome-icon icon="fa-solid fa-plus" />
    </router-link>
  </div>
  <div class="overflow-x-auto">
    <table class="table table-compact w-full">
      <thead>
      <tr>
        <th style="width: 5%">ID</th>
        <th>Name</th>
        <th style="width: 5%" colspan="2" class="text-center">Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(genre, index) in genres" :key="index">
        <th>{{ genre.id }}</th>
        <td>{{ genre.name }}</td>
        <td title="Edit" class="text-end">
          <router-link :to="{ name: 'update-genre', params: { id: genre.id } }" class="btn btn-sm btn-secondary" title="Edit">
            <font-awesome-icon icon="fa-solid fa-pen" />
          </router-link>
        </td>
        <td title="Delete">
          <button @click="deleteGenre(genre.id)" class="btn btn-sm btn-danger ms-2" title="Delete">
            <font-awesome-icon icon="fa-solid fa-trash" />
          </button>
        </td>
      </tr>
      </tbody>
    </table>
    <div class="btn-group mt-8 justify-center" v-if="paging.pages > 1">
      <router-link :to="{ name: 'admin-genres', query: { page: page } }" v-for="(page, index) in paging.pages" :key="index"
                   class="btn" :class="{ 'btn-disabled': page === paging.currentPage }">
        {{ page }}
      </router-link>
    </div>
  </div>
</template>

<script>
  import Genre from '@/services/genre'
  import { computed } from "vue"
  import { useHead } from "@vueuse/head"

  export default {
    data() {
      return {
        genres: [],
        paging: [],
        currentPage: this.$route.query.page,
      }
    },
    methods: {
      retrieveGenres() {
        Genre.getAll(this.currentPage, process.env.VUE_APP_ADMIN_PAGE_SIZE)
            .then(response => {
              this.genres = response.data.genres
              this.paging = response.data.paging
            })
            .catch(e => {
              alert(e)
            })
      },
      deleteGenre(id) {
        Genre.delete(id)
            .then(() => {
              const index = this.genres.findIndex(post => post.id === id)
              if (~index)
                this.genres.splice(index, 1)
            })
            .catch(e => {
              alert(e)
            })
      },
    },
    mounted() {
      this.retrieveGenres()

      useHead({
        title: computed(() => 'Admin - Genres | ' + process.env.VUE_APP_TITLE),
      })
    }
  }
</script>

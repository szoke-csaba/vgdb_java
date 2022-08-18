<template>
  <div class="flex mb-5 items-center">
    <h1 class="text-4xl mr-3">Platforms</h1>
    <router-link :to="{ name: 'add-platform' }" class="btn btn-sm btn-primary" title="Add">
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
      <tr v-for="(platform, index) in platforms" :key="index">
        <th>{{ platform.id }}</th>
        <td>{{ platform.name }}</td>
        <td title="Edit" class="text-end">
          <router-link :to="{ name: 'update-platform', params: { id: platform.id } }" class="btn btn-sm btn-secondary" title="Edit">
            <font-awesome-icon icon="fa-solid fa-pen" />
          </router-link>
        </td>
        <td title="Delete">
          <button @click="deletePlatform(platform.id)" class="btn btn-sm btn-danger ms-2" title="Delete">
            <font-awesome-icon icon="fa-solid fa-trash" />
          </button>
        </td>
      </tr>
      </tbody>
    </table>
    <div class="btn-group mt-8 justify-center" v-if="paging.pages > 1">
      <router-link :to="{ name: 'admin-platforms', query: { page: page } }" v-for="(page, index) in paging.pages" :key="index"
                   class="btn" :class="{ 'btn-disabled': page === paging.currentPage }">
        {{ page }}
      </router-link>
    </div>
  </div>
</template>

<script>
  import { useRoute } from 'vue-router'
  import Platform from '@/services/platform'
  import { computed } from "vue"
  import { useHead } from "@vueuse/head"

  export default {
    data() {
      return {
        platforms: [],
        paging: [],
        currentPage: useRoute().query.page
      }
    },
    methods: {
      retrievePlatforms() {
        Platform.getAll(this.currentPage, process.env.VUE_APP_ADMIN_PAGE_SIZE)
            .then(response => {
              this.platforms = response.data.platforms
              this.paging = response.data.paging
            })
            .catch(e => {
              alert(e)
            })
      },
      deletePlatform(id) {
        Platform.delete(id)
            .then(() => {
              const index = this.platforms.findIndex(post => post.id === id)
              if (~index)
                this.platforms.splice(index, 1)
            })
            .catch(e => {
              alert(e)
            })
      },
    },
    mounted() {
      this.retrievePlatforms()

      useHead({
        title: computed(() => 'Admin - Platforms | ' + process.env.VUE_APP_TITLE),
      })
    }
  }
</script>

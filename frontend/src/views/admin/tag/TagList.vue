<template>
  <div class="flex mb-5 items-center">
    <h1 class="text-4xl mr-3">Tags</h1>
    <router-link :to="{ name: 'add-tag' }" class="btn btn-sm btn-primary" title="Add">
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
      <tr v-for="(tag, index) in tags" :key="index">
        <th>{{ tag.id }}</th>
        <td>{{ tag.name }}</td>
        <td title="Edit" class="text-end">
          <router-link :to="{ name: 'update-tag', params: { id: tag.id } }" class="btn btn-sm btn-secondary" title="Edit">
            <font-awesome-icon icon="fa-solid fa-pen" />
          </router-link>
        </td>
        <td title="Delete">
          <button @click="deleteTag(tag.id)" class="btn btn-sm btn-danger ms-2" title="Delete">
            <font-awesome-icon icon="fa-solid fa-trash" />
          </button>
        </td>
      </tr>
      </tbody>
    </table>
    <div class="btn-group mt-8 justify-center" v-if="paging.pages > 1">
      <router-link :to="{ name: 'admin-tags', query: { page: page } }" v-for="(page, index) in paging.pages" :key="index"
                   class="btn" :class="{ 'btn-disabled': page === paging.currentPage }">
        {{ page }}
      </router-link>
    </div>
  </div>
</template>

<script>
  import { useRoute } from 'vue-router'
  import Tag from '@/services/tag'
  import { computed } from "vue"
  import { useHead } from "@vueuse/head"

  export default {
    data() {
      return {
        tags: [],
        paging: [],
        currentPage: useRoute().query.page
      }
    },
    methods: {
      retrieveTags() {
        Tag.getAll(this.currentPage, process.env.VUE_APP_ADMIN_PAGE_SIZE)
            .then(response => {
              this.tags = response.data.tags
              this.paging = response.data.paging
            })
            .catch(e => {
              alert(e)
            })
      },
      deleteTag(id) {
        Tag.delete(id)
            .then(() => {
              const index = this.tags.findIndex(post => post.id === id)
              if (~index)
                this.tags.splice(index, 1)
            })
            .catch(e => {
              alert(e)
            })
      },
    },
    mounted() {
      this.retrieveTags()

      useHead({
        title: computed(() => 'Admin - Tags | ' + process.env.VUE_APP_TITLE),
      })
    }
  }
</script>

<template>
  <div class="flex mb-5 items-center">
    <h1 class="text-4xl mr-3">Users</h1>
    <router-link :to="{ name: 'add-user' }" class="btn btn-sm btn-primary" title="Add">
      <font-awesome-icon icon="fa-solid fa-plus" />
    </router-link>
  </div>
  <div class="overflow-x-auto">
    <table class="table table-compact w-full">
      <thead>
      <tr>
        <th style="width: 5%">ID</th>
        <th>Email</th>
        <th>Role</th>
        <th style="width: 5%" colspan="2" class="text-center">Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(user, index) in users" :key="index">
        <th>{{ user.id }}</th>
        <td>{{ user.email }}</td>
        <td>{{ user.role }}</td>
        <td title="Edit" class="text-end">
          <router-link :to="{ name: 'update-user', params: { id: user.id } }" class="btn btn-sm btn-secondary" title="Edit">
            <font-awesome-icon icon="fa-solid fa-pen" />
          </router-link>
        </td>
        <td title="Delete">
          <button @click="deleteUser(user.id)" class="btn btn-sm btn-danger ms-2" title="Delete">
            <font-awesome-icon icon="fa-solid fa-trash" />
          </button>
        </td>
      </tr>
      </tbody>
    </table>
    <div class="btn-group mt-8 justify-center" v-if="paging.pages > 1">
      <router-link :to="{ name: 'admin-users', query: { page: page } }" v-for="(page, index) in paging.pages" :key="index"
                   class="btn" :class="{ 'btn-disabled': page === paging.currentPage }">
        {{ page }}
      </router-link>
    </div>
  </div>
</template>

<script>
  import User from '@/services/user'
  import { computed } from "vue"
  import { useHead } from "@vueuse/head"

  export default {
    data() {
      return {
        users: [],
        paging: [],
        currentPage: this.$route.query.page,
      }
    },
    methods: {
      retrieveUsers() {
        User.getAll(this.currentPage, process.env.VUE_APP_ADMIN_PAGE_SIZE)
            .then(response => {
              this.users = response.data.users
              this.paging = response.data.paging
            })
            .catch(e => {
              alert(e)
            })
      },
      deleteUser(id) {
        User.delete(id)
            .then(() => {
              const index = this.users.findIndex(post => post.id === id)
              if (~index)
                this.users.splice(index, 1)
            })
            .catch(e => {
              alert(e)
            })
      },
    },
    mounted() {
      this.retrieveUsers()

      useHead({
        title: computed(() => 'Admin - Users | ' + process.env.VUE_APP_TITLE),
      })
    }
  }
</script>

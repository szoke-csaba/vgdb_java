<template>
  <div class="flex mb-5 items-center">
    <h1 class="text-4xl mr-3">Games</h1>
    <router-link :to="{ name: 'add-game' }" class="btn btn-sm btn-primary" title="Add">
      <font-awesome-icon icon="fa-solid fa-plus" />
    </router-link>
  </div>
  <div class="overflow-x-auto">
    <table class="table table-compact w-full">
      <thead>
        <tr>
          <th style="width: 5%">ID</th>
          <th>Title</th>
          <th style="width: 5%" colspan="2" class="text-center">Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(game, index) in games" :key="index">
          <th>{{ game.id }}</th>
          <td>{{ game.title }}</td>
          <td title="Edit" class="text-end">
            <router-link :to="{ name: 'update-game', params: { id: game.id } }" class="btn btn-sm btn-secondary" title="Edit">
              <font-awesome-icon icon="fa-solid fa-pen" />
            </router-link>
          </td>
          <td title="Delete">
            <button @click="deleteGame(game.id)" class="btn btn-sm btn-danger ms-2" title="Delete">
              <font-awesome-icon icon="fa-solid fa-trash" />
            </button>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="btn-group mt-8 justify-center" v-if="paging.pages > 1">
      <router-link :to="{ name: 'admin-games', query: { page: page } }" v-for="(page, index) in paging.pages" :key="index"
                   class="btn" :class="{ 'btn-disabled': page === paging.currentPage }">
        {{ page }}
      </router-link>
    </div>
  </div>
</template>

<script>
  import { useRoute } from 'vue-router'
  import Game from '@/services/game'

  const PAGE_SIZE = 20

  export default {
  data() {
    return {
      games: [],
      paging: [],
      currentPage: useRoute().query.page
    }
  },
  methods: {
    retrieveGames() {
      Game.getAll(this.currentPage, PAGE_SIZE)
          .then(response => {
            this.games = response.data.games
            this.paging = response.data.paging
          })
          .catch(e => {
            alert(e)
          })
    },
    deleteGame(id) {
      Game.delete(id)
          .then(() => {
            const index = this.games.findIndex(post => post.id === id)
            if (~index)
              this.games.splice(index, 1)
          })
          .catch(e => {
            alert(e)
          })
    },
  },
  mounted() {
    this.retrieveGames()
  }
}
</script>

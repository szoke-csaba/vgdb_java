<template>
  <div>
    <h1 class="mb-3">Games</h1>
    <router-link :to="{ name: 'add-game' }" class="btn btn-primary mb-3" v-if="isAdmin">
      <font-awesome-icon icon="fa-solid fa-plus" />
    </router-link>
    <table class="table">
      <thead>
        <tr>
          <th scope="col" width="40%">Title</th>
          <th scope="col">Created</th>
          <th scope="col">Updated</th>
          <th scope="col" colspan="2" v-if="isAdmin" class="text-center">
            Actions
          </th>
        </tr>
      </thead>
      <tbody v-for="(game, index) in games" :key="index">
        <tr>
          <td>{{game.title}}</td>
          <td>{{game.created}}</td>
          <td>{{game.updated}}</td>
          <td v-if="isAdmin" class="text-end">
            <router-link :to="{ name: 'update-game', params: { id: game.id } }" class="btn btn-primary">
              <font-awesome-icon icon="fa-solid fa-pen" />
            </router-link>
          </td>
          <td v-if="isAdmin">
            <button @click="deleteGame(game.id)" class="btn btn-danger">
              <font-awesome-icon icon="fa-solid fa-trash" />
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import Game from '@/services/game'

export default {
  data() {
    return {
      games: []
    }
  },
  methods: {
    retrieveGames() {
      Game.getAll()
          .then(response => {
            this.games = response.data
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
  computed: {
    currentUser() {
      return this.$store.state.auth.user
    },
    isAdmin() {
      return this.$store.getters['auth/isAdmin']
    }
  },
  mounted() {
    this.retrieveGames()
  }
}
</script>

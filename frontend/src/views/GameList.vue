<template>
  <div>
    <router-link :to="{ name: 'add-game' }" class="btn btn-primary mb-3">Add</router-link>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">Title</th>
          <th scope="col">Created</th>
          <th scope="col">Updated</th>
          <th scope="col" colspan="2">Actions</th>
        </tr>
      </thead>
      <tbody v-for="(game, index) in games" :key="index">
        <tr>
          <td>{{game.title}}</td>
          <td>{{game.created}}</td>
          <td>{{game.updated}}</td>
          <td>
            <router-link :to="{ name: 'update-game', params: { id: game.id } }" class="btn btn-primary">Edit</router-link>
          </td>
          <td>
            <button @click="deleteGame(game.id)" class="btn btn-danger">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import GameDataService from '@/services/GameDataService'

export default {
  data() {
    return {
      games: []
    }
  },
  methods: {
    retrieveGames() {
      GameDataService.getAll()
          .then(response => {
            this.games = response.data
          })
          .catch(e => {
            alert(e)
          })
    },
    deleteGame(id) {
      GameDataService.delete(id)
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

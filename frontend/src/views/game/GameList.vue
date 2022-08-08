<template>
  <div>
    <h1 class="mb-3">Games</h1>
    <router-link :to="{ name: 'add-game' }" class="btn btn-primary mb-3" v-if="isAdmin" title="Add">
      <font-awesome-icon icon="fa-solid fa-plus" />
    </router-link>
    <div class="row">
      <div class="col-sm-3" v-for="(game, index) in games" :key="index">
        <div class="card mb-4">
          <img src="https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg?20200913095930" class="card-img-top" :alt="game.title + ' thumbnail'">
          <div class="card-body">
            <h5 class="card-title">{{ game.title }}</h5>
            <div v-if="isAdmin" class="text-end">
              <router-link :to="{ name: 'update-game', params: { id: game.id } }" class="btn btn-primary" title="Edit">
                <font-awesome-icon icon="fa-solid fa-pen" />
              </router-link>
              <button @click="deleteGame(game.id)" class="btn btn-danger ms-2" title="Delete">
                <font-awesome-icon icon="fa-solid fa-trash" />
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
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

<template>
  <div>
    <div class="text-sm breadcrumbs mb-5">
      <ul>
        <li><router-link :to="{ name: 'home' }">Home</router-link></li>
        <li><router-link :to="{ name: 'games' }">Games</router-link></li>
      </ul>
    </div>
    <h1 class="text-3xl mb-5">Games</h1>
    <router-link :to="{ name: 'add-game' }" class="btn btn-primary mb-5" v-if="isAdmin" title="Add">
      <font-awesome-icon icon="fa-solid fa-plus" />
    </router-link>
    <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-5">
      <div class="card card-compact bg-base-100 shadow-xl" v-for="(game, index) in games" :key="index">
        <figure>
          <img src="https://placeimg.com/400/225/nature" :alt="game.title + ' thumbnail'" />
        </figure>
        <div class="card-body">
          <h2 class="card-title">{{ game.title }}</h2>
          <div v-if="isAdmin" class="card-actions justify-end">
            <router-link :to="{ name: 'update-game', params: { id: game.id } }" class="btn btn-sm btn-secondary" title="Edit">
              <font-awesome-icon icon="fa-solid fa-pen" />
            </router-link>
            <button @click="deleteGame(game.id)" class="btn btn-sm btn-danger ms-2" title="Delete">
              <font-awesome-icon icon="fa-solid fa-trash" />
            </button>
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

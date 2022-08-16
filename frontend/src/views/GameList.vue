<template>
  <div>
    <div class="text-sm breadcrumbs mb-5">
      <ul>
        <li><router-link :to="{ name: 'home' }">Home</router-link></li>
        <li><router-link :to="{ name: 'games' }">Games</router-link></li>
      </ul>
    </div>
    <h1 class="text-3xl mb-5">Games</h1>
    <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-5">
      <div class="card card-compact bg-base-100 shadow-xl" v-for="(game, index) in games" :key="index">
        <figure>
          <img src="https://placeimg.com/400/225/nature" :alt="game.title + ' thumbnail'" />
        </figure>
        <div class="card-body">
          <h2 class="card-title">{{ game.title }}</h2>
        </div>
      </div>
    </div>
    <div class="btn-group mt-8 justify-center" v-if="paging.pages > 1">
      <router-link :to="{ name: 'games', query: { page: page } }" v-for="(page, index) in paging.pages" :key="index"
              class="btn" :class="{ 'btn-disabled': page === paging.currentPage }">
        {{ page }}
      </router-link>
    </div>
  </div>
</template>

<script>
  import { useRoute } from 'vue-router'
  import Game from '@/services/game'

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
        Game.getAll(this.currentPage)
            .then(response => {
              this.games = response.data.games
              this.paging = response.data.paging
            })
            .catch(e => {
              alert(e)
            })
      },
    },
    mounted() {
      this.retrieveGames()
    },
  }
</script>

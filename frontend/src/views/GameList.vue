<template>
  <div>
    <div class="text-sm breadcrumbs mb-5">
      <ul>
        <li><router-link :to="{ name: 'home' }">Home</router-link></li>
        <li><router-link :to="{ name: 'games' }">Games</router-link></li>
      </ul>
    </div>
    <h1 class="text-3xl mb-5">Games</h1>
    <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-6 gap-1">
      <div class="cursor-pointer card bg-base-100 shadow-xl bg-cover bg-no-repeat bg-center rounded h-80" v-for="(game, index) in games" :key="index"
           :style="{ 'background-image': `url(${game.thumbnailAbsolute})` }">
        <div class="hover:bg-black/20 transition card-body justify-end p-0">
          <h2 class="bg-black/20 card-title text-slate-50 p-1">{{ game.title }}</h2>
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
  import { computed } from "vue"
  import { useHead } from "@vueuse/head"

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
        Game.getAll(this.currentPage, process.env.VUE_APP_GAMES_PAGE_SIZE)
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

      useHead({
        title: computed(() => 'Games | ' + process.env.VUE_APP_TITLE),
      })
    },
  }
</script>

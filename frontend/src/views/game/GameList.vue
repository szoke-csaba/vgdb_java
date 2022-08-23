<template>
  <div>
    <div class="text-sm breadcrumbs mb-5">
      <ul>
        <li><router-link :to="{ name: 'home' }">Home</router-link></li>
        <li>Games</li>
      </ul>
    </div>
    <div class="mb-5">
      <div class="form-control">
        <label class="input-group">
          <select class="select select-bordered" @change="searchGames" v-model="sort">
            <option disabled>Sort by</option>
            <option value="title-asc">Title Ascending</option>
            <option value="title-desc">Title Descending</option>
            <option value="releaseDate-asc">Release date Ascending</option>
            <option value="releaseDate-desc">Release date Descending</option>
            <option value="id-desc">Recently added</option>
            <option value="id-asc">Oldest added</option>
          </select>
          <input ref="filter" v-model="searchQuery" @input="searchGames" type="text"
                 placeholder="Search…" class="input input-bordered w-full" />
        </label>
      </div>
    </div>
    <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 2xl:grid-cols-6 gap-1">
      <router-link :to="{ name: 'game-single', params: { id: game.id } }"
                   v-for="(game, index) in games" :key="index">
        <div class="cursor-pointer card bg-base-100 shadow-xl bg-cover bg-no-repeat bg-center rounded h-80"
             :style="{ 'background-image': `url(${game.thumbnailAbsolute})` }">
          <div class="hover:bg-black/20 transition card-body justify-end p-0">
            <h2 class="bg-black/20 card-title text-slate-50 p-1">{{ game.title }}</h2>
          </div>
        </div>
      </router-link>
    </div>
    <h1 v-if="games.length === 0" class="text-xl text-center">No games found</h1>
    <div class="btn-group mt-8 justify-center" v-if="paging.pages > 1">
      <router-link :to="{ name: 'games', query: { page: page, query: searchQuery, sort: sort } }"
                   v-for="(page, index) in paging.pages" :key="index"
              class="btn" :class="{ 'btn-disabled': page === paging.currentPage }">
        {{ page }}
      </router-link>
    </div>
    <div class="text-center mt-3" v-if="paging.totalNumOfItems">
      <small>
        {{ (paging.currentPage - 1) * paging.perPage + 1 }}-{{ paging.numOfItems + ((paging.currentPage - 1) * paging.perPage) }} ({{ paging.totalNumOfItems }})
      </small>
    </div>
  </div>
</template>

<script>
  import { computed, nextTick, onMounted, ref } from "vue"
  import { useHead } from "@vueuse/head"
  import Game from '@/services/game'

  export default {
    data() {
      return {
        games: [],
        paging: [],
        currentPage: this.$route.query.page,
        searchQuery: '',
        sort: '',
      }
    },
    setup() {
      const filter = ref(null)

      onMounted(() => {
        nextTick(() => {
          filter.value.focus()
        })
      })

      return {
        filter
      }
    },
    methods: {
      searchGames() {
        this.$router.push({ name: 'games', query: { query: this.searchQuery, sort: this.sort } })
      },
      retrieveGames() {
        Game.getAll(this.currentPage, process.env.VUE_APP_GAMES_PAGE_SIZE, this.searchQuery, this.sort)
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
      this.searchQuery = this.$route.query.query
      this.sort = this.$route.query.sort ?? process.env.VUE_APP_SORT_DEFAULT
      this.retrieveGames()

      useHead({
        title: computed(() => 'Games | ' + process.env.VUE_APP_TITLE),
      })
    },
  }
</script>

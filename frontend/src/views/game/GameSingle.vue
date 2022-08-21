<template>
  <div class="text-sm breadcrumbs mb-5">
    <ul>
      <li><router-link :to="{ name: 'home' }">Home</router-link></li>
      <li><router-link :to="{ name: 'games' }">Games</router-link></li>
      <li>{{ pageTitle }}</li>
    </ul>
  </div>
  <div v-if="game">
    <div class="grid md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-5 gap-5">
      <div class="xl:col-start-2 h-80 card bg-base-100 shadow-xl bg-cover bg-no-repeat bg-center rounded"
           :style="{ 'background-image': `url(${ game.thumbnailAbsolute })` }">
      </div>
      <div class="lg:col-span-2 shadow-xl h-fit border border-x-0 border-t-0">
        <div class="grid grid-cols-3 border p-3 border-b-0">
          <div>Title</div>
          <div class="col-span-2">{{ game.title }}</div>
        </div>
        <div class="grid grid-cols-3 border p-3 border-b-0" v-if="game.releaseDate">
          <div>Release date</div>
          <div class="col-span-2">{{ game.releaseDate }}</div>
        </div>
        <div class="grid grid-cols-3 border p-3 border-b-0" v-if="game.tags.length">
          <div>Tags</div>
          <div class="col-span-2">
            <span v-for="(tag, index) in game.tags" :key="index">
              {{ itemWithCommaIfNotLast(game.tags, index) }}
            </span>
          </div>
        </div>
        <div class="grid grid-cols-3 border p-3 border-b-0" v-if="game.genres.length">
          <div>Genres</div>
          <div class="col-span-2">
            <span v-for="(genre, index) in game.genres" :key="index">
              {{ itemWithCommaIfNotLast(game.genres, index) }}
            </span>
          </div>
        </div>
        <div class="grid grid-cols-3 border p-3 border-b-0" v-if="game.developers.length">
          <div>Developers</div>
          <div class="col-span-2">
            <span v-for="(developer, index) in game.developers" :key="index">
              {{ itemWithCommaIfNotLast(game.developers, index) }}
            </span>
          </div>
        </div>
        <div class="grid grid-cols-3 border p-3 border-b-0" v-if="game.publishers.length">
          <div>Publishers</div>
          <div class="col-span-2">
            <span v-for="(publisher, index) in game.publishers" :key="index">
              {{ itemWithCommaIfNotLast(game.publishers, index) }}
            </span>
          </div>
        </div>
        <div class="grid grid-cols-3 border p-3 border-b-0" v-if="game.platforms.length">
          <div>Platforms</div>
          <div class="col-span-2">
            <span v-for="(platform, index) in game.platforms" :key="index">
              {{ itemWithCommaIfNotLast(game.platforms, index) }}
            </span>
          </div>
        </div>
        <div class="grid grid-cols-3 border p-3 border-b-0 items-center" v-if="currentUser">
          <div>My vote</div>
          <div class="col-span-2">
            <div class="form-control w-20">
              <select class="select select-sm select-bordered" @change="vote" v-model="userVote">
                <option value="0">Vote</option>
                <option value="10">10</option>
                <option value="9">9</option>
                <option value="8">8</option>
                <option value="7">7</option>
                <option value="6">6</option>
                <option value="5">5</option>
                <option value="4">4</option>
                <option value="3">3</option>
                <option value="2">2</option>
                <option value="1">1</option>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="toast toast-start" v-if="voteSaved">
      <div class="alert alert-success">
        <div>
          <span>Vote saved.</span>
        </div>
      </div>
    </div>
    <div class="flex flex-wrap justify-center gap-3 mt-10" v-if="imgs.length">
      <div v-for="(src, index) in imgs" :key="index" class="cursor-pointer hover:outline w-72" @click="() => showImg(index)">
        <img :src="src" />
      </div>
      <vue-easy-lightbox :visible="visibleRef" :imgs="imgs" :index="indexRef" @hide="onHide"></vue-easy-lightbox>
    </div>
  </div>
</template>

<script>
  import Game from "@/services/game"
  import Vote from "@/services/vote"
  import { ref } from 'vue'

  export default {
    data() {
      return {
        game: null,
        pageTitle: '',
        userVote: 0,
        voteSaved: false,
      }
    },
    setup() {
      const visibleRef = ref(false)
      const indexRef = ref(0)
      const imgs = []
      const showImg = (index) => {
        indexRef.value = index
        visibleRef.value = true
      }
      const onHide = () => visibleRef.value = false

      return {
        visibleRef,
        indexRef,
        imgs,
        showImg,
        onHide
      }
    },
    computed: {
      currentUser() {
        return this.$store.state.auth.user
      }
    },
    methods: {
      vote(el) {
        Vote.addVote(this.game.id, parseInt(this.userVote))
            .then(() => {
              this.voteSaved = true
              el.target.blur()
              setTimeout(() => this.voteSaved = false, 3000)
            })
            .catch(e => {
              this.success = false
              this.message = e
            })
      },
      itemWithCommaIfNotLast(list, index) {
        let item = list[index].name

        if (index + 1 < list.length) {
          item += ', '
        }

        return item
      },
      getGame(id) {
        Game.get(id)
            .then(response => {
              this.game = response.data
              this.pageTitle = this.game.title
              this.game.screenshots.forEach(screenshot => this.imgs.push(screenshot.absoluteUrl))

              document.title = this.game.title + ' | ' + process.env.VUE_APP_TITLE
            })
            .catch(e => {
              this.success = false
              this.message = e
            })
      },
      getVote(gameId) {
        Vote.getVote(gameId)
            .then(response => {
              this.userVote = response.data
            })
            .catch(e => {
              this.success = false
              this.message = e
            })
      },
    },
    mounted() {
      if (this.currentUser) {
        this.getVote(this.$route.params.id)
      }

      this.getGame(this.$route.params.id)
    }
  }
</script>

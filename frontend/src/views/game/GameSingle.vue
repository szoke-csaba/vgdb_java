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
        <div class="grid grid-cols-3 border p-3 border-b-0 items-center" v-if="currentUser">
          <div>My lists</div>
          <div class="col-span-2">
            <div class="form-control w-36">
              <select class="select select-sm select-bordered" @change="changeListType" v-model="userListType">
                <option value="0">Not on my list</option>
                <option value="PLAN_TO_PLAY">Plan to play</option>
                <option value="PLAYING">Playing</option>
                <option value="PLAYED">Played</option>
                <option value="ON_HOLD">On hold</option>
                <option value="DROPPED">Dropped</option>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="toast toast-start" v-if="message">
      <div class="alert" :class="success ? 'alert-success' : 'alert-error'">
        <div>
          <span>{{ message }}</span>
        </div>
      </div>
    </div>
    <div>
      <div>
        <label for="write-review" class="btn btn-xs" v-if="currentUser">Write review</label>
        <div>
          <input type="checkbox" id="write-review" class="modal-toggle" />
          <label for="write-review" class="modal cursor-pointer">
            <div class="modal-box w-11/12 max-w-5xl">
              <label for="write-review" id="close-write-review" class="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
              <h1 class="text-2xl text-center pb-5">Write review</h1>
              <Form @submit="postReview" :validation-schema="schema">
                <ErrorMessage name="text" class="error-feedback text-red-400" />
                <Field as="textarea" class="textarea textarea-bordered w-full" rows="20" name="text"></Field>
                <button class="mt-3 btn">Submit</button>
              </Form>
            </div>
          </label>
        </div>
      </div>
      <div class="collapse">
        <input type="checkbox" @click="toggleReviewsCollapseArrow" />
        <div class="collapse-title text-xl font-medium px-0">
          Reviews ({{ game.reviews.length }})
          <font-awesome-icon v-if="game.reviews.length" :icon="'fa-solid fa-angle-' + reviewsCollapsed" />
        </div>
        <div class="collapse-content grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-5" v-if="game.reviews.length">
          <div class="card rounded bg-base-200 shadow-md p-4 h-48"
               v-for="(review, index) in game.reviews" :key="index">
            <div>
              <label v-if="truncateTextNeeded(review.text)" class="btn btn-xs modal-button float-right"
                     :for="'review-' + review.id">Read more</label>
              <div class="mb-5">
                <h1>{{ review.user.username }}</h1>
                <small>{{ review.created }}</small>
              </div>
              <div class="mb-3">
                {{ truncateText(review.text) }}
              </div>
            </div>
            <div v-if="truncateTextNeeded(review.text)">
              <input type="checkbox" :id="'review-' + review.id" class="modal-toggle" />
              <label :for="'review-' + review.id" class="modal cursor-pointer">
                <div class="modal-box w-11/12 max-w-5xl">
                  <label :for="'review-' + review.id" class="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
                  <h1>{{ review.user.username }}</h1>
                  <small>{{ review.created }}</small>
                  <div class="mt-5">
                    {{ review.text }}
                  </div>
                </div>
              </label>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-if="numberOfVotes">
      <div class="flex flex-col-reverse mt-14 w-max max-w-full m-auto">
        <div class="flex items-center w-80 max-w-full" v-for="(numberOfVotes, rating) in numberOfVotesPerRating" :key="rating">
          <div class="w-6">{{ rating }}</div>
          <progress class="mx-3 progress h-4 bg-base-300" :value="calculateWidth(numberOfVotes)" max="100"></progress>
          <div class="ml-auto">({{ numberOfVotes }})</div>
        </div>
      </div>
      <div class="text-center mt-4">
        {{ numberOfVotes }} votes total, average {{ averageRating }}
      </div>
    </div>
    <div class="flex flex-wrap justify-center gap-3 mt-14" v-if="imgs.length">
      <div v-for="(src, index) in imgs" :key="index" class="cursor-pointer hover:outline w-72" @click="() => showImg(index)">
        <img :src="src" />
      </div>
      <vue-easy-lightbox :visible="visibleRef" :imgs="imgs" :index="indexRef" @hide="onHide"></vue-easy-lightbox>
    </div>
  </div>
</template>

<script>
  import { Form, Field, ErrorMessage } from 'vee-validate'
  import * as yup from 'yup'
  import Game from "@/services/game"
  import Vote from "@/services/vote"
  import UserList from "@/services/user-list"
  import Review from "@/services/review"
  import { ref } from 'vue'

  export default {
    components: {
      Form,
      Field,
      ErrorMessage,
    },
    data() {
      const schema = yup.object().shape({
        text: yup
            .string()
            .required("You can't send an empty review!")
      })

      return {
        game: null,
        pageTitle: '',
        userVote: 0,
        success: false,
        message: '',
        numberOfVotes: 0,
        averageRating: 0,
        userListType: 0,
        numberOfVotesPerRating: null,
        mostVotesForARating: 0,
        reviewsCollapsed: 'down',
        schema
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
      postReview(review, { resetForm }) {

        Review.post(this.game.id, review.text)
          .then(() => {
            resetForm()
            document.getElementById('close-write-review').click()
          })
      },
      toggleReviewsCollapseArrow() {
        this.reviewsCollapsed = this.reviewsCollapsed === 'up' ? 'down' : 'up'
      },
      truncateTextNeeded(text) {
        return text.length > 150
      },
      truncateText(text) {
        if (text.length > 150) {
          text = text.substring(0, 150) + '...'
        }

        return text
      },
      calculateWidth(numberOfVotes) {
        return numberOfVotes / this.mostVotesForARating * 100
      },
      vote(el) {
        Vote.addVote(this.game.id, parseInt(this.userVote))
            .then(() => {
              this.success = true
              this.message = 'Vote saved.'
              el.target.blur()
              setTimeout(() => this.message = '', 3000)
            })
            .catch(e => {
              this.success = false
              this.message = e
            })
      },
      changeListType(el) {
        el.target.blur()
        UserList.changeListType(this.game.id, this.userListType)
            .then(() => {
              this.success = true
              this.message = 'List changed.'
              el.target.blur()
              setTimeout(() => this.message = '', 3000)
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
              this.numberOfVotes = response.data.votes.length
              this.averageRating = response.data.averageRating
              this.numberOfVotesPerRating = response.data.numberOfVotesPerRating
              this.mostVotesForARating = response.data.mostVotesForARating

              console.log(response.data)

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
      getListType(gameId) {
        UserList.getListType(gameId)
            .then(response => {
              this.userListType = response.data
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
        this.getListType(this.$route.params.id)
      }

      this.getGame(this.$route.params.id)
    }
  }
</script>

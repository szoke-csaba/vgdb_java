<template>
  <div v-if="currentGame">
    <div class="alert alert-success" role="alert" v-if="message">
      {{ message }}
    </div>
    <div class="mb-3">
      <label for="title" class="form-label">Title</label>
      <input type="text" class="form-control" id="title" required name="title" v-model="currentGame.title">
    </div>
    <div class="mb-3">
      <button @click="updateGame" class="btn btn-primary me-3">Update</button>
    </div>
  </div>
</template>

<script>
import GameDataService from '../services/GameDataService'

export default {
  name: 'edit-game',
  data() {
    return {
      currentGame: null,
      message: ''
    }
  },
  methods: {
    getGame(id) {
      GameDataService.get(id)
          .then(response => {
            this.currentGame = response.data
          })
          .catch(e => {
            alert(e)
          })
    },
    updateGame() {
      GameDataService.update(this.currentGame.id, this.currentGame)
          .then(() => {
            this.message = 'The game was updated successfully!'
          })
          .catch(e => {
            alert(e)
          })
    },
  },
  mounted() {
    this.getGame(this.$route.params.id)
  }
}
</script>

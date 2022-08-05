<template>
  <div v-if="currentGame">
    <div v-if="errors.length" class="alert alert-danger">
      <p class="m-0" v-for="(error, index) in errors" v-bind:key="index">{{ error }}</p>
    </div>
    <div class="alert alert-success" role="alert" v-if="success">
      {{ success }}
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
import GameDataService from '@/services/GameDataService'

export default {
  data() {
    return {
      currentGame: null,
      success: '',
      errors: []
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
      this.success = ''
      this.errors = []

      if (this.currentGame.title === '') {
        this.errors.push('※ Title field is required.')

        return;
      }

      GameDataService.update(this.currentGame.id, this.currentGame)
          .then(() => {
            this.success = 'The game was updated successfully!'
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

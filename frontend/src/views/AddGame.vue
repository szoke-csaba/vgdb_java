<template>
  <div>
    <div v-if="errors.length" class="alert alert-danger">
      <p class="m-0" v-for="(error, index) in errors" v-bind:key="index">{{ error }}</p>
    </div>
    <div class="mb-3">
      <label for="title" class="form-label">Title</label>
      <input type="text" class="form-control" id="title" required name="title" v-model="game.title">
    </div>
    <div class="mb-3">
      <button @click="saveGame" class="btn btn-primary">Save</button>
    </div>
  </div>
</template>

<script>
import GameDataService from '@/services/GameDataService'

export default {
  data() {
    return {
      game: {
        title: '',
      },
      errors: []
    }
  },
  methods: {
    saveGame() {
      this.errors = []

      if (this.game.title === '') {
        this.errors.push('※ Title field is required.')

        return;
      }

      const data = {
        title: this.game.title,
      }

      GameDataService.create(data)
        .then(() => {
          this.$router.push({name: 'games'})
        })
        .catch(e => {
          alert(e)
        })
    },
  }
}
</script>

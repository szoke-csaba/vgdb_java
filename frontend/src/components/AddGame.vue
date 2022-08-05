<template>
  <div>
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
import GameDataService from '../services/GameDataService'

export default {
  name: 'add-game',
  data() {
    return {
      game: {
        title: "",
      },
    }
  },
  methods: {
    saveGame() {
      const data = {
        title: this.game.title,
      };
      GameDataService.create(data)
          .then(() => {
            this.$router.push({name: 'games'})
          })
          .catch( e => {
            alert(e)
          })
    },
  }
}
</script>

<template>
  <div class="container" v-if="currentUser">
    <div class="text-sm breadcrumbs mb-5">
      <ul>
        <li><router-link :to="{ name: 'home' }">Home</router-link></li>
        <li><router-link :to="{ name: 'profile' }">Profile</router-link></li>
      </ul>
    </div>
    <h1 class="mb-5 text-3xl">{{ currentUser.email }}'s profile</h1>
    <p>
      <strong>Token:</strong>
      {{ currentUser.token.substring(0, 20) }} ... {{ currentUser.token.substr(currentUser.token.length - 20) }}
    </p>
    <p>
      <strong>Email:</strong>
      {{ currentUser.email }}
    </p>
    <p>
      <strong>Role:</strong>
      {{ currentUser.role }}
    </p>
  </div>
</template>

<script>
  import { computed } from "vue"
  import { useHead } from "@vueuse/head"

  export default {
    name: 'ProfilePage',
    computed: {
      currentUser() {
        return this.$store.state.auth.user
      }
    },
    mounted() {
      useHead({
        title: computed(() => 'Profile | ' + process.env.VUE_APP_TITLE),
      })
    }
  }
</script>

<template>
  <div class="container" v-if="currentUser">
    <h1 class="mb-5">{{ currentUser.email }}'s profile</h1>
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
  export default {
    name: 'ProfilePage',
    computed: {
      currentUser() {
        return this.$store.state.auth.user
      }
    },
  }
</script>

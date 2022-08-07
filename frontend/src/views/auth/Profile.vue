<template>
  <div class="container">
    <h1 class="mb-5">{{ currentUser.email }}'s profile</h1>
    <p>
      <strong>Token:</strong>
      {{ currentUser.accessToken.substring(0, 20) }} ... {{ currentUser.accessToken.substr(currentUser.accessToken.length - 20) }}
    </p>
    <p>
      <strong>Id:</strong>
      {{ currentUser.id }}
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

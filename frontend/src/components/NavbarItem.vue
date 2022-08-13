<template>
  <nav class="navbar navbar-expand-lg bg-light">
    <div class="container">
      <router-link class="navbar-brand" :to="{ name: 'home' }">vgdb</router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'games' }">Games</router-link>
          </li>
        </ul>
        <div class="navbar-nav ml-auto gap-3">
          <div v-if="!currentUser" class="d-flex">
            <li class="nav-item">
              <router-link :to="{ name: 'register' }" class="nav-link">
                <font-awesome-icon icon="user-plus" /> Register
              </router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{ name: 'login' }" class="nav-link">
                <font-awesome-icon icon="sign-in-alt" /> Login
              </router-link>
            </li>
          </div>
          <div v-if="currentUser" class="d-flex" title="Profile">
            <li class="nav-item me-2">
              <router-link to="/profile" class="nav-link">
                <font-awesome-icon icon="user" />
                {{ currentUser.email }}
              </router-link>
            </li>
            <li class="nav-item" role="button">
              <a class="nav-link" @click.prevent="logOut">
                <font-awesome-icon icon="sign-out-alt" /> Logout
              </a>
            </li>
          </div>
          <div class="theme-switch d-flex align-items-center" title="Switch theme">
            <input type="checkbox" id="theme-switch" class="d-none">
            <font-awesome-icon class="d-none theme-switcher" id="light-icon" icon="fa-solid fa-sun" />
            <font-awesome-icon class="d-none theme-switcher" id="dark-icon" icon="fa-solid fa-moon" />
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
  export default {
    computed: {
      currentUser() {
        return this.$store.state.auth.user
      },
    },
    methods: {
      logOut() {
        this.$store.dispatch('auth/logout')
        this.$router.push({name: 'login'})
      }
    }
  }
</script>

<style lang="scss">
  .theme-switcher {
    cursor: pointer;
    height: 30px;
  }
</style>

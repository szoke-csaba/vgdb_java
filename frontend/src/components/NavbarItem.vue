<template>
    <div class="shadow-md bg-base-200/50">
      <div class="container mx-auto">
      <div class="navbar px-0">
        <div class="navbar-start">
          <div class="dropdown">
            <label tabindex="0" class="btn btn-ghost lg:hidden">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h8m-8 6h16" /></svg>
            </label>
            <ul tabindex="0" class="divide-y menu menu-compact dropdown-content mt-3 p-2 shadow bg-base-100 rounded-box w-52">
              <li>
                <router-link :to="{ name: 'games' }">Games</router-link>
              </li>
              <li v-if="!currentUser" class="md:hidden">
                <router-link :to="{ name: 'register' }">
                  <font-awesome-icon icon="user-plus" /> Register
                </router-link>
              </li>
              <li v-if="!currentUser" class="md:hidden">
                <router-link :to="{ name: 'login' }">
                  <font-awesome-icon icon="sign-in-alt" /> Login
                </router-link>
              </li>
              <li v-if="isAdmin" class="md:hidden">
                <router-link :to="{ name: 'admin-home' }">
                  <font-awesome-icon icon="fa-solid fa-user-secret" /> Admin
                </router-link>
              </li>
              <li v-if="currentUser" class="md:hidden">
                <router-link :to="{ name: 'profile' }">
                  <font-awesome-icon icon="user" />
                  {{ currentUser.email }}
                </router-link>
              </li>
              <li v-if="currentUser" class="md:hidden">
                <a @click.prevent="logOut">
                  <font-awesome-icon icon="sign-out-alt" /> Logout
                </a>
              </li>
            </ul>
          </div>
          <router-link class="btn btn-ghost normal-case text-xl" :to="{ name: 'home' }">vgdb</router-link>
        </div>
        <div class="navbar-center hidden lg:flex">
          <ul class="menu menu-horizontal p-0">
            <li>
              <router-link :to="{ name: 'games' }">Games</router-link>
            </li>
          </ul>
        </div>
        <div class="navbar-end">
          <ul class="menu menu-horizontal p-0 hidden md:inline-flex">
            <li v-if="!currentUser">
              <router-link :to="{ name: 'register' }">
                <font-awesome-icon icon="user-plus" /> Register
              </router-link>
            </li>
            <li v-if="!currentUser">
              <router-link :to="{ name: 'login' }">
                <font-awesome-icon icon="sign-in-alt" /> Login
              </router-link>
            </li>
          </ul>
          <div class="dropdown dropdown-end hidden md:inline-block" v-if="currentUser">
            <label tabindex="0" class="btn btn-ghost rounded-btn">
              <div class="avatar">
                <div class="w-10 rounded-full">
                  <img src="https://picsum.photos/40/40"  alt="user avatar"/>
                </div>
              </div>
            </label>
            <ul tabindex="0" class="menu dropdown-content p-2 shadow bg-base-100 rounded-box w-52 mt-4">
              <li v-if="isAdmin">
                <router-link :to="{ name: 'admin-home' }">
                  <font-awesome-icon icon="fa-solid fa-user-secret" /> Admin
                </router-link>
              </li>
              <li>
                <router-link :to="{ name: 'profile' }">
                  <font-awesome-icon icon="user" />
                  {{ currentUser.email }}
                </router-link>
              </li>
              <li>
                <a @click.prevent="logOut">
                  <font-awesome-icon icon="sign-out-alt" /> Logout
                </a>
              </li>
            </ul>
          </div>
          <ThemeSwitcher />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import ThemeSwitcher from '@/components/ThemeSwitcher'

  export default {
    components: {
      ThemeSwitcher
    },
    computed: {
      currentUser () {
        return this.$store.state.auth.user
      },
      isAdmin () {
        return this.$store.getters['auth/isAdmin']
      }
    },
    methods: {
      logOut () {
        this.$store.dispatch('auth/logout')
        this.$router.push({ name: 'login' })
      }
    }
  }
</script>

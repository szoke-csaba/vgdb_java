<template>
  <div class="text-sm breadcrumbs mb-5">
    <ul>
      <li><router-link :to="{ name: 'home' }">Home</router-link></li>
      <li>404 - Page not found</li>
    </ul>
  </div>
  <div class="card flex-shrink-0 max-w-sm shadow-2xl bg-base-100 mx-auto">
    <div class="card-body items-center">
      <h1 class="card-title text-4xl">404</h1>
      <p class="text-xl mb-3">Page not found</p>
      <router-link :to="{ name: 'home' }" class="btn btn-primary">
        Go home
      </router-link>
    </div>
  </div>
</template>

<script>
  import { computed } from 'vue'
  import { useHead } from '@vueuse/head'

  export default {
    mounted () {
      useHead({
        title: computed(() => '404 - Page not found | ' + process.env.VUE_APP_TITLE)
      })
    }
  }
</script>

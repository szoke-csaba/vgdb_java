<template>
  <font-awesome-icon icon="fa-solid fa-user-secret" />
</template>

<script>
  import { computed } from 'vue'
  import { useHead } from '@vueuse/head'

  export default {
    name: 'AdminHome',
    mounted () {
      useHead({
        title: computed(() => 'Admin - Dashboard | ' + process.env.VUE_APP_TITLE)
      })
    }
  }
</script>

<template>
  <font-awesome-icon icon="fa-solid fa-house" />
</template>

<script>
  import { computed } from 'vue'
  import { useHead } from '@vueuse/head'

  export default {
    name: 'HomePage',
    mounted () {
      useHead({
        title: computed(() => 'Home | ' + process.env.VUE_APP_TITLE)
      })
    }
  }
</script>

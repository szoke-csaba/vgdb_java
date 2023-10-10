<template>
  <component :is="layout"></component>
</template>

<script>
  import DefaultLayout from '@/components/layouts/DefaultLayout'
  import AdminLayout from '@/components/layouts/AdminLayout'

  export default {
    components: {
      default: DefaultLayout,
      admin: AdminLayout
    },
    computed: {
      layout () {
        return this.$route.meta.layout || 'default'
      }
    }
  }
</script>

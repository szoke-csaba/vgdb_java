<template>
  <NavbarItem />
  <div class="container mx-auto py-8 px-4">
    <router-view />
  </div>
</template>

<script>
  import NavbarItem from '@/components/NavbarItem'

  export default {
    components: {
      NavbarItem
    }
  }
</script>

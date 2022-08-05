<template>
  <NavbarItem />
  <div class="container mt-5">
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

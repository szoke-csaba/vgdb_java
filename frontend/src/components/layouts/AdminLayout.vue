<template>
  <div class="drawer drawer-mobile">
    <input id="my-drawer-2" type="checkbox" class="drawer-toggle" />
    <div class="drawer-content">
      <label for="my-drawer-2" class="btn btn-sm btn-primary drawer-button lg:hidden">Menu</label>
      <div class="container mx-auto py-8 px-4">
        <router-view :key="$route.fullPath" />
      </div>
    </div>
    <div class="drawer-side shadow-xl">
      <label for="my-drawer-2" class="drawer-overlay"></label>
      <ul class="menu p-4 overflow-y-auto w-80 bg-base-100 text-base-content">
        <li>
          <router-link :to="{ name: 'admin-games' }">
            Games
          </router-link>
        </li>
        <div class="divider m-0"></div>
        <li>
          <router-link :to="{ name: 'home' }">
            <font-awesome-icon icon="fa-solid fa-house" /> Home
          </router-link>
        </li>
<!--        <li class="items-start">-->
<!--          <ThemeSwitcher />-->
<!--        </li>-->
      </ul>
    </div>
  </div>
</template>

<!-- TODO: doesn't work after switching layouts -->
<!--<script>-->
<!--  import ThemeSwitcher from '@/components/ThemeSwitcher'-->

<!--  export default {-->
<!--    components: {-->
<!--      ThemeSwitcher-->
<!--    }-->
<!--  }-->
<!--</script>-->

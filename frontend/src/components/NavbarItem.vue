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
        <div class="theme-switch">
          <input type="checkbox" id="theme-switch" class="d-none">
          <font-awesome-icon class="d-none theme-switcher" id="light-icon" icon="fa-solid fa-sun" />
          <font-awesome-icon class="d-none theme-switcher" id="dark-icon" icon="fa-solid fa-moon" />
        </div>
      </div>
    </div>
  </nav>
</template>

<style lang="scss">
  .theme-switcher {
    cursor: pointer;
    height: 30px;
  }
</style>

<template>
  <div class="container" v-if="currentUser">
    <div class="text-sm breadcrumbs mb-5">
      <ul>
        <li><router-link :to="{ name: 'home' }">Home</router-link></li>
        <li>Profile</li>
      </ul>
    </div>
    <h1 class="mb-5 text-3xl">{{ currentUser.email }}'s profile</h1>
    <p>
      <strong>Token:</strong>
      {{ currentUser.token.substring(0, 20) }} ... {{ currentUser.token.substr(currentUser.token.length - 20) }}
    </p>
    <p>
      <strong>Email:</strong>
      {{ currentUser.email }}
    </p>
    <p>
      <strong>Role:</strong>
      {{ currentUser.role }}
    </p>
    <div class="divider my-5"></div>
    <div>
      <div class="tabs">
        <a class="tab tab-bordered" :class="{ 'tab-active': activeTab === 'PLAN_TO_PLAY' }"
           @click="activeTab = 'PLAN_TO_PLAY'">PLAN TO PLAY<div class="badge badge-sm ml-2">{{ planToPlayCount }}</div>
        </a>
        <a class="tab tab-bordered" :class="{ 'tab-active': activeTab === 'PLAYING' }"
           @click="activeTab = 'PLAYING'">PLAYING<div class="badge badge-sm ml-2">{{ playingCount }}</div>
        </a>
        <a class="tab tab-bordered" :class="{ 'tab-active': activeTab === 'PLAYED' }"
           @click="activeTab = 'PLAYED'">PLAYED<div class="badge badge-sm ml-2">{{ playedCount }}</div>
        </a>
        <a class="tab tab-bordered" :class="{ 'tab-active': activeTab === 'ON_HOLD' }"
           @click="activeTab = 'ON_HOLD'">ON HOLD<div class="badge badge-sm ml-2">{{ onHoldCount }}</div>
        </a>
        <a class="tab tab-bordered" :class="{ 'tab-active': activeTab === 'DROPPED' }"
           @click="activeTab = 'DROPPED'">DROPPED<div class="badge badge-sm ml-2">{{ droppedCount }}</div>
        </a>
      </div>
      <div>
        <router-link :data-list-type="list.type" class="block hover:underline" :to="{ name: 'game-single', params: { id: list.game.id } }"
                     v-for="(list, index) in currentUser.lists" :key="index" v-show="list.type === activeTab">
          {{ list.game.title }}
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
  import { computed } from "vue"
  import { useHead } from "@vueuse/head"

  export default {
    name: 'ProfilePage',
    data() {
      return {
        activeTab: 'PLAYING',
        planToPlayCount: 0,
        playingCount: 0,
        playedCount: 0,
        onHoldCount: 0,
        droppedCount: 0,
      }
    },
    computed: {
      currentUser() {
        return this.$store.state.auth.user
      }
    },
    mounted() {
      this.countLists()

      useHead({
        title: computed(() => 'Profile | ' + process.env.VUE_APP_TITLE),
      })
    },
    methods: {
      countLists() {
        this.planToPlayCount = document.querySelectorAll('[data-list-type=PLAN_TO_PLAY]').length
        this.playingCount = document.querySelectorAll('[data-list-type=PLAYING]').length
        this.playedCount = document.querySelectorAll('[data-list-type=PLAYED]').length
        this.onHoldCount = document.querySelectorAll('[data-list-type=ON_HOLD]').length
        this.droppedCount = document.querySelectorAll('[data-list-type=DROPPED]').length
      }
    }
  }
</script>

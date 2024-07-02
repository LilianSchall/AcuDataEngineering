<template>
  <v-app>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script setup>
  //
</script>

<style>
html { 
  overflow-y: auto;
}

::-webkit-scrollbar {
  width: 12px;
  background: rgb(var(--v-theme-light_gray_background));
  border-radius: 6px;
}

::-webkit-scrollbar-thumb {
  background-color: rgb(var(--v-theme-dark_gray_background));
  border-radius: 6px;
}

</style>
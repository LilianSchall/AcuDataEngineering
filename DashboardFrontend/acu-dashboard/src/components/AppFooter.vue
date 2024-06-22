<template>
  <v-footer height="30" app>
    <v-spacer></v-spacer>
    <span class="d-flex justify-center">Powered by
      <v-dialog v-model="group6" max-width="50vw">
        <template v-slot:activator="{ props }">
          <v-btn density="compact" variant="text" v-bind="props">Groupe 6</v-btn>
        </template>
        <v-card>
          <v-card-text>
            Projet fait par le Groupe 6 de la majeure SCIA. <br><br>
            Lilian Schall <br>
            Julien Schaffauser <br>
            Guillaume Lalire <br>
            Emile Merle <br> <br>
            Retrouvez l'ensemble du projet sur <a href="https://github.com/LilianSchall/DataEngineeringGp6" target="_blank">Github</a>.
          </v-card-text>
        </v-card>
      </v-dialog>
    </span>
  </v-footer>
</template>

<script setup>
import { ref } from "vue";

const group6 = ref(false);


</script>

<style scoped>
.v-footer {
  background-color: rgb(var(--v-theme-light_gray_background));
}
</style>

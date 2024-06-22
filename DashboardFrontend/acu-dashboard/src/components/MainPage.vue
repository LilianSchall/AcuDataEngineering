<template>
  <v-container fluid class="fill-height mx-0 w-100">
    <v-row>
      <v-col cols="2">
        <v-card>
          <h2 class="mt-2 ml-3">Regions</h2>
          <v-list density="comfortable">
            <v-list-item v-for="region in region_ranks" :key="region.id" @click="clickedRegion(region.id)">
              <v-list-item-title :class="[`rank-${region.rank}`]">#{{ region.rank }} - {{ region.id }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-card>
      </v-col>
      <v-col cols="8" class="text-center pb-0">
        <div class="d-flex justify-space-between">
          <div v-if="hoveredRegion" class="tooltip">
            {{ "Region : " + hoveredRegion }}
          </div>
          <div v-else class="tooltip" @click="clickedRegion(0)">
            Toute la France
          </div>
          <div class="tooltip" @click="viewExercise(selectedExercise)">
            {{ selectedExercise }}
          </div>
          <v-dialog v-model="dialogExercise" max-width="90vw" max-height="80vh">
          <v-card>
            <v-card-title class="d-flex flex-rows">
              <h2 class="mt-2 ml-2">
                {{ selectedExercise }}
              </h2>
              <v-spacer></v-spacer>
              <v-card-actions>
                <v-btn text @click="dialogExercise = false" icon="mdi-close">
                </v-btn>
              </v-card-actions>
            </v-card-title>
            <v-card-text>
              <v-data-table-virtual fixed-header height="60vh" density="comfortable"
                :items="scores_info"></v-data-table-virtual>
            </v-card-text>
          </v-card>
        </v-dialog>
        </div>
        <div class="mt-5">
          <svg xmlns:svg="http://www.w3.org/2000/svg" id="svg2" height="540" width="550"
            class="france-map">
            <path v-for="region of regions" :key="region.id" :id="region.id" :d="region.path"
              @click="clickedRegion(region.id)" :class="[
              'region',
              `rank-${region.rank}`,
              { 'hovered-region': region.id === hoveredRegion },
            ]" @mouseover="onMouseOver($event, region.id)" @mouseout="onMouseOut()" />
          </svg>
        </div>
        <v-dialog v-model="dialogRegion" max-width="90vw" max-height="80vh">
          <v-card>
            <v-card-title class="d-flex flex-rows">
              <h2 class="mt-2 ml-2">
                {{ selectedRegion == 0 ? "Toute la France" : selectedRegion }}
              </h2>
              <v-spacer></v-spacer>
              <v-card-actions>
                <v-btn text @click="dialogRegion = false" icon="mdi-close">
                </v-btn>
              </v-card-actions>
            </v-card-title>
            <v-card-text>
              <v-data-table-virtual fixed-header height="60vh" density="comfortable"
                :items="scores_info"></v-data-table-virtual>
            </v-card-text>
          </v-card>
        </v-dialog>
      </v-col>
      <v-col cols="2">
        <v-card>
          <h2 class="mt-2 ml-3">Exercices</h2>
          <v-list height="80vh" density="comfortable" slim selectable :items="exercises_info" item-title="name"
            item-value="id"
            @update:selected="selectExercise($event)"
            >
            <template v-slot:item="{ props }">
              <v-list-item v-bind="props">
                <template v-slot:prepend="{ isActive }">
                  <v-list-item-action start>
                    <v-checkbox-btn :model-value="isActive"></v-checkbox-btn>
                  </v-list-item-action>
                </template>
              </v-list-item>
            </template>
          </v-list>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref } from "vue";

import paths from "@/utils/france-map.json";
import exercises from "@/utils/exercises.json";
import scores from "@/utils/scores_example.json";

const region_ranks = ref([]);

const regions = paths["paths"];

const exercises_info = exercises["exercises"];

const scores_info = scores["scores"];

console.log(scores_info);

regions.sort((a, b) => a.rank - b.rank);

regions.forEach((region) => {
  region_ranks.value.push({
    id: region.id,
    rank: region.rank,
    name: region.id,
  });
});

const hoveredRegion = ref(null);
const dialogRegion = ref(false);
const dialogExercise = ref(false);
const selectedRegion = ref({});
const selectedExercise = ref("Tous les exercices");

const onMouseOver = (event, regionId) => {
  hoveredRegion.value = regionId;
  const element = event.target;
  element.parentNode.appendChild(element); // Move element to the end of the <g> element
};

const onMouseOut = () => {
  hoveredRegion.value = null;
};


const selectExercise = (id) => {
  console.log(id);
  if (id.length === 0) {
    selectedExercise.value = "Tous les exercices";
  } else {
    selectedExercise.value = "Exercice : " + exercises_info[id - 1]["name"];
  }
};

const viewExercise = (exercise) => {
  console.log(exercise);
  dialogExercise.value = true;
};

const clickedRegion = (regionId) => {
  console.log(regionId);
  selectedRegion.value = regionId;
  dialogRegion.value = true;
};
</script>

<style scoped>
.region {
  fill: #000;
  stroke: #000;
  stroke-width: 1;
}

.hovered-region {
  stroke: #000;
  stroke-width: 2;
  transform: translateY(-10px);
  position: relative;
  z-index: 10 !important;
}

.france-map path {
  transition: transform 0.3s ease;
}

.tooltip {
  background-color: rgb(var(--v-theme-light_gray_background));
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.tooltip:hover {
  cursor: pointer;
  background-color: rgb(var(--v-theme-dark_gray_background));
}

.rank-1 {
  fill: #3232ff;
  color: #3232ff;
}

.rank-2 {
  fill: #4532ec;
  color: #4532ec;
}

.rank-3 {
  fill: #5732da;
  color: #5732da;
}

.rank-4 {
  fill: #6a32c7;
  color: #6a32c7;
}

.rank-5 {
  fill: #7d32b4;
  color: #7d32b4;
}

.rank-6 {
  fill: #8f32a2;
  color: #8f32a2;
}

.rank-7 {
  fill: #a2328f;
  color: #a2328f;
}

.rank-8 {
  fill: #b4327d;
  color: #b4327d;
}

.rank-9 {
  fill: #c7326a;
  color: #c7326a;
}

.rank-10 {
  fill: #da3257;
  color: #da3257;
}

.rank-11 {
  fill: #ec3245;
  color: #ec3245;
}

.rank-12 {
  fill: #ff3232;
  color: #ff3232;
}
</style>

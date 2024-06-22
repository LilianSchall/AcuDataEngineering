<template>
  <v-container fluid class="fill-height mx-0 w-100">
    <v-row>
      <v-col cols="2">
        <v-card>
          <h2 class="mt-2 ml-3">Regions</h2>
          <v-list density="comfortable">
            <v-list-item v-for="region in region_ranks"
            :key="region.id"
            @click="clickedRegion(region.id)"
            >
                <v-list-item-title :class="[`rank-${region.rank}`]">#{{region.rank}} - {{ region.id }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-card>
      </v-col>
      <v-col cols="8" class="text-center">
        <div>
          <svg xmlns:svg="http://www.w3.org/2000/svg" id="svg2" height="584.5448" width="596.41547"
            class="france-map mt-5">
            <path v-for="region of regions" :key="region.id" :id="region.id" :d="region.path" @click="clickedRegion(region.id)" :class="[
            'region',
            `rank-${region.rank}`,
            { 'hovered-region': region.id === hoveredRegion },
          ]" 
          @mouseover="onMouseOver($event, region.id)" @mouseout="onMouseOut()" />
          </svg>
        </div>
        <v-dialog v-model="dialog" max-width="90vw" max-height="80vh">
        <v-card>
          <v-card-title class="d-flex flex-rows">
            <h2 class="mt-2 ml-2">
              {{ selectedRegion }}
            </h2>
            <v-spacer></v-spacer>
              <v-card-actions>
                <v-btn text @click="dialog = false" icon="mdi-close">
                </v-btn>
              </v-card-actions>
          </v-card-title>
          <v-card-text>
            <v-data-table-virtual 
            fixed-header
            height="60vh"
            density="comfortable"
            :items="scores_info"
            ></v-data-table-virtual>
         </v-card-text>
        </v-card>
    </v-dialog>
      </v-col>
      <v-col cols="2">
        <v-card>
          <h2 class="mt-2 ml-3">Exercices</h2>
          <v-list height="80vh" density="comfortable" slim selectable :items="exercises_info" item-title="name"
            item-value="id">
            <template v-slot:item="{ props }">
              <v-list-item v-bind="props" @click="selectExercise(props.value)">
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
const dialog = ref(false);
const selectedRegion = ref({});

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
};

const clickedRegion = (regionId) => {
  console.log(regionId);
  selectedRegion.value = regionId;
  dialog.value = true;
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

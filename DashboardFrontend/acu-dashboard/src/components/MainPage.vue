<template>
  <v-container fluid class="fill-height mx-0 w-100">
    <v-overlay v-model="loading" persistent class="d-flex align-center justify-center">
      <v-progress-circular indeterminate color="white"></v-progress-circular>
    </v-overlay>
    <v-row>
      <v-col cols="2">
        <v-card>
          <h2 class="mt-2 ml-3">Regions</h2>
          <v-list density="comfortable">
            <v-list-item v-for="region in region_ranks" :key="region.id" @click="clickedRegion(region.id)">
              <v-list-item-title :class="[`rank-${region.rank}`]">#{{ region.rank }} - {{ region.name
                }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-card>
      </v-col>
      <v-col cols="8" class="text-center pb-0">
        <div class="d-flex justify-space-between">
          <div v-if="hoveredRegion" class="tooltip">
            {{ "Region : " + getRegionName(hoveredRegion) }}
          </div>
          <div v-else class="tooltip" @click="clickedRegion(0)">
            Toute la France
          </div>
          <!--<div>
            Classement par alertes
            <v-switch v-model="sort_by_score" @update:model-value="change_sorting(selectedExercise)" />
            Classement par score
          </div>-->
          <div class="tooltip" @click="viewExercise(selectedExercise)">
            {{ selectedExercise }}
          </div>
          <v-dialog v-model="dialogExercise" max-width="50vw" max-height="80vh">
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
                  :items="exercises_scores_info"></v-data-table-virtual>
              </v-card-text>
            </v-card>
          </v-dialog>
        </div>
        <div class="mt-5">
          <svg xmlns:svg="http://www.w3.org/2000/svg" id="svg2" height="540" width="550" class="france-map">
            <path v-for="region of regions_paths" :key="region.id" :id="region.id" :d="region.path"
              @click="clickedRegion(region.id)" :class="[
      'region',
      `rank-${getRegionRank(region.id)}`,
      { 'hovered-region': region.id === hoveredRegion },
    ]" @mouseover="onMouseOver($event, region.id)" @mouseout="onMouseOut()" />
          </svg>
        </div>
        <v-dialog v-model="dialogRegion" max-width="50vw" max-height="80vh">
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
                :items="regions_scores_info"></v-data-table-virtual>
            </v-card-text>
          </v-card>
        </v-dialog>
      </v-col>
      <v-col cols="2">
        <v-card>
          <h2 class="mt-2 ml-3">Exercices</h2>
          <v-list height="80vh" density="comfortable" slim selectable :items="exercises_info" item-title="name"
            item-value="id" @update:selected="selectExercise($event)">
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
import { useAppStore } from "@/stores/app";
import paths from "@/utils/france-map.json";

const loading = ref(false);

const appStore = useAppStore();

// Load the regions and exercises
await appStore.getRegions();
await appStore.updateRegionRanks();
await appStore.getExercises();

// Get the list of exercises
const exercises_info = appStore.exercises;

console.log('ex_info_main', exercises_info);

// Get the regions and their ranks
const region_info = appStore.regions;
region_info.sort((a, b) => a.rank - b.rank);
const region_ranks = ref([]);
region_info.forEach((region) => {
  region_ranks.value.push({
    id: region.id,
    rank: region.rank,
    name: region.name,
  });
});

// Get the regions paths
const regions_paths = paths["paths"];

const getRegionRank = (regionId) => {
  const region = region_info.find((region) => region.id === regionId);
  return region ? region.rank : 0;
}

const getRegionName = (regionId) => {
  const region = region_info.find((region) => region.id === regionId);
  return region ? region.name : "Toute la France";
}

const getExerciseName = (exerciseId) => {
  const exercise = exercises_info.find((exercise) => exercise.id === exerciseId);
  return exercise ? exercise.name : "Tous les exercices";
}

//const getExerciseIdFromName = (exerciseName) => {
//  const exercise = exercises_info.find((exercise) => exercise.name === exerciseName);
//  return exercise ? exercise.id : 0;
//}

const sort_by_score = ref(true);

const hoveredRegion = ref(null);
const dialogRegion = ref(false);
const dialogExercise = ref(false);
const selectedRegion = ref({});
const selectedExercise = ref("Tous les exercices");
const exercises_scores_info = ref([]);
const regions_scores_info = ref([]);

const onMouseOver = (event, regionId) => {
  hoveredRegion.value = regionId;
  const element = event.target;
  element.parentNode.appendChild(element); // Move element to the end of the <g> element
};

const onMouseOut = () => {
  hoveredRegion.value = null;
};


const selectExercise = async (id) => {
  if (id.length === 0) {
    id = 0;
    selectedExercise.value = "Tous les exercices";
  } else {
    id = id[0];
    selectedExercise.value = "Exercice : " + exercises_info[id - 1]["name"];
  }

  // Now we need to update the ranks of the regions
  console.log('Updating ranks for id : ', id)
  await appStore.updateRegionRanks(id, sort_by_score.value);
  const new_region_info = appStore.regions;

  new_region_info.sort((a, b) => a.rank - b.rank);

  region_ranks.value = [];

  new_region_info.forEach((region) => {
    region_ranks.value.push({
      id: region.id,
      rank: region.rank,
      name: region.name,
    });
  });
};

const viewExercise = (exercise) => {
  console.log(exercise);

  // Get the scores for the selected exercise
  const scores = appStore.exercises_scores;
  const nb_alerts = appStore.exercises_nb_alerts;

  exercises_scores_info.value = scores.map((region) => {
    return {
      region: getRegionName(region.id_region),
      score: region.score,
      alerts: nb_alerts.find((alert) => alert.id_region === region.id_region).nb_alert
    }
  })
  
  dialogExercise.value = true;


};

const clickedRegion = async (regionId) => {
  selectedRegion.value = getRegionName(regionId);

  console.log('Clicked region : ', regionId)

  // Update the selected region scores
  await appStore.fetchRegionsScores(regionId);
  await appStore.fetchRegionsNbAlerts(regionId);

  const scores = appStore.regions_scores;
  const nb_alerts = appStore.regions_nb_alerts;

  regions_scores_info.value = scores.map((exercise) => {
    return {
      exercice: getExerciseName(exercise.id_exercise),
      score: exercise.score,
      alerts: nb_alerts.find((alert) => alert.id_exercise === exercise.id_exercise).nb_alert
    }
  })

  dialogRegion.value = true;
};


//const change_sorting = async (exerciseName) => {
//
//  const id = getExerciseIdFromName(exerciseName);
//
//  await appStore.updateRegionRanks(id, sort_by_score.value);
//
//  const new_region_info = appStore.regions;
//
//  new_region_info.sort((a, b) => a.rank - b.rank);
//
//  region_ranks.value = [];
//
//  new_region_info.forEach((region) => {
//    region_ranks.value.push({
//      id: region.id,
//      rank: region.rank,
//      name: region.name,
//    });
//  });
//}

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

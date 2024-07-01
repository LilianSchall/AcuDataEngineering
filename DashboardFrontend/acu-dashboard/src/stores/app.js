// Utilities
import { defineStore } from "pinia";

// import {
//   getRegionIds,
//   getExerciseIds,
//   getExerciseAverageScore,
//   getExerciseNbAlerts,
//   getRegionAverageScore,
//   getRegionNbAlerts,
// } from "@/utils/false_data_generator";
import {
  getRegionIds,
  getExerciseIds,
  getExerciseAverageScore,
  getExerciseNbAlerts,
  getRegionAverageScore,
  getRegionNbAlerts,
} from "@/services/api";

export const useAppStore = defineStore("app", {
  state: () => ({
    regions: {},
    exercises: {},
    exercises_scores: {},
    exercises_nb_alerts: {},
    regions_scores: {},
    regions_nb_alerts: {},
    selectedExercise: 0,
    selectedRegion: 0,
  }),
  actions: {
    async getRegions() {
      const regions = await getRegionIds();
      console.log("regions", regions);
      this.regions = regions;
    },
    async updateRegionRanks(exerciseId = 0, by_score = true) {
      const exercises_scores = await getExerciseAverageScore(exerciseId);
      console.log('ex_sc', exercises_scores);
      this.exercises_scores = exercises_scores;
      const exercises_nb_alerts = await getExerciseNbAlerts(exerciseId);
      this.exercises_nb_alerts = exercises_nb_alerts;

      if (by_score) {
        // Sort by score
        exercises_scores.sort((a, b) => b.score - a.score);
        
        let ranks = exercises_scores.map((region) => region.id_region);

        // We need to add the rank to the regions object
        this.regions.forEach((region) => {
          region.rank = ranks.indexOf(region.id) + 1;
        });
      } else {
        // Sort by number of alerts
        exercises_nb_alerts.sort((a, b) => b.nb_alert - a.nb_alert);

        let ranks = exercises_nb_alerts.map((region) => region.id_region);

        // We need to add the rank to the regions object
        this.regions.forEach((region) => {
          region.rank = ranks.indexOf(region.id) + 1;
        });
      }
    },
    async getExercises() {
      const exercises = await getExerciseIds();
      this.exercises = exercises;
    },
    async fetchRegionsScores(regionId = 0) {
      const regions_scores = await getRegionAverageScore(regionId);
      this.regions_scores = regions_scores;
    },
    async fetchRegionsNbAlerts(regionId = 0) {
      const regions_nb_alerts = await getRegionNbAlerts(regionId);
      this.regions_nb_alerts = regions_nb_alerts;
    },
  },
});


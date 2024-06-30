// Utilities
import { defineStore } from 'pinia'

import { getRegionIds, getExerciseIds, getExerciseAverageScore, getRegionAverageScore } from '@/utils/false_data_generator'

export const useAppStore = defineStore('app', {
  state: () => ({
    regions: {},
    exercises: {},
    exercises_scores: {},
    regions_scores: {},
    selectedExercise: 0,
    selectedRegion: 0,
  }),
  actions: {
    getRegions() {
      const regions = getRegionIds()
      this.regions = regions;
    },
    updateRegionRanks(exerciseId = 0) {
      const exercises_scores = getExerciseAverageScore(exerciseId);

      this.exercises_scores = exercises_scores;

      // Sort by score
      exercises_scores.sort((a, b) => b.score - a.score);

      let ranks = exercises_scores.map(
        (region) => region.id_region
      )

      // We need to add the rank to the regions object
      this.regions.forEach((region) => {
        region.rank = ranks.indexOf(region.id) + 1;
      });
    },
    getExercises() {
      const exercises = getExerciseIds();
      this.exercises = exercises;
    },
    fetchRegionsScores(regionId = 0) {
      const regions_scores = getRegionAverageScore(regionId);
      this.regions_scores = regions_scores;
    }
  }
})

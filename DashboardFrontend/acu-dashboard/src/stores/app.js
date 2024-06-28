// Utilities
import { defineStore } from 'pinia'

import { getRegionIds } from '@/services/api.js'

export const useAppStore = defineStore('app', {
  state: () => ({
    hello: 'world',
    regions: {},
    exercises: {},
    regionScores: {},
    regionAlerts: {},
    exerciseScores: {},
    exerciseAlerts: {},
  }),
  actions: {
    fetchRegionsIds() {
      // Use the functions from the api.js file here
      const regions = getRegionIds()
      this.regions = regions;
    },
  }
})

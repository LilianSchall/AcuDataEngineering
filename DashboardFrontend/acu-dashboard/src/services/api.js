import axios from 'axios';

const api = axios.create({
  //baseURL: '',
});

const getRegionIds = async () => {
    const response = await api.get('/regions_ids');
    return response.data;
}

const getRegionAverageScore = async (regionId) => {
    if (!regionId) {
        regionId = 0;
    }
    const response = await api.get(`/region_score/${regionId}`);
    return response.data;
}

const getRegionNbAlerts = async (regionId) => {
    if (!regionId) {
        regionId = 0;
    }
    const response = await api.get(`/region_nb_alert/${regionId}`);
    return response.data;
}

const getExerciseIds = async () => {
    const response = await api.get('/exercises_ids');
    return response.data;
}

const getExerciseAverageScore = async (exerciseId) => {
    if (!exerciseId) {
        exerciseId = 0;
    }
    const response = await api.get(`/exercise_score/${exerciseId}`);
    return response.data;
}

const getExerciseNbAlerts = async (exerciseId) => {
    if (!exerciseId) {
        exerciseId = 0;
    }
    const response = await api.get(`/exercise_nb_alert/${exerciseId}`);
    return response.data;
}

export {
    getRegionIds,
    getRegionAverageScore,
    getRegionNbAlerts,
    getExerciseIds,
    getExerciseAverageScore,
    getExerciseNbAlerts,
}
import axios from 'axios';

const api = axios.create({
	baseURL: 'http://localhost:81',
});


const getRegionIds = async () => {

    /*
    {
        "regions": [
            {
                "id": 1,
                "name": "Grand Est"
            },
            {
                "id": 2,
                "name": "Ile-de-France"
            },
            (...)
        ]
    }
    */

    const response = await api.get('/region_ids');

    console.log('res', response)

    return response.data['regions'];
}

const getRegionAverageScore = async (regionId) => {
    /*
    {
        "average_score": [
            {
                "id_exercise": 1,
                "score": 50
            },
            {
                "id_exercise": 2,
                "score": 60
            },
            (...)
        ]
    }
    */

    if (!regionId) {
        regionId = 0;
    }
    const response = await api.get(`/region_score?id=${regionId}`);
    return response.data['average_score'];
}

const getRegionNbAlerts = async (regionId) => {
    /*
    {
        "nb_alert": [
            {
                "id_exercise": 1,
                "nb_alert": 50
            },
            {
                "id_exercise": 2,
                "nb_alert": 60
            },
            (...)
        ]
    }
    */
    if (!regionId) {
        regionId = 0;
    }
    const response = await api.get(`/region_nb_alert?id=${regionId}`);
    return response.data['nb_alert'];
}

const getExerciseIds = async () => {
    /*
    {
        "exercises": [
        {
            "id": 1
            "name": "80cols",
            "difficulty": "easy",
        },
        {
            "id": 2
            "name": "80cols_grep",
            "difficulty": "easy",
        },
        (...)
        ]
    }
    
    */

    const response = await api.get('/exercises_ids');
    return response.data['exercises'];
}

const getExerciseAverageScore = async (exerciseId) => {
    /*
    {
        "average_score": [
            {
                "id_region": 1,
                "score": 50
            },
            { 
                "id_region": 2,
                "score": 60
            }
        ]
    }
    */

    if (!exerciseId) {
        exerciseId = 0;
    }
    const response = await api.get(`/exercises_score?id=${exerciseId}`);
    return response.data['average_score'];
}

const getExerciseNbAlerts = async (exerciseId) => {
        /*
    {
        "nb_alert": [
            {
                "id_region": 1,
                "nb_alert": 50
            },
            { 
                "id_region": 2,
                "nb_alert": 60
            }
        ]
    }
    */
    if (!exerciseId) {
        exerciseId = 0;
    }
    const response = await api.get(`/exercises_nb_alert?id=${exerciseId}`);
    return response.data['nb_alert'];
}

export {
    getRegionIds,
    getRegionAverageScore,
    getRegionNbAlerts,
    getExerciseIds,
    getExerciseAverageScore,
    getExerciseNbAlerts,
}

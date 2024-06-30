from flask import request, jsonify, abort
import os

from .tools import get_db, logger

# GET /regions_ids
# Returns a map of all region with key the id, and value the name of the region
# Assume that the table "regions" in the postgres database exists
# Returns ID, name in JSON
# In format:
# {
# "regions": [
#   {
#       "id": 1,
#       "name": "Grand Est",
#   },
# ]
# }
def get_region_ids():
    conn = get_db()
    cur = conn.cursor()
    logger("connected to database")

    cur.execute('SELECT * FROM regions')

    regions = cur.fetchall()
    cur.close()
    conn.close()
    logger("disconnected from database")

    regions_list = [{"id": region[0], "name": region[1]} for region in regions]
    logger(regions_list)

    return jsonify({"regions": regions_list})

# GET /region_score/{id}
# OU
# GET /region_score/?id={id}
# Param id = the region id (from 1 to 13)
# if id is missing or id = 0, get data from whole France
# - return for the given region the list of average score for all exercises
# Returns ID, score in JSON
# In format:
# {
#   "average_score": [
#       {
#           "id_exercice": 1,
#           "score": 50,
#       },
#   ]
# }
def get_region_average_score():
    args = request.args
    if 'id' in args:
        id = int(args['id'])

    conn = get_db()
    cur = conn.cursor()

    if id is None or id == 0:
        cur.execute('SELECT exercise_id, average_score FROM analytics')
    else:
        cur.execute('SELECT exercise_id, average_score FROM analytics WHERE region_id = %s AND MAX(created_at)', (id))

    averages = cur.fetchall()
    cur.close()
    conn.close()

    averages_list = [{'id_exercice': average[0], 'score': average[1]} for average in averages]

    return jsonify({"average_score": averages_list})

# GET /region_nb_alert
# Param id = the region id (from 1 to 13)
# if id is missing or id = 0, get data from whole France
# - return for the given region the list of number of alerts for all exercises
# Returns ID, nb_alert in JSON
# In format:
# {
#   "nb_alert": [
#       {
#           "id_exercice": 1,
#           "nb_alert": 50,
#       },
#   ]
# }
def get_region_nb_alert():
    args = request.args
    pass

# GET /exercises_ids
# Returns a map of all exercise with key the id, and value the name of the exercise
# Assume that the table "exercises" in the postgres database exists
# Returns ID, name, difficulty in JSON
# In format:
# {
#   "exercises": [
#       {
#           "id": 1,
#           "name": "80cols",
#           "difficulty": "easy",
#       },
#   ]
# }
def get_exercise_ids():
    conn = get_db()
    cur = conn.cursor()

    cur.execute('SELECT * FROM exercises')

    exercises = cur.fetchall()
    cur.close()
    conn.close()

    exercises_list = [{"id": exercise[0], "name": exercise[1], "difficulty": exercise[2]} for exercise in exercises]

    return jsonify({"exercises": exercises_list})

# GET /exercise_score
# Param id = the exercise id
# if id is missing or id = 0, get data from whole France
# - returns for the given exercise the list of average score per region, ponderated by difficulty.
# - ponderate means based on difficulty
# Returns region_id, score in JSON
# In format:
# {
#   "average_score": [
#       {
#           "id_region": 1,
#           "score": 50,
#       },
#   ]
# }
def get_exercise_average_score():
    args = request.args
    if 'id' in args:
        id = int(args['id'])

    conn = get_db()
    cur = conn.cursor()

    if id is None or id == 0:
        cur.execute('SELECT region_id, average_score FROM analytics')
    else:
        cur.execute('SELECT region_id, average_score FROM analytics WHERE exercise_id = %s AND MAX(created_at)', (id))

    averages = cur.fetchall()
    cur.close()
    conn.close()
            
    averages_list = [{'id_exercice': average[0], 'score': average[1]} for average in averages]

    return jsonify({"average_score": averages_list})

# GET /exercise_nb_alert
# Param id = the exercise id
# if id is missing or equal to 0, get data from whole France
# - return for the given exercise the list of number of alerts for all regions.
# - ponderate means based on difficulty
# Returns region_id, nb_alert in JSON
# In format:
# {
#   "nb_alert": [
#       {
#           "id_region": 1,
#           "nb_alert": 50,
#       },
#   ]
# }
def get_exercise_nb_alert():
    args = request.args
    pass

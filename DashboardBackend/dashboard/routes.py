from flask import request, jsonify, abort
import os
import sys

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
    with conn.cursor() as cur:
        cur.execute('SELECT * FROM regions')
        regions = cur.fetchall()

    regions_list = [{"id": region[0], "name": region[1]} for region in regions]
    print(regions_list, file=sys.stderr)

    response = jsonify({"regions": regions_list})
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response

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
#           "id_exercise": 1,
#           "score": 50,
#       },
#   ]
# }
def get_region_average_score():
    args = request.args
    if 'id' in args:
        id = int(args['id'])
    else:
        id = None

    conn = get_db()
    cur = conn.cursor()

    if id is None or id == 0:
        cur.execute('''SELECT exercise_id, region_id, AVG(average_score) AS average_score
                    FROM analytics a
                    WHERE created_at = (
                        SELECT MAX(created_at)
                        FROM analytics
                        WHERE region_id = a.region_id
                        AND exercise_id = a.exercise_id
                    )
                    GROUP BY exercise_id''')
    else:
        cur.execute('''SELECT exercise_id, region_id, average_score
                    FROM analytics a
                    WHERE region_id = %s
                    AND created_at = (
                        SELECT MAX(created_at)
                        FROM analytics
                        WHERE region_id = a.region_id
                        AND exercise_id = a.exercise_id
                    )''', (id,))

    averages = cur.fetchall()
    cur.close()

    print(averages, file=sys.stderr)
    averages_list = [{'id_exercise': average[0], 'score': average[2]} for average in averages]
    print(averages_list, file=sys.stderr)

    response = jsonify({"average_score": averages_list})
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response


# GET /region_nb_alert
# Param id = the region id (from 1 to 13)
# if id is missing or id = 0, get data from whole France
# - return for the given region the list of number of alerts for all exercises
# Returns ID, nb_alert in JSON
# In format:
# {
#   "nb_alert": [
#       {
#           "id_exercise": 1,
#           "nb_alert": 50,
#       },
#   ]
# }
def get_region_nb_alert():
    args = request.args
    if 'id' in args:
        id = int(args['id'])
    else:
        id = None
    
    conn = get_db()
    cur = conn.cursor()

    if id is None or id == 0:
        cur.execute('''SELECT exercise_id, region_id, SUM(nb_alert) as nb_alert
                    FROM analytics a
                    WHERE created_at = (
                        SELECT MAX(created_at)
                        FROM analytics
                        WHERE region_id = a.region_id
                        AND exercise_id = a.exercise_id
                    )
                    GROUPBY exercise_id''')
    else:
        cur.execute('''SELECT exercise_id, region_id, nb_alert
                    FROM analytics a
                    WHERE region_id = %s
                    AND created_at = (
                        SELECT MAX(created_at)
                        FROM analytics
                        WHERE region_id = a.region_id
                        AND exercise_id = a.exercise_id
                    )''', (id,))

    alerts = cur.fetchall()
    cur.close()

    if id is None or id == 0:
        total_alerts = 0
        for alert in alerts:
            total_alerts += alert[1]
        alerts = [[0, total_alerts]]

    print(alerts, file=sys.stderr)
    alerts_list = [{'id_exercise': alert[0], 'nb_alert': alert[2]} for alert in alerts]
    print(alerts_list, file=sys.stderr)

    response = jsonify({"nb_alert": alerts_list})
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response

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
    with conn.cursor() as cur:
        cur.execute('SELECT * FROM exercises')
        exercises = cur.fetchall()

    print(exercises, file=sys.stderr)
    exercises_list = [{"id": exercise[0], "name": exercise[1], "difficulty": exercise[2]} for exercise in exercises]
    print(exercises_list, file=sys.stderr)

    response = jsonify({"exercises": exercises_list})
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response

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
    else:
        id = None

    conn = get_db()
    cur = conn.cursor()

    if id is None or id == 0:
        cur.execute('''SELECT region_id, exercise_id, AVG(average_score) AS average_score
                    FROM analytics a
                    WHERE created_at = (
                        SELECT MAX(created_at)
                        FROM analytics
                        WHERE region_id = a.region_id
                        AND exercise_id = a.exercise_id
                    ) GROUPBY region_id''')
    else:
        cur.execute('''SELECT region_id, exercise_id, average_score
                    FROM analytics a
                    WHERE exercise_id = %s
                    AND created_at = (
                        SELECT MAX(created_at)
                        FROM analytics
                        WHERE region_id = a.region_id
                        AND exercise_id = a.exercise_id
                    )''', (id,))

    averages = cur.fetchall()
    cur.close()

    if id is None or id == 0:
        new_score = 0
        for average in averages:
            new_score += average[1]
        new_score /= len(averages)
        averages = [[0, new_score]]

    print(averages, file=sys.stderr)        
    averages_list = [{'id_region': average[0], 'score': average[2]} for average in averages]
    print(averages_list, file=sys.stderr)

    response = jsonify({"average_score": averages_list})
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response

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
    if 'id' in args:
        id = int(args['id'])
    else:
        id = None
    
    conn = get_db()
    cur = conn.cursor()

    if id is None or id == 0:
        cur.execute('''SELECT region_id, exercise_id, SUM(nb_alert) as nb_alert
                    FROM analytics a
                    WHERE created_at = (
                        SELECT MAX(created_at)
                        FROM analytics
                        WHERE region_id = a.region_id
                        AND exercise_id = a.exercise_id
                    )''')
    else:
        cur.execute('''SELECT region_id, exercise_id, nb_alert
                    FROM analytics a
                    WHERE exercise_id = %s
                    AND created_at = (
                        SELECT MAX(created_at)
                        FROM analytics
                        WHERE region_id = a.region_id
                        AND exercise_id = a.exercise_id
                    )''', (id,))

    alerts = cur.fetchall()
    cur.close()

    print(alerts, file=sys.stderr)
    alerts_list = [{'id_region': alert[0], 'nb_alert': alert[2]} for alert in alerts]
    print(alerts_list, file=sys.stderr)

    response = jsonify({"nb_alert": alerts_list})
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response

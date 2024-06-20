from flask import request, abort
import os

from .tools import get_db, logger



# GET /regions_ids
# Returns a map of all region with key the id, and value the name of the region
# Assume that the table "regions" in the postgres database exists
def get_region_ids():
    pass


# GET /region_score
# Param id = the region id (from 1 to 13)
# if id is missing or id = 0, get data from whole France
# - return for the given region the list of average score for all exercises
def get_region_average_score():
    args = request.args
    pass


# GET /region_nb_alert
# Param id = the region id (from 1 to 13)
# if id is missing or id = 0, get data from whole France
# - return for the given region the list of number of alerts for all exercises
def get_region_nb_alert():
    args = request.args
    pass


# GET /exercises_ids
# Returns a map of all exercise with key the id, and value the name of the exercise
# Assume that the table "exercises" in the postgres database exists
def get_exercise_ids():
    pass


# GET /exercise_score
# Param id = the exercise id
# - returns for the given exercise the list of average score per region.
def get_exercise_average_score():
    pass

# GET /exercise_nb_alert
# Param id = the exercise id 
# - return for the given exercise the list of number of alerts for all regions
def get_exercise_nb_alert():
    args = request.args
    pass

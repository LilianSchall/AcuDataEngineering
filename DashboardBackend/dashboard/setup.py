from flask import Flask
import logging

from . import routes

app = Flask(__name__)

app.add_url_rule("/region_ids", view_func=routes.get_region_ids, methods=["GET"])
app.add_url_rule("/region_score", view_func=routes.get_region_average_score, methods=["GET"])
app.add_url_rule("/region_nb_alert", view_func=routes.get_region_nb_alert, methods=["GET"])
app.add_url_rule("/exercises_ids", view_func=routes.get_exercise_ids, methods=["GET"])
app.add_url_rule("/exercises_score", view_func=routes.get_exercise_average_score, methods=["GET"])
app.add_url_rule("/exercises_nb_alert", view_func=routes.get_exercise_nb_alert, methods=["GET"])

if __name__ != "__main__":
    # Do not mind, it's for production mode (containerized)
    gunicorn_logger = logging.getLogger("gunicorn.error")
    app.logger.handlers = gunicorn_logger.handlers
    app.logger.setLevel(gunicorn_logger.level)

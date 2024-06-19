from flask import Flask
import logging

from . import routes

app = Flask(__name__)

app.add_url_rule("/", view_func=routes.index, methods=["GET"])

if __name__ != "__main__":
    # Do not mind, it's for production mode (containerized)
    gunicorn_logger = logging.getLogger("gunicorn.error")
    app.logger.handlers = gunicorn_logger.handlers
    app.logger.setLevel(gunicorn_logger.level)

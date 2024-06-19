from flask import request, abort
import os

from .tools import get_db, logger


def index():
    args = request.args

    # TODO: This is a just an example

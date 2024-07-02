import os


def fetch_env(env_name):
    value = os.getenv(env_name)
    if not value:
        print(env_name + " environment variable is not set.")
        exit(1)
    return value

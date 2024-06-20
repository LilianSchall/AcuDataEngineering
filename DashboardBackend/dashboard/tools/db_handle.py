import psycopg2


from .env_fetcher import fetch_env

__db = None

def get_db():
    global __db

    if __db != None:
        return __db
    
    db_host = fetch_env("DB_HOST")
    db_name = fetch_env("DB_NAME")
    db_user = fetch_env("DB_USER")
    db_password = fetch_env("DB_PASSWORD")
    db_port = fetch_env("DB_PORT")

    __db = psycopg2.connect(
        host=db_host, database=db_name, user=db_user, password=db_password, port=db_port
    )

    return __db

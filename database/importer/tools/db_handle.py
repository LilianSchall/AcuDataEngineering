import psycopg2


from .env_fetcher import fetch_env

__db = None

def get_db():
    global __db

    if __db != None:
        return __db
    
    db_host = "localhost" 
    db_name = fetch_env("POSTGRES_DB")
    db_user = fetch_env("POSTGRES_USER")
    db_password = fetch_env("POSTGRES_PASSWORD")
    db_port = "5432"

    __db = psycopg2.connect(
        host=db_host, database=db_name, user=db_user, password=db_password, port=db_port
    )

    return __db

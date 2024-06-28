#!/bin/bash

set -e
FULL_PATH="/docker-entrypoint-initdb.d/scripts";

PSQL_SHORT()
{
    psql --dbname "$POSTGRES_DB" --username "$POSTGRES_USER" "$@";
}

connect_to_db()
{
    echo "Connection to db $1";
    psql --dbname "$1" --username "$POSTGRES_USER" -tc "$2";
}

create_database_if_not_exist()
{
    sql_script="$1";
    database_name=$(echo "$1" | cut -d '/' -f4 | cut -d '.' -f1)
    PSQL_SHORT -tc "SELECT 1 FROM pg_database WHERE datname = $database_name" \
        | grep -q 1 || PSQL_SHORT -a -f "$sql_script";
}

create_user_if_not_exist()
{
    user_to_create="$1";
    user_password="$2";
    granted_db="$3";
    PSQL_SHORT -tc "SELECT 1 FROM pg_user WHERE usename = '$user_to_create'" \
        | grep -q 1 \
        || PSQL_SHORT -c "CREATE USER $user_to_create WITH PASSWORD '$user_password';
            GRANT ALL PRIVILEGES ON DATABASE $granted_db TO $user_to_create;"
}

grant_user_ownership()
{
    user="$1";
    database="$2";

    connect_to_db "$database" "ALTER DATABASE $database OWNER TO $user";
}

grant_user_access()
{
    user="$1";
    database="$2";

    connect_to_db "$database" "GRANT ALL ON ALL TABLES IN SCHEMA public TO $user";
    connect_to_db "$database" "GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO $user";
    connect_to_db "$database" "GRANT ALL ON ALL FUNCTIONS IN SCHEMA public TO $user";
}

create_database_if_not_exist "$FULL_PATH/acu_infra.sql";

create_user_if_not_exist "analytics_service" "$ANALYTICS_SERVICE_PASSWORD" "acu_infra";
create_user_if_not_exist "dashboard_service" "$DASHBOARD_SERVICE_PASSWORD" "acu_infra";

grant_user_access "analytics_service" "acu_infra";
grant_user_access "dashboard_service" "acu_infra";

# import json in database 
python /app/main.py &

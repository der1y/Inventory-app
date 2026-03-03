export PGPASSWORD='@Djbook2024'
BASEDIR=$(dirname "$0")
DATABASE=inventory_application
PSQL=/usr/local/opt/postgresql@18/bin/psql
CREATEDB=/usr/local/opt/postgresql@18/bin/createdb

"$PSQL" -v ON_ERROR_STOP=1 -U postgres -f "$BASEDIR/dropdb.sql" &&
"$CREATEDB" -U postgres "$DATABASE" &&
"$PSQL" -v ON_ERROR_STOP=1 -U postgres -d "$DATABASE" -f "$BASEDIR/schema.sql" &&
"$PSQL" -v ON_ERROR_STOP=1 -U postgres -d "$DATABASE" -f "$BASEDIR/data.sql" &&
"$PSQL" -v ON_ERROR_STOP=1 -U postgres -d "$DATABASE" -f "$BASEDIR/user.sql"

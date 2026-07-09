#!/usr/bin/env bash
# ******************************************************************************
# Rebuilds the database: drops it, recreates it, then loads schema, data, users.
#
# Cross-platform (macOS + Windows Git Bash). Everything machine-specific is an
# overridable environment variable, so the committed script needs no edits:
#
#   PSQL / CREATEDB  paths to the tools     (default: found on PATH)
#   DATABASE         database name          (default: inventory_application)
#   PGUSER           superuser to run as    (default: postgres)
#   PGHOST           server host            (default: localhost)
#   PGPASSWORD       password for PGUSER    (default: none -> use ~/.pgpass or prompt)
#
# Examples:
#   ./create.sh
#   PGPASSWORD='secret' ./create.sh
#   PSQL='/usr/local/opt/postgresql@18/bin/psql' \
#     CREATEDB='/usr/local/opt/postgresql@18/bin/createdb' ./create.sh
# ******************************************************************************
set -euo pipefail

BASEDIR=$(dirname "$0")

# Prefer whatever is on PATH; override with the PSQL/CREATEDB env vars if needed.
PSQL=${PSQL:-psql}
CREATEDB=${CREATEDB:-createdb}

# Connection settings. psql/createdb read PGUSER/PGHOST/PGPASSWORD from the
# environment natively, so we just export what's set and pass -U/-h explicitly.
DATABASE=${DATABASE:-inventory_application}
export PGUSER=${PGUSER:-postgres}
export PGHOST=${PGHOST:-localhost}
[ -n "${PGPASSWORD:-}" ] && export PGPASSWORD

"$PSQL"     -v ON_ERROR_STOP=1 -f "$BASEDIR/dropdb.sql"
"$CREATEDB" "$DATABASE"
"$PSQL"     -v ON_ERROR_STOP=1 -d "$DATABASE" -f "$BASEDIR/schema.sql"
"$PSQL"     -v ON_ERROR_STOP=1 -d "$DATABASE" -f "$BASEDIR/data.sql"
"$PSQL"     -v ON_ERROR_STOP=1 -d "$DATABASE" -f "$BASEDIR/user.sql"

echo "=== Database '$DATABASE' rebuilt successfully ==="

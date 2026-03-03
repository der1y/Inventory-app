-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

DO
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'inventory_application_owner') THEN
        CREATE USER inventory_application_owner WITH PASSWORD 'inventoryapplication';
    ELSE
        ALTER USER inventory_application_owner WITH PASSWORD 'inventoryapplication';
    END IF;
END
$$;

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO inventory_application_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO inventory_application_owner;

DO
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'inventory_application_appuser') THEN
        CREATE USER inventory_application_appuser WITH PASSWORD 'inventoryapplication';
    ELSE
        ALTER USER inventory_application_appuser WITH PASSWORD 'inventoryapplication';
    END IF;
END
$$;

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO inventory_application_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO inventory_application_appuser;

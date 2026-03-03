-- **************************************************************
-- This script destroys the database and associated users
-- **************************************************************

-- The following line terminates any active connections to the database so that it can be destroyed
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'inventory_application';

DROP DATABASE IF EXISTS inventory_application;

DROP USER IF EXISTS inventory_application_owner;
DROP USER IF EXISTS inventory_application_appuser;

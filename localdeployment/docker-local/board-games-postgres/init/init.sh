psql -v ON_ERROR_STOP=1 -U postgres -d postgres -c "CREATE DATABASE \"board-games\"";

psql -v ON_ERROR_STOP=1 -U postgres -d postgres -c "CREATE USER johndoe WITH CREATEDB PASSWORD 'passw0rd'";

psql -v ON_ERROR_STOP=1 -U postgres -d postgres -c "GRANT ALL PRIVILEGES ON DATABASE \"board-games\" TO johndoe";

psql -v ON_ERROR_STOP=1 -U postgres -d postgres -c "ALTER USER johndoe WITH SUPERUSER";
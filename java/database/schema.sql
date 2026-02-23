BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    sku SERIAL,
    name varchar(255) NOT NULL,
    category varchar(255) NOT NULL,
    default_bottle_size int NOT NULL,

);

COMMIT TRANSACTION;

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
    product_id SERIAL,
    sku varchar(50) UNIQUE,
    name varchar(50) NOT NULL,
    category_id SERIAL NOT NULL UNIQUE, --Need to make category table
    default_bottle_ml int NOT NULL,
    is_active boolean,
    CONSTRAINT PK_product PRIMARY KEY (product_id)
);

CREATE TABLE product_purchase (
    purchase_id SERIAL,
    product_id int NOT NULL,
    vendor_id int NOT NULL -- Need to make vendor table
    purchased_at TIMESTAMP,

)

COMMIT TRANSACTION;

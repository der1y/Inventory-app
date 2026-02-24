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
    category_id SERIAL NOT NULL UNIQUE,
    default_bottle_ml int NOT NULL,
    is_active boolean,
    CONSTRAINT PK_product PRIMARY KEY (product_id)
);

CREATE TABLE product_purchases (
    purchase_id SERIAL,
    product_id int NOT NULL,
    vendor_id int NOT NULL,
    purchased_at TIMESTAMP,
    package_ml int,
    package_cost_cents float,
    notes varchar(255),
    CONSTRAINT PK_purchase PRIMARY KEY (purchase_id),
    CONSTRAINT FK_product_purchases_product FOREIGN KEY (product_id) REFERENCES products (product_id),
);

CREATE TABLE categories (
    category_id SERIAL,
    name varchar(50),
    product_id int,
    CONSTRAINT PK_category PRIMARY KEY (category_id),
    CONSTRAINT FK_category_product FOREIGN KEY (product_id) REFERENCES products (product_id),
);

CREATE TABLE vendors (
    vendor_id SERIAL,
    name varchar(50),
    purchase_id int,
    CONSTRAINT PK_vendor PRIMARY KEY (vendor_id),
    CONSTRAINT FK_vendor_purchase FOREIGN KEY (purchase_id) REFERENCES product_purchases (purchase_id),
);

CREATE TABLE inventory (
    inventory_id SERIAL,
    product_id int,
    purchase_id int,
    received_at timestamp,
    full_ml int,
    remaining_ml int,
    status bottle_status,
);

CREATE TYPE bottle_status AS ENUM ('OPENED', 'UNOPENED', 'EMPTY');

COMMIT TRANSACTION;

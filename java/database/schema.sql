BEGIN;

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS product_purchase CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS vendors CASCADE;
DROP TABLE IF EXISTS vendor_product CASCADE;
DROP TABLE IF EXISTS vendor_purchase CASCADE;
DROP TABLE IF EXISTS category_product CASCADE;
DROP TABLE IF EXISTS inventory CASCADE;
DROP TABLE IF EXISTS locations CASCADE;

CREATE TABLE users
(
    user_id       SERIAL,
    username      varchar(50)  NOT NULL UNIQUE,
    password_hash varchar(255) NOT NULL,
    role          varchar(50)  NOT NULL,
    CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE categories
(
    category_id SERIAL,
    name        varchar(50) NOT NULL UNIQUE,
    CONSTRAINT PK_category PRIMARY KEY (category_id)
);

CREATE TABLE products
(
    product_id        SERIAL,
    sku               varchar(50) UNIQUE,
    name              varchar(50) NOT NULL,
    category_id       int         NOT NULL,
    default_bottle_ml int         NOT NULL,
    is_active         boolean     NOT NULL DEFAULT true,
    CONSTRAINT PK_product PRIMARY KEY (product_id),
    CONSTRAINT FK_category_product FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

CREATE TABLE vendors
(
    vendor_id SERIAL,
    name      varchar(50) NOT NULL UNIQUE,
    CONSTRAINT PK_vendor PRIMARY KEY (vendor_id)
);

CREATE TABLE vendor_product
(
    vendor_product_id SERIAL,
    vendor_id         int NOT NULL,
    product_id        int NOT NULL,
    CONSTRAINT PK_vendor_product PRIMARY KEY (vendor_product_id),
    CONSTRAINT FK_vendor_product_vendor FOREIGN KEY (vendor_id) REFERENCES vendors (vendor_id),
    CONSTRAINT FK_vendor_product_product FOREIGN KEY (product_id) REFERENCES products (product_id)
);

CREATE TABLE product_purchase
(
    purchase_id        SERIAL,
    product_id         int       NOT NULL,
    purchased_at       TIMESTAMP NOT NULL DEFAULT now(),
    package_ml         int       NOT NULL,
    package_cost_cents int       NOT NULL,
    notes              varchar(255),
    CONSTRAINT PK_purchase PRIMARY KEY (purchase_id),
    CONSTRAINT FK_product_purchase_product FOREIGN KEY (product_id) REFERENCES products (product_id)
);

CREATE TABLE vendor_purchase
(
    vendor_purchase_id SERIAL,
    vendor_id          int NOT NULL,
    purchase_id        int NOT NULL,
    CONSTRAINT PK_vendor_purchase PRIMARY KEY (vendor_purchase_id),
    CONSTRAINT FK_vendor_purchase_vendor FOREIGN KEY (vendor_id) REFERENCES vendors (vendor_id),
    CONSTRAINT FK_vendor_purchase_purchase FOREIGN KEY (purchase_id) REFERENCES product_purchase (purchase_id)
);
CREATE TABLE locations
(
    location_id SERIAL,
    name        varchar(50) UNIQUE NOT NULL,
    CONSTRAINT PK_location PRIMARY KEY (location_id)
);

CREATE TABLE inventory
(
    inventory_id SERIAL,
    product_id   int       NOT NULL,
    location_id  int       NOT NULL,
    purchase_id  int       NOT NULL,
    received_at  timestamp NOT NULL DEFAULT now(),
    full_ml      int       NOT NULL,
    remaining_ml int       NOT NULL,
    CONSTRAINT PK_inventory PRIMARY KEY (inventory_id),
    CONSTRAINT FK_inventory_product FOREIGN KEY (product_id) REFERENCES products (product_id),
    CONSTRAINT FK_inventory_location FOREIGN KEY (location_id) REFERENCES locations (location_id),
    CONSTRAINT FK_inventory_purchase FOREIGN KEY (purchase_id) REFERENCES product_purchase (purchase_id),

    CONSTRAINT chk_inventory_remaining_range
        CHECK (remaining_ml >= 0 AND remaining_ml <= full_ml),
    CONSTRAINT chk_inventory_full_positive
        CHECK (full_ml > 0)
);

-- PRODUCTS
CREATE INDEX idx_products_category_active
    ON products (category_id, is_active);

-- PURCHASES (latest cost per product)
CREATE INDEX idx_product_purchase_product_date_desc
    ON product_purchase (product_id, purchased_at DESC);

-- INVENTORY (location-first browsing + summaries)
CREATE INDEX idx_inventory_location_product
    ON inventory (location_id, product_id);

CREATE INDEX idx_inventory_product
    ON inventory (product_id);

CREATE INDEX idx_inventory_purchase
    ON inventory (purchase_id);

-- VENDOR_PRODUCT (prevent duplicates + fast lookups)
CREATE UNIQUE INDEX ux_vendor_product_vendor_product
    ON vendor_product (vendor_id, product_id);

CREATE INDEX idx_vendor_product_product
    ON vendor_product (product_id);

-- VENDOR_PURCHASE (prevent duplicates + fast lookups)
CREATE UNIQUE INDEX ux_vendor_purchase_vendor_purchase
    ON vendor_purchase (vendor_id, purchase_id);

CREATE INDEX idx_vendor_purchase_purchase
    ON vendor_purchase (purchase_id);

COMMIT;

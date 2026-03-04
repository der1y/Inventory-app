BEGIN TRANSACTION;

-- the password for both users is "password"
INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

-- product categories
INSERT INTO categories (name) VALUES
    ('Whiskey'),
    ('Gin'),
    ('Vodka'),
    ('Tequila'),
    ('Rum'),
    ('Brandy'),
    ('Liqueur'),
    ('Aperitif'),
    ('Amaro'),
    ('Bitters');

INSERT INTO products (name, category_id, default_bottle_ml, is_active) VALUES
    ('Buffalo Trace Bourbon', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 750, true),
    ('Wild Turkey 101', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 750, true),
    ('Woodford Double Oaked', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 750, true),
    ('Makers Mark Bourbon', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 1000, true),
    ('Bulleit Bourbon', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 750, true),
    ('Laphroaig 10 Year', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 750, true),
    ('Michters US1 Rye', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 750, true),
    ('Jack Daniels Old No 7', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 1000, true),

    ('Tanqueray London Dry', (SELECT category_id FROM categories WHERE name = 'Gin'), 1000, true),
    ('Bombay Sapphire', (SELECT category_id FROM categories WHERE name = 'Gin'), 750, true),
    ('Hendricks Original', (SELECT category_id FROM categories WHERE name = 'Gin'), 750, true),
    ('Beefeater London Dry', (SELECT category_id FROM categories WHERE name = 'Gin'), 750, true),
    ('Plymouth Original Gin', (SELECT category_id FROM categories WHERE name = 'Gin'), 750, true),

    ('Titos Handmade Vodka', (SELECT category_id FROM categories WHERE name = 'Vodka'), 750, true),
    ('Grey Goose Original', (SELECT category_id FROM categories WHERE name = 'Vodka'), 750, true),
    ('Ketel One Vodka', (SELECT category_id FROM categories WHERE name = 'Vodka'), 1000, true),
    ('Svedka Vodka', (SELECT category_id FROM categories WHERE name = 'Vodka'), 750, true),
    ('Belvedere Vodka', (SELECT category_id FROM categories WHERE name = 'Vodka'), 750, true),

    ('Casamigos Blanco', (SELECT category_id FROM categories WHERE name = 'Tequila'), 750, true),
    ('El Jimador Blanco', (SELECT category_id FROM categories WHERE name = 'Tequila'), 750, true),
    ('Don Julio Reposado', (SELECT category_id FROM categories WHERE name = 'Tequila'), 750, true),
    ('Patron Silver', (SELECT category_id FROM categories WHERE name = 'Tequila'), 750, true),
    ('Espolon Anejo', (SELECT category_id FROM categories WHERE name = 'Tequila'), 750, true),
    ('Herradura Reposado', (SELECT category_id FROM categories WHERE name = 'Tequila'), 1000, true),

    ('Bacardi Superior White', (SELECT category_id FROM categories WHERE name = 'Rum'), 1000, true),
    ('Myers Original Dark', (SELECT category_id FROM categories WHERE name = 'Rum'), 750, true),
    ('Plantation XO 20th', (SELECT category_id FROM categories WHERE name = 'Rum'), 750, true),
    ('Don Q Cristal', (SELECT category_id FROM categories WHERE name = 'Rum'), 1000, true),
    ('Captain Morgan Spiced', (SELECT category_id FROM categories WHERE name = 'Rum'), 750, true),

    ('Hennessy VS Cognac', (SELECT category_id FROM categories WHERE name = 'Brandy'), 750, true),
    ('Remy Martin VSOP', (SELECT category_id FROM categories WHERE name = 'Brandy'), 750, true),
    ('Torres 10 Brandy', (SELECT category_id FROM categories WHERE name = 'Brandy'), 700, true),

    ('Cointreau Triple Sec', (SELECT category_id FROM categories WHERE name = 'Liqueur'), 750, true),
    ('Disaronno Amaretto', (SELECT category_id FROM categories WHERE name = 'Liqueur'), 750, true),
    ('Baileys Irish Cream', (SELECT category_id FROM categories WHERE name = 'Liqueur'), 750, true),
    ('Kahlua Coffee Liqueur', (SELECT category_id FROM categories WHERE name = 'Liqueur'), 1000, true),
    ('St Germain Elderflower', (SELECT category_id FROM categories WHERE name = 'Liqueur'), 750, true),
    ('Frangelico Hazelnut', (SELECT category_id FROM categories WHERE name = 'Liqueur'), 750, true),

    ('Aperol', (SELECT category_id FROM categories WHERE name = 'Aperitif'), 750, true),
    ('Campari', (SELECT category_id FROM categories WHERE name = 'Aperitif'), 1000, true),
    ('Lillet Blanc', (SELECT category_id FROM categories WHERE name = 'Aperitif'), 750, true),

    ('Amaro Montenegro', (SELECT category_id FROM categories WHERE name = 'Amaro'), 750, true),
    ('Averna Amaro', (SELECT category_id FROM categories WHERE name = 'Amaro'), 700, true),
    ('Fernet Branca', (SELECT category_id FROM categories WHERE name = 'Amaro'), 750, true),

    ('Angostura Aromatic Bitters', (SELECT category_id FROM categories WHERE name = 'Bitters'), 200, true),
    ('Peychauds Aromatic Bitters', (SELECT category_id FROM categories WHERE name = 'Bitters'), 100, true);

INSERT INTO vendors (vendor_id, name) VALUES
    ('1', 'Southern Glazers'),
    ('2', 'B-Side Selections'),
    ('3', 'RNDC'),
    ('4', 'Johnson Brothers'),
    ('5', 'Crossroads Vineyard');

-- random vendor -> product links
INSERT INTO vendor_product (vendor_id, product_id) VALUES
    (1, 1),
    (1, 3),
    (1, 9),
    (1, 19),
    (1, 33),
    (2, 2),
    (2, 11),
    (2, 15),
    (2, 25),
    (2, 41),
    (3, 5),
    (3, 14),
    (3, 21),
    (3, 29),
    (3, 44),
    (4, 6),
    (4, 13),
    (4, 22),
    (4, 30),
    (4, 45),
    (5, 8),
    (5, 17),
    (5, 24),
    (5, 37),
    (5, 46);


COMMIT TRANSACTION;

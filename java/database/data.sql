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

-- sample products with structured SKUs:
-- <CATEGORY>-<BRAND>-<ITEM>-<SIZE_ML>-<SEQ>
INSERT INTO products (sku, name, category_id, default_bottle_ml, is_active) VALUES
    ('WHSK-BF-BOUR-750-001', 'Buffalo Trace Bourbon', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 750, true),
    ('WHSK-WT-101-750-001', 'Wild Turkey 101', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 750, true),
    ('WHSK-WR-DOAK-750-001', 'Woodford Double Oaked', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 750, true),
    ('WHSK-MM-BRBN-1000-001', 'Makers Mark Bourbon', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 1000, true),
    ('WHSK-BL-BRBN-750-001', 'Bulleit Bourbon', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 750, true),
    ('WHSK-LP-10YR-750-001', 'Laphroaig 10 Year', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 750, true),
    ('WHSK-MH-RYE-750-001', 'Michters US1 Rye', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 750, true),
    ('WHSK-JD-OLD7-1000-001', 'Jack Daniels Old No 7', (SELECT category_id FROM categories WHERE name = 'Whiskey'), 1000, true),

    ('GIN-TN-LDN-1000-001', 'Tanqueray London Dry', (SELECT category_id FROM categories WHERE name = 'Gin'), 1000, true),
    ('GIN-BS-SAPP-750-001', 'Bombay Sapphire', (SELECT category_id FROM categories WHERE name = 'Gin'), 750, true),
    ('GIN-HD-ORB-750-001', 'Hendricks Original', (SELECT category_id FROM categories WHERE name = 'Gin'), 750, true),
    ('GIN-BF-LDN-750-001', 'Beefeater London Dry', (SELECT category_id FROM categories WHERE name = 'Gin'), 750, true),
    ('GIN-PLM-ORIG-750-001', 'Plymouth Original Gin', (SELECT category_id FROM categories WHERE name = 'Gin'), 750, true),

    ('VODK-TT-CLAS-750-001', 'Titos Handmade Vodka', (SELECT category_id FROM categories WHERE name = 'Vodka'), 750, true),
    ('VODK-GG-ORIG-750-001', 'Grey Goose Original', (SELECT category_id FROM categories WHERE name = 'Vodka'), 750, true),
    ('VODK-KO-CLAS-1000-001', 'Ketel One Vodka', (SELECT category_id FROM categories WHERE name = 'Vodka'), 1000, true),
    ('VODK-SV-80P-750-001', 'Svedka Vodka', (SELECT category_id FROM categories WHERE name = 'Vodka'), 750, true),
    ('VODK-BV-CLAS-750-001', 'Belvedere Vodka', (SELECT category_id FROM categories WHERE name = 'Vodka'), 750, true),

    ('TEQ-CS-BLAN-750-001', 'Casamigos Blanco', (SELECT category_id FROM categories WHERE name = 'Tequila'), 750, true),
    ('TEQ-EJ-BLAN-750-001', 'El Jimador Blanco', (SELECT category_id FROM categories WHERE name = 'Tequila'), 750, true),
    ('TEQ-DJ-REPO-750-001', 'Don Julio Reposado', (SELECT category_id FROM categories WHERE name = 'Tequila'), 750, true),
    ('TEQ-PT-SLVR-750-001', 'Patron Silver', (SELECT category_id FROM categories WHERE name = 'Tequila'), 750, true),
    ('TEQ-ES-ANJO-750-001', 'Espolon Anejo', (SELECT category_id FROM categories WHERE name = 'Tequila'), 750, true),
    ('TEQ-HR-REPO-1000-001', 'Herradura Reposado', (SELECT category_id FROM categories WHERE name = 'Tequila'), 1000, true),

    ('RUM-BC-WHT-1000-001', 'Bacardi Superior White', (SELECT category_id FROM categories WHERE name = 'Rum'), 1000, true),
    ('RUM-MY-DRK-750-001', 'Myers Original Dark', (SELECT category_id FROM categories WHERE name = 'Rum'), 750, true),
    ('RUM-PT-XO20-750-001', 'Plantation XO 20th', (SELECT category_id FROM categories WHERE name = 'Rum'), 750, true),
    ('RUM-DQ-CRYS-1000-001', 'Don Q Cristal', (SELECT category_id FROM categories WHERE name = 'Rum'), 1000, true),
    ('RUM-CM-SPCD-750-001', 'Captain Morgan Spiced', (SELECT category_id FROM categories WHERE name = 'Rum'), 750, true),

    ('BRDY-HN-VS-750-001', 'Hennessy VS Cognac', (SELECT category_id FROM categories WHERE name = 'Brandy'), 750, true),
    ('BRDY-RM-VSOP-750-001', 'Remy Martin VSOP', (SELECT category_id FROM categories WHERE name = 'Brandy'), 750, true),
    ('BRDY-TR-10Y-700-001', 'Torres 10 Brandy', (SELECT category_id FROM categories WHERE name = 'Brandy'), 700, true),

    ('LIQ-CT-TRPL-750-001', 'Cointreau Triple Sec', (SELECT category_id FROM categories WHERE name = 'Liqueur'), 750, true),
    ('LIQ-DS-AMRT-750-001', 'Disaronno Amaretto', (SELECT category_id FROM categories WHERE name = 'Liqueur'), 750, true),
    ('LIQ-BY-IRCR-750-001', 'Baileys Irish Cream', (SELECT category_id FROM categories WHERE name = 'Liqueur'), 750, true),
    ('LIQ-KH-COFF-1000-001', 'Kahlua Coffee Liqueur', (SELECT category_id FROM categories WHERE name = 'Liqueur'), 1000, true),
    ('LIQ-SG-ELDR-750-001', 'St Germain Elderflower', (SELECT category_id FROM categories WHERE name = 'Liqueur'), 750, true),
    ('LIQ-FR-HZNT-750-001', 'Frangelico Hazelnut', (SELECT category_id FROM categories WHERE name = 'Liqueur'), 750, true),

    ('APER-AP-ORIG-750-001', 'Aperol', (SELECT category_id FROM categories WHERE name = 'Aperitif'), 750, true),
    ('APER-CP-BITT-1000-001', 'Campari', (SELECT category_id FROM categories WHERE name = 'Aperitif'), 1000, true),
    ('APER-LB-BLNC-750-001', 'Lillet Blanc', (SELECT category_id FROM categories WHERE name = 'Aperitif'), 750, true),

    ('AMAR-MN-MONT-750-001', 'Amaro Montenegro', (SELECT category_id FROM categories WHERE name = 'Amaro'), 750, true),
    ('AMAR-AV-ORIG-700-001', 'Averna Amaro', (SELECT category_id FROM categories WHERE name = 'Amaro'), 700, true),
    ('AMAR-FB-MNTA-750-001', 'Fernet Branca', (SELECT category_id FROM categories WHERE name = 'Amaro'), 750, true),

    ('BIT-AN-AROM-200-001', 'Angostura Aromatic Bitters', (SELECT category_id FROM categories WHERE name = 'Bitters'), 200, true),
    ('BIT-PC-AROM-100-001', 'Peychauds Aromatic Bitters', (SELECT category_id FROM categories WHERE name = 'Bitters'), 100, true);

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

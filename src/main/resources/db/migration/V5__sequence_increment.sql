ALTER TABLE product ALTER COLUMN id RESTART WITH (
    SELECT MAX(id) + 1 FROM product
);

ALTER TABLE product_category ALTER COLUMN id RESTART WITH (
    SELECT MAX(id) + 1 FROM product_category
);

ALTER TABLE brand ALTER COLUMN id RESTART WITH (
    SELECT MAX(id) + 1 FROM brand
);
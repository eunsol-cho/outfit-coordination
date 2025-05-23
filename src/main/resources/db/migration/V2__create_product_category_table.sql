CREATE TABLE product_category (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    display_name VARCHAR(255) NOT NULL,
    display_order INT NOT NULL,
    deleted_at TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT UK_product_category_code UNIQUE (code)
);

CREATE INDEX idx_category_code_deleted_at ON product_category (code, deleted_at);

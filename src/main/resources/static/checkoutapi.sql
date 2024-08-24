DROP SCHEMA IF EXISTS postgres CASCADE;

CREATE SCHEMA postgres;

SET search_path TO postgres;

CREATE TABLE cart
(
    id                   SERIAL PRIMARY KEY,
    total_price          DOUBLE PRECISION DEFAULT 0,
    applied_promotion_id INT              DEFAULT 0,
    total_discount       DOUBLE PRECISION DEFAULT 0
);

CREATE TABLE item
(
    id          SERIAL PRIMARY KEY,
    category_id INT,
    seller_id   INT,
    price       DOUBLE PRECISION,
    quantity    INT,
    cart_id     INT,
    CONSTRAINT FK_CART FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE vas_item
(
    id          SERIAL PRIMARY KEY,
    category_id INT,
    seller_id   INT,
    price       DOUBLE PRECISION,
    quantity    INT,
    item_id     INT,
    CONSTRAINT FK_ITEM FOREIGN KEY (item_id) REFERENCES item (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO cart (total_price, applied_promotion_id, total_discount)
VALUES (0, 0, 0);
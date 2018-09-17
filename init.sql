USE skt;
DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id INT NOT NULL,
    name VARCHAR(255),
    description VARCHAR(255),
    PRIMARY KEY (id)
);

DELIMITER $$
CREATE PROCEDURE retrieve_products()
BEGIN
    SELECT * FROM products;
END $$

CREATE PROCEDURE create_product(
    IN p_id INT,
    IN p_name VARCHAR(255),
    IN p_description VARCHAR(255)
)
BEGIN
    INSERT INTO products (id, name, description)
        VALUES (p_id, p_name, p_description);
END $$
DELIMITER ;

USE skt;

DROP TABLE IF EXISTS products;

CREATE TABLE products (
  id          INT NOT NULL,
  name        VARCHAR(255),
  description VARCHAR(255),
  price       DECIMAL(8, 2),
  PRIMARY KEY (id)
);

DROP PROCEDURE IF EXISTS retrieve_products;
DROP PROCEDURE IF EXISTS create_product;

DELIMITER $$
CREATE PROCEDURE retrieve_products(
  OUT o_error_code TINYINT,
  OUT o_error_msg  VARCHAR(255))
  BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION SET o_error_code = 1, o_error_msg = 'An unexpected exception was found.';
    SET o_error_code = 0;
    SET o_error_msg = '';
    SELECT * FROM products;
  END $$

CREATE PROCEDURE create_product(
  IN  p_id          INT,
  IN  p_name        VARCHAR(255),
  IN  p_description VARCHAR(255),
  IN  p_price       DECIMAL(8, 2),
  OUT o_error_code  INT,
  OUT o_error_msg   VARCHAR(255)
)
  BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION SET o_error_code = 1, o_error_msg = 'An unexpected exception was found.';
    DECLARE EXIT HANDLER FOR 1062 SET o_error_code = 2, o_error_msg = 'Duplicate product ID.';
    SET o_error_code = 0;
    SET o_error_msg = '';
    INSERT INTO products (id, name, description, price) VALUES (p_id, p_name, p_description, p_price);
  END $$
DELIMITER ;

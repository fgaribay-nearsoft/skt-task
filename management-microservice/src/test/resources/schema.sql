USE skt;

DROP TABLE IF EXISTS products;

CREATE TABLE products (
  id          INT NOT NULL,
  name        VARCHAR(255),
  description VARCHAR(255),
  price       DECIMAL(8, 2),
  PRIMARY KEY (id)
);

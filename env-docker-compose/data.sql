
CREATE TABLE products (id INT(4), code VARCHAR(20), name VARCHAR(150), description VARCHAR(250), price INT(5), death DATE);

insert into products(id, code, name, description, price) VALUES
(1, 'P001', 'Product 1', 'Product 1 description', 25),
(2, 'P002', 'Product 2', 'Product 2 description', 32),
(3, 'P003', 'Product 3', 'Product 3 description', 50)
;



create table inventory (id int(4), product_code varchar(100), quantity int(4));

insert into inventory(id, product_code, quantity) VALUES
(1, 'P001', 250),
(2, 'P002', 132),
(3, 'P003', 0)
;
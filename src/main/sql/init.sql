
CREATE TABLE users (
                       username VARCHAR(50) NOT NULL PRIMARY KEY,
                       password VARCHAR(500) NOT NULL,
                       enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
                             username VARCHAR(50) NOT NULL,
                             authority VARCHAR(50) NOT NULL,
                             CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE shoes (
                       shoe_id SERIAL PRIMARY KEY,
                       brand VARCHAR(255) NOT NULL,
                       model VARCHAR(255) NOT NULL,
                       size FLOAT NOT NULL,
                       color VARCHAR(50) NOT NULL,
                       price DECIMAL(10, 2) NOT NULL,
                       quantity INT NOT NULL,
                       image_url VARCHAR(255)
);

CREATE TABLE orders (
                        order_id SERIAL PRIMARY KEY,
                        order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        username VARCHAR(50) REFERENCES users(username),
                        total_amount DECIMAL(10, 2) NOT NULL
);

CREATE TABLE order_details (
                               order_detail_id SERIAL PRIMARY KEY,
                               order_id INT REFERENCES orders(order_id),
                               shoe_id INT REFERENCES shoes(shoe_id),
                               quantity INT NOT NULL
);


CREATE TABLE cart (
                      cart_id SERIAL PRIMARY KEY,
                      username VARCHAR(50) REFERENCES users(username),
                      shoe_id INT REFERENCES shoes(shoe_id),
                      quantity INT NOT NULL,
                      CONSTRAINT fk_cart_username FOREIGN KEY (username) REFERENCES users(username),
                      CONSTRAINT fk_cart_shoe_id FOREIGN KEY (shoe_id) REFERENCES shoes(shoe_id),
                      UNIQUE (username, shoe_id)
);
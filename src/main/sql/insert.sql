-- Insert Users
INSERT INTO users (username, password, enabled) VALUES
                                                    ('admin', 'admin', true),
                                                    ('client', 'client', true);

-- Insert Authorities
INSERT INTO authorities (username, authority) VALUES
                                                  ('admin', 'ROLE_ADMIN'),
                                                  ('client', 'ROLE_USER');

-- Insert Shoes
INSERT INTO shoes (brand, model, size, color, price, quantity, image_url) VALUES
                                                                              ('Nike', 'Air Max', 10.5, 'Blue', 99.99, 50, 'https://warsawsneakerstore.com/storage/media/f1000/2023/nike/220966/nike-air-max-1-deep-royal-blue-fd9082-100-64ddf7c62b1ef.jpg'),
                                                                              ('Adidas', 'Ultra Boost', 9.5, 'Black', 129.99, 30, 'https://image-cdn.hypb.st/https%3A%2F%2Fhypebeast.com%2Fimage%2F2021%2F01%2Fadidas-ultraboost-2021-triple-black-release-info-fy0306-1.jpg?cbr=1&q=90'),
                                                                              ('Nike', 'Air Force 1', 9.0, 'White', 89.99, 20, 'https://warsawsneakerstore.com/storage/media/f1000/2022/nike/202756/nike-air-force-1-07-fresh-dm0211-100-62ecccafde1d4.jpg'),
                                                                              ('Adidas', 'Stan Smith', 8.5, 'Green', 79.99, 15, 'https://warsawsneakerstore.com/storage/media/f1000/2023/adidas/223580/adidas-stan-smith-cs-collegiate-green-id2045-64f96f742c685.jpg'),
                                                                              ('Puma', 'Clyde', 10.0, 'Red', 69.99, 25, 'https://images.stockx.com/images/Puma-Clyde-OG-END-Red.jpg?fit=fill&bg=FFFFFF&w=480&h=320&fm=jpg&auto=compress&dpr=2&trim=color&updated_at=1675105258&q=60'),
                                                                              ('Reebok', 'Classic Leather', 8.0, 'Black', 74.99, 18, 'https://warsawsneakerstore.com/storage/media/f1000/2023/reebok/225337/reebok-classic-leather-black-100008497-65968a62329ab.webp'),
                                                                              ('Vans', 'Old Skool', 7.5, 'Blue', 59.99, 30, 'https://cdn.officeshoes.ws/product_images/2023ss/big/vd3h-y28.jpg'),
                                                                              ('Converse', 'Chuck Taylor', 9.5, 'White', 49.99, 22, 'https://www.shooos.ro/media/catalog/product/cache/8/image/1350x778/9df78eab33525d08d6e5fb8d27136e95/c/o/converse-chuck-taylor-hi-leather-w-c132169-2.jpg'),
                                                                              ('New Balance', '990', 11.0, 'Gray', 129.99, 10, 'https://izicop.com/cdn/shop/products/snakerstoreNew_Balance_990_v3_Grey_20192021_-M990GL3-0.png?v=1680250181'),
                                                                              ('Adidas', 'NMD', 10.5, 'Black/Red', 149.99, 12, 'https://i.ebayimg.com/images/g/DDIAAOSwOFFk0MQo/s-l2400.jpg'),
                                                                              ('Nike', 'React Element 55', 8.0, 'Blue/Orange', 109.99, 15, 'https://cdn.idealo.com/folder/Product/200998/5/200998508/s3_produktbild_max_1/nike-react-element-55-women-grey-hyper-crimson-blue-fury.jpg'),
                                                                              ('Puma', 'Thunder Spectra', 11.5, 'Multi-color', 119.99, 8, 'https://sneakit-marketplace-media.s3.eu-central-1.amazonaws.com/production/eaa1900f4ac592412d22ea6b4ed80c8e/web_3115_1.webp');

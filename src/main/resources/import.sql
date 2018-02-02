-- Populate roles
INSERT INTO role (id, role_name, description) VALUES (1, 'STANDARD_USER', 'Standard User - Has no admin rights');
INSERT INTO role (id, role_name, description) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');

-- USER (non-encrypted password: password)
INSERT INTO user (id, first_name, last_name, email, alias, password, activated, gender) VALUES (1, 'Standard', 'User', 'standard@user.com', 'Alias1', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 1, 'MALE');
INSERT INTO user (id, first_name, last_name, email, alias, password, activated, gender) VALUES (2, 'Admin', 'User', 'admin@user.com', 'Alias2', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 1, 'FEMALE');

-- Populate the user_role join table
INSERT INTO user_role(user_id, role_id) VALUES(1,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,2);

-- Populate random hotel table
INSERT INTO hotel(id, name, webpage, country, state, city, address, zip_code) VALUES (1, 'Viru', 'www.viru.com', 'Estonia', 'Virumaa', 'Rakvere', 'Rakvere tee 1', '45875');
INSERT INTO hotel(id, name, webpage, country, state, city, address, zip_code) VALUES (2, 'Sokos', 'www.sokos.com', 'Estonia', 'Harjumaa', 'Tallinn', 'Järvevana tee 5', '87987');
INSERT INTO hotel(id, name, webpage, country, state, city, address, zip_code) VALUES (3, 'Hilton', 'www.hilton.com', 'Estonia', 'Harjumaa', 'Tallinn', 'Puiestee 97', '87745');
INSERT INTO hotel(id, name, webpage, country, state, city, address, zip_code) VALUES (4, 'Tartu Hotell', 'www.tartu.com', 'Estonia', 'Tartumaa', 'Tartu', 'Soola 2', '54287');
INSERT INTO hotel(id, name, webpage, country, state, city, address, zip_code) VALUES (5, 'StarEst Motel', 'www.starest.com', 'Estonia', 'Tartumaa', 'Tartu', 'Mõisavahe 102', '59348');
INSERT INTO hotel(id, name, webpage, country, state, city, address, zip_code) VALUES (6, 'KHK Hotell', 'www.khk.com', 'Estonia', 'Tartumaa', 'Tartu', 'Kopli 9', '57787');

-- Populate random restaurants
INSERT INTO restaurant(id, name, webpage, country, state, city, address, zip_code, hotel_id) VALUES (1, 'FancyResto', 'www.fancyresto.com', 'Estonia', 'Harjumaa', 'Tallinn', 'Järvevana tee 5', '87987', 1);
INSERT INTO restaurant(id, name, webpage, country, state, city, address, zip_code, hotel_id) VALUES (2, 'RandomResto', 'www.randomresto.com', 'Estonia', 'Harjumaa', 'Tallinn', 'Järvevana tee 8', '87987', NULL);
INSERT INTO restaurant(id, name, webpage, country, state, city, address, zip_code, hotel_id) VALUES (3, 'ModestResto', 'www.modestresto.com', 'Estonia', 'Tartumaa', 'Tartu', 'Mõisavahe 102', '59348', 5);
INSERT INTO restaurant(id, name, webpage, country, state, city, address, zip_code, hotel_id) VALUES (4, 'KHK-Resto', 'www.khkresto.com', 'Estonia', 'Tartumaa', 'Tartu', 'Kopli 9', '57787', 6);
INSERT INTO restaurant(id, name, webpage, country, state, city, address, zip_code, hotel_id) VALUES (5, 'KHK-Söökla', 'www.khksookla.com', 'Estonia', 'Tartumaa', 'Tartu', 'Kopli 9', '57787', 6);

-- Populate degrees
INSERT INTO degree (id, name, description) VALUES (1, 'Bachelor Degree', 'Some description');
INSERT INTO degree (id, name, description) VALUES (2, 'Masters Degree', 'Some description');

-- Populate the user_degree join table
INSERT INTO user_degree (degree_id, user_id) VALUES (1, 1);
INSERT INTO user_degree (degree_id, user_id) VALUES (2, 2);

-- Populate occupations
INSERT INTO occupation (id, name, description) VALUES (1, 'Programmer', 'Some description');
INSERT INTO occupation (id, name, description) VALUES (2, 'Teacher', 'Some description');

-- Populate the user_occupation join table
INSERT INTO user_occupation (user_id, occupation_id) VALUES (1, 2);
INSERT INTO user_occupation (user_id, occupation_id) VALUES (2, 1);

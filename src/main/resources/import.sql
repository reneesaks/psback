-- ##
-- ## DO NOT AUTO-FORMAT THIS CODE!!! EVERY SQL STATEMENT MUST BE ON ITS OWN LINE!!! DO NOT PUT LINE BREAKS BETWEEN SQL STATEMENTS!!!
-- ## LINE BREAKS WILL BREAK SQL IMPORT!!!
-- ##
-- Populate roles
INSERT INTO role (id, role_name, description) VALUES (1, 'STANDARD_USER', 'Standard User - Has no admin rights');
INSERT INTO role (id, role_name, description) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');
INSERT INTO role (id, role_name, description) VALUES (3, 'DEVELOPER', 'Developer user - Has access to documentation, server etc.');

-- USER (non-encrypted password: password)
INSERT INTO user (id, email, alias, total_adverts, total_responses, password, activated, gender) VALUES (1, 'developer@user.com', 'DevUser', 0, 0, '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 1, 'MALE');
INSERT INTO user (id, email, alias, total_adverts, total_responses, password, activated, gender) VALUES (2, 'standard@user.com', 'StandUser', 0, 0, '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 1, 'MALE');
INSERT INTO user (id, email, alias, total_adverts, total_responses, password, activated, gender) VALUES (3, 'admin@user.com', 'AdminUser', 0, 0, '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 1, 'FEMALE');
INSERT INTO user (id, email, alias, total_adverts, total_responses, password, activated, gender) VALUES (4, 'random@user.com', 'RandUser', 0, 0, '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 1, 'MALE');

-- Populate the user_role join table
INSERT INTO user_role (user_id, role_id) VALUES (1, 3);
INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO user_role (user_id, role_id) VALUES (3, 2);

-- Populate random hotel table
INSERT INTO hotel (id, name, webpage, country, state, city, address, zip_code) VALUES (1, 'Viru', 'www.viru.com', 'Estonia', 'Virumaa', 'Rakvere', 'Rakvere tee 1', '45875');
INSERT INTO hotel (id, name, webpage, country, state, city, address, zip_code) VALUES (2, 'Sokos', 'www.sokos.com', 'Estonia', 'Harjumaa', 'Tallinn', 'Järvevana tee 5', '87987');
INSERT INTO hotel (id, name, webpage, country, state, city, address, zip_code) VALUES (3, 'Hilton', 'www.hilton.com', 'Estonia', 'Harjumaa', 'Tallinn', 'Puiestee 97', '87745');
INSERT INTO hotel (id, name, webpage, country, state, city, address, zip_code) VALUES (4, 'Tartu Hotell', 'www.tartu.com', 'Estonia', 'Tartumaa', 'Tartu', 'Soola 2', '54287');
INSERT INTO hotel (id, name, webpage, country, state, city, address, zip_code) VALUES (5, 'StarEst Motel', 'www.starest.com', 'Estonia', 'Tartumaa', 'Tartu', 'Mõisavahe 102', '59348');
INSERT INTO hotel (id, name, webpage, country, state, city, address, zip_code) VALUES (6, 'KHK Hotell', 'www.khk.com', 'Estonia', 'Tartumaa', 'Tartu', 'Kopli 9', '57787');
INSERT INTO hotel (id, name, webpage, country, state, city, address, zip_code) VALUES (7, 'Umaga', 'www.umaga.com', 'Estonia', 'Tartumaa', 'Elva', 'Metsavahe tee 3', '45789');
INSERT INTO hotel (id, name, webpage, country, state, city, address, zip_code) VALUES (8, 'Omega', 'www.omega.com', 'Estonia', 'Võrumaa', 'Võru', 'Võro 9', '27787');
INSERT INTO hotel (id, name, webpage, country, state, city, address, zip_code) VALUES (9, 'Ruupus', 'www.ruupus.com', 'Estonia', 'Harjumaa', 'Keila', 'Keila tee 22', '12379');
INSERT INTO hotel (id, name, webpage, country, state, city, address, zip_code) VALUES (10, 'Vene', 'www.vene.com', 'Estonia', 'Ida-Virumaa', 'Narva', 'Vene tee 15', '85345');

-- Populate random restos
INSERT INTO resto (id, name, webpage, country, state, city, address, zip_code) VALUES (1, 'FancyResto', 'www.fancyresto.com', 'Estonia', 'Harjumaa', 'Tallinn', 'Järvevana tee 5', '87987');
INSERT INTO resto (id, name, webpage, country, state, city, address, zip_code) VALUES (2, 'RandomResto', 'www.randomresto.com', 'Estonia', 'Harjumaa', 'Tallinn', 'Järvevana tee 8', '87987');
INSERT INTO resto (id, name, webpage, country, state, city, address, zip_code) VALUES (3, 'ModestResto', 'www.modestresto.com', 'Estonia', 'Tartumaa', 'Tartu', 'Mõisavahe 102', '59348');
INSERT INTO resto (id, name, webpage, country, state, city, address, zip_code) VALUES (4, 'KHK-Resto', 'www.khkresto.com', 'Estonia', 'Tartumaa', 'Tartu', 'Kopli 9', '57787');
INSERT INTO resto (id, name, webpage, country, state, city, address, zip_code) VALUES (5, 'KHK-Söökla', 'www.khksookla.com', 'Estonia', 'Tartumaa', 'Tartu', 'Kopli 9', '57787');

-- Populate resto_hotel join table
INSERT INTO resto_hotel (resto_id, hotel_id) VALUES (1, 1);
INSERT INTO resto_hotel (resto_id, hotel_id) VALUES (2, 2);

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

-- Populate advert table
INSERT INTO advert (id, advert_status, created_date, advert_text, meal_type, preferred_start, preferred_end, accepted_time) VALUES (1, 'NOT_ACCEPTED', '2017-08-14T12:17:47.720Z', 'Programmer looking for a dinner companion.', 'DINNER', '2017-08-14T12:17:47.720Z', '2017-08-14T12:17:47.720Z', '2017-08-14T12:17:47.720Z');
INSERT INTO advert (id, advert_status, created_date, advert_text, meal_type, preferred_start, preferred_end, accepted_time) VALUES (2, 'ACCEPTED', '2017-08-14T12:17:47.720Z', 'Is there somebody who wants to have a meaningful conversation over breakfast?.', 'BREAKFAST', '2017-08-14T12:17:47.720Z', '2017-08-14T12:17:47.720Z', '2017-08-14T12:17:47.720Z');

-- Populate response table
INSERT INTO response (id, response_text, proposed_time, response_status) VALUES (1, 'That is a great idea! I would love to have a meaningful conversation over breakfast.', '2017-08-14T12:17:47.720Z', 'NOT_ANSWERED');
INSERT INTO response (id, response_text, proposed_time, response_status) VALUES (2, 'Why not. I am up for it!', '2017-08-14T12:17:47.720Z', 'NOT_ANSWERED');
INSERT INTO response (id, response_text, proposed_time, response_status) VALUES (3, 'YES! This is absolutely what I need!', '2017-08-14T12:17:47.720Z', 'NOT_ANSWERED');

-- Populate response_advert table
INSERT INTO response_advert (response_id, advert_id) VALUES (1, 2);
INSERT INTO response_advert (response_id, advert_id) VALUES (2, 2);
INSERT INTO response_advert (response_id, advert_id) VALUES (3, 1);

-- Populate advert_user table
INSERT INTO advert_user (advert_id, user_id) VALUES (1, 2);
INSERT INTO advert_user (advert_id, user_id) VALUES (2, 3);

-- Populate response_user table
INSERT INTO response_user (response_id, user_id) VALUES (1, 2);
INSERT INTO response_user (response_id, user_id) VALUES (2, 3);
INSERT INTO response_user (response_id, user_id) VALUES (3, 2);

-- Populate advert_resto table
INSERT INTO advert_resto (advert_id, resto_id) VALUES (1, 2);
INSERT INTO advert_resto (advert_id, resto_id) VALUES (2, 2);

-- Populate advert_hotel table
INSERT INTO advert_hotel (advert_id, hotel_id) VALUES (1, 2);
INSERT INTO advert_hotel (advert_id, hotel_id) VALUES (2, 2);


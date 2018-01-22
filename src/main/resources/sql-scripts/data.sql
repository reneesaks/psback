INSERT INTO role (id, role_name, description) VALUES (1, 'STANDARD_USER', 'Standard User - Has no admin rights');
INSERT INTO role (id, role_name, description) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');

-- USER
-- non-encrypted password: jwtpass
INSERT INTO user (id, first_name, last_name, password, username) VALUES (1, 'Standard', 'User', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user');
INSERT INTO user (id, first_name, last_name, password, username) VALUES (2, 'Super', 'User', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'admin');


INSERT INTO user_role(user_id, role_id) VALUES(1,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,2);

-- Populate random hotel table
INSERT INTO hotel(id, name) VALUES (1, 'Viru');
INSERT INTO hotel(id, name) VALUES (2, 'Sokos');
INSERT INTO hotel(id, name) VALUES (3, 'Hilton');
INSERT INTO hotel(id, name) VALUES (4, 'Tartu Hotell');
INSERT INTO hotel(id, name) VALUES (5, 'StarEst Motel');
INSERT INTO hotel(id, name) VALUES (6, 'KHK Hotell');


INSERT INTO task_status(name)
VALUES('Do zrobienia');

INSERT INTO task_status(name)
VALUES('W trakcie');

INSERT INTO task_status(name)
VALUES('Ukończone');

INSERT INTO user_role(name)
VALUES('Administrator');

INSERT INTO user_role(name)
VALUES('Użytkownik');

INSERT INTO user_address(street, apartmentNumber, city, zipCode, state)
VALUES ('ul. Kubusia Puchatka', '33', '00-037', 'Warszawa', 'Polska');

INSERT INTO user_address(street, apartmentNumber, city, zipCode, state)
VALUES ('ul. Szkolna', '4A/101', '00-006', 'Warszawa', 'Polska');

INSERT INTO user_address(street, apartmentNumber, city, zipCode, state)
VALUES ('ul. Marszałkowska', '1A/23', '00-026', 'Warszawa', 'Polska');

INSERT INTO user_address(street, apartmentNumber, city, zipCode, state)
VALUES ('ul. Lubelska', '3D/16', '30-003', 'Kraków', 'Polska');

INSERT INTO user_address(street, apartmentNumber, city, zipCode, state)
VALUES ('ul. Żywiecka', '16', '20-870', 'Lublin', 'Polska');

INSERT INTO user_address(street, apartmentNumber, city, zipCode, state)
VALUES ('ul. Żeromskiego Stefana', '121A', '20-460', 'Lublin', 'Polska');

-- password = admin

INSERT INTO user(username, password, firstname, lastname, age, phoneNumber, emailAddress, isArchived, address_id, role_id)
VALUES ('admin', '$2a$10$LCuvh98/oDJ80WLSjpzx0uCiMj.JOoniYZDb8FPe5BWkmVwDU2uHW', 'Tomasz', 'Nowak', '36', '093100322', 'admin@domena.pl', FALSE, 1, 1);

-- password = password

INSERT INTO user(username, password, firstname, lastname, age, phoneNumber, emailAddress, isArchived, address_id, role_id)
VALUES ('kowalski002', '$2a$10$jGgN4HjXLlCFDYqqwfirKeq7as5sCuesUQz4dMGX.08FpNkG5hqH2', 'Jan', 'Kowalski', '26', '360248990', 'jan_kowalski@domena.pl', FALSE, 2, 2);

INSERT INTO user(username, password, firstname, lastname, age, phoneNumber, emailAddress, isArchived, address_id, role_id)
VALUES ('malgosia102', '$2a$10$GVVmLgQELX9biIFEBdT41uNzujbc3VdyUdXxkRoEyAdoPRoX6jBae', 'Małgorzata', 'Janowska', '31', '034120009', 'malgorzata031@domena.pl', FALSE, 3, 2);

INSERT INTO user(username, password, firstname, lastname, age, phoneNumber, emailAddress, isArchived, address_id, role_id)
VALUES ('marek01', '$2a$10$wotaE2kBKdd/gBHEM9sp5OoR82x8RFoJHyA4/B70mFr9P.G7ZoVxa', 'Marek', 'Dym', '24', '167890330', 'marek_d@domena.pl', FALSE, 4, 2);

INSERT INTO user(username, password, firstname, lastname, age, phoneNumber, emailAddress, isArchived, address_id, role_id)
VALUES ('ania113', '$2a$10$MIJq1imiWnINg7D5RKi6guRFrOh9/uNy1OKHHGbyjVkxaS6j6Gfxa', 'Ania', 'Wróblewska', '23', '880766231', 'ania23@domena.pl', FALSE, 5, 2);

INSERT INTO user(username, password, firstname, lastname, age, phoneNumber, emailAddress, isArchived, address_id, role_id)
VALUES ('marcin003', '$2a$10$KXE2TR4GoKJpreHXEoSKB.xciiEV6bq5f3plkNnTnbj1SPFcb4IyW', 'Marcin', 'Grabarz', '33', '889112303', 'marcin_g3@domena.pl', TRUE, 6, 2);

-- Seed data for local profile (MySQL syntax)
-- City
INSERT INTO city (id, name, active) VALUES (1, 'Metro City', TRUE);

-- Theatres
INSERT INTO theatre (id, name, address) VALUES (1, 'Grand Cinema', NULL);
INSERT INTO theatre (id, name, address) VALUES (2, 'City Lights', NULL);

-- City-Theatre join (assuming a unidirectional OneToMany creates a join table named city_theatre)
INSERT INTO city_theatre (city_id, theatre_id) VALUES (1, 1), (1, 2);

-- Screens
INSERT INTO screen (id, name, theatre_id) VALUES (1, 'Screen 1', 1);
INSERT INTO screen (id, name, theatre_id) VALUES (2, 'Screen 2', 1);
INSERT INTO screen (id, name, theatre_id) VALUES (3, 'Screen A', 2);

-- Movie
INSERT INTO movie (id, name, genre, title, description) VALUES (1, 'Sample Movie', 'Drama', 'Sample Movie', 'A dummy movie for seeding');

-- Shows
INSERT INTO shows (id, movie_id, screen_id, start_time) VALUES (1, 1, 1, '2026-03-19 10:00:00');
INSERT INTO shows (id, movie_id, screen_id, start_time) VALUES (2, 1, 3, '2026-03-19 14:00:00');

-- Users
INSERT INTO users (id, name, email, password) VALUES (1, 'Alice', 'alice@example.com', 'password');
INSERT INTO users (id, name, email, password) VALUES (2, 'Bob', 'bob@example.com', 'password');

-- Tickets
INSERT INTO ticket (id, user_id, show_id, time_of_booking, price, booking_status, payment_status) VALUES
(1, 1, 1, NOW(), 100.0, 2, 2),
(2, 2, 2, NOW(), 120.0, 2, 2);

--User Table
CREATE TABLE event_user (
    id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    name VARCHAR(255) NOT NULL,
    dob DATE,
    gender VARCHAR(10),
    email_address VARCHAR(255) UNIQUE,
    mobile_number BIGINT UNIQUE
);
INSERT INTO event_user (id, username, name, dob, gender, email_address, mobile_number)
VALUES
('user1', 'john_doe', 'John Doe', '1985-07-15', 'Male', 'john.doe@example.com', 9876543210),
('user2', 'jane_doe', 'Jane Doe', '1990-03-22', 'Female', 'jane.doe@example.com', 9876543211),
('user3', 'alex_smith', 'Alex Smith', '1995-09-30', 'Male', 'alex.smith@example.com', 9876543212);


-- Venue Table
CREATE TABLE venue (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    zip_code VARCHAR(20),
    country VARCHAR(255),
    phone VARCHAR(20),
    web VARCHAR(255),
    email VARCHAR(255)
);
INSERT INTO venue (id, name, address, city, state, zip_code, country, phone, web, email)
VALUES
('venue1', 'Grand Hall', '123 Main St', 'Los Angeles', 'California', '90001', 'USA', '123-456-7890', 'http://grandhall.com', 'contact@grandhall.com'),
('venue2', 'Sunset Arena', '456 Sunset Blvd', 'San Francisco', 'California', '94105', 'USA', '123-456-7891', 'http://sunsetarena.com', 'info@sunsetarena.com'),
('venue3', 'City Theater', '789 City Rd', 'New York', 'New York', '10001', 'USA', '123-456-7892', 'http://citytheater.com', 'support@citytheater.com');


-- Event Table
CREATE TABLE event (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    venue_id VARCHAR(255),
    artists VARCHAR(1000),
    event_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_venue FOREIGN KEY (venue_id) REFERENCES venue(id)
);
INSERT INTO event (id, name, description, venue_id, artists, event_date)
VALUES
('event1', 'Rock Concert', 'A thrilling rock concert featuring top bands.', 'venue1', 'Band A, Band B, Band C', '2024-01-15 19:00:00'),
('event2', 'Jazz Night', 'A relaxing night of smooth jazz music.', 'venue2', 'Artist A, Artist B', '2024-02-20 18:30:00'),
('event3', 'Theater Play', 'A captivating performance of classic theater.', 'venue3', 'Theater Group A, Theater Group B', '2024-03-10 20:00:00');


-- Booking Table
CREATE TABLE booking (
    id VARCHAR(255) PRIMARY KEY,
    event_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    booking_status VARCHAR(50) NOT NULL,
    created TIMESTAMP NOT NULL,
    updated TIMESTAMP NOT NULL,
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES event(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES event_user(id)
);
INSERT INTO booking (id, event_id, user_id, booking_status, created, updated)
VALUES
('booking1', 'event1', 'user1', 'Confirmed', '2023-12-20 10:00:00', '2023-12-20 10:00:00'),
('booking2', 'event2', 'user2', 'Pending', '2023-12-22 11:00:00', '2023-12-22 11:00:00'),
('booking3', 'event3', 'user3', 'Confirmed', '2023-12-23 14:00:00', '2023-12-23 14:00:00');


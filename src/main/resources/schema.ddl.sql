DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS event_categories;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS users;

CREATE TABLE users ( 
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL -- CHECK (type IN ('CLIENT', 'EVENT_PLANNER'))
);

CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date TIMESTAMP NOT NULL,
    location VARCHAR(255) NOT NULL,
    organizer_id INTEGER NOT NULL,
    type VARCHAR(255) NOT NULL,
    FOREIGN KEY (organizer_id) REFERENCES users(id)
);

CREATE TABLE event_categories (
    id SERIAL PRIMARY KEY,
    event_id INTEGER NOT NULL,
    name VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    capacity INTEGER NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events(id)
);

CREATE TABLE reservations (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    event_category_id INTEGER NOT NULL,
    reservation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL, -- CHECK (status IN ('PENDING', 'CONFIRMED','CANCELLED')),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (event_category_id) REFERENCES event_categories(id)
);


-- CREATE TABLE reservation_items (
--    id SERIAL PRIMARY KEY,
--    reservation_id INTEGER NOT NULL,
--    category_id INTEGER NOT NULL,
--    quantity INTEGER NOT NULL,
--    total_price DECIMAL(10,2) NOT NULL,
--    FOREIGN KEY (reservation_id) REFERENCES reservations(id),
--    FOREIGN KEY (category_id) REFERENCES event_categories(id)
--);

-- CREATE TABLE payments (
--     id SERIAL PRIMARY KEY,
--     reservation_id INTEGER NOT NULL,
--     amount DECIMAL(10,2) NOT NULL,
--     status VARCHAR(20) NOT NULL, -- CHECK (status IN ('SUCCESS', 'FAILED')),
--     payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     FOREIGN KEY (reservation_id) REFERENCES reservations(id)
-- );
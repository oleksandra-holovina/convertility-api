CREATE DATABASE convertility ENCODING 'UTF8';

\c convertility

CREATE TABLE IF NOT EXISTS job_listing
(
    id BIGSERIAL PRIMARY KEY,
    title              VARCHAR(100) NOT NULL,
    description        TEXT         NOT NULL,
    price_for_day        DECIMAL      NOT NULL,
    decrease_percentage DECIMAL      NOT NULL
);

CREATE TABLE IF NOT EXISTS technology
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS acceptance_criteria
(
    id BIGSERIAL PRIMARY KEY,
    description TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS job_listing_technology
(
    job_listing_id BIGINT REFERENCES job_listing,
    technology_id  INT REFERENCES technology,
    PRIMARY KEY (job_listing_id, technology_id)
);

CREATE TABLE IF NOT EXISTS job_listing_acceptance_criteria
(
    job_listing_id         BIGINT REFERENCES job_listing,
    acceptance_criteria_id INT REFERENCES acceptance_criteria,
    PRIMARY KEY (job_listing_id, acceptance_criteria_id)
);

CREATE TABLE IF NOT EXISTS site_user
(
    id VARCHAR(30) PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    email VARCHAR(50),
    phone_number VARCHAR(15),
    picture_url VARCHAR(200),
    access_token VARCHAR(1000),
    token_expiration BIGINT
);

CREATE INDEX access_token_index ON site_user (access_token);

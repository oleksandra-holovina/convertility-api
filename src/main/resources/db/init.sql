CREATE DATABASE convertility ENCODING 'UTF8';

\c convertility

CREATE TABLE IF NOT EXISTS job_listing
(
    id BIGSERIAL PRIMARY KEY,
    title              VARCHAR(100) NOT NULL,
    description        TEXT         NOT NULL,
    price_for_day        DECIMAL      NOT NULL,
    decrease_percentage DECIMAL      NOT NULL,
    createdBy VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS job_application
(
    job_listing_id BIGINT REFERENCES job_listing,
    user_id VARCHAR(30),
    PRIMARY KEY (job_listing_id, user_id)
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

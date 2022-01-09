CREATE DATABASE convertility ENCODING 'UTF8';

\c convertility

CREATE TABLE IF NOT EXISTS job_listing
(
    id BIGSERIAL PRIMARY KEY,
    title              VARCHAR(100) NOT NULL,
    description        TEXT         NOT NULL,
    priceForDay        DECIMAL      NOT NULL,
    decreasePercentage DECIMAL      NOT NULL
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
    job_listing_id BIGINT REFERENCES job_listing (id),
    technology_id  INT REFERENCES technology (id),
    CONSTRAINT job_listing_technology_key FOREIGN KEY (job_listing_id, technology_id)
);

CREATE TABLE IF NOT EXISTS job_listing_acceptance_criteria
(
    job_listing_id         BIGINT REFERENCES job_listing (id),
    acceptance_criteria_id INT REFERENCES acceptance_criteria (id),
    CONSTRAINT job_listing_acceptance_criteria_key FOREIGN KEY (job_listing_id, acceptance_criteria_id)
);


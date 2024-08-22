CREATE TABLE provider (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          photo_url VARCHAR(255)
);

CREATE TABLE contact_info (
                              id BIGSERIAL PRIMARY KEY,
                              address VARCHAR(255),
                              phone VARCHAR(20),
                              working_hours VARCHAR(255)
);

CREATE TABLE service (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price DECIMAL(10, 2) NOT NULL,
                         duration VARCHAR(255) NOT NULL,
                         provider_id BIGINT NOT NULL REFERENCES provider(id)
);

ALTER TABLE provider ADD COLUMN contact_info_id BIGINT UNIQUE REFERENCES contact_info(id);
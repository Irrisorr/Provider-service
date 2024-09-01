-- Create Provider table
CREATE TABLE provider (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT
);

-- Create ContactInfo table
CREATE TABLE contact_info (
                              id BIGSERIAL PRIMARY KEY,
                              city VARCHAR(255),
                              street VARCHAR(255),
                              house VARCHAR(50),
                              phone VARCHAR(20),
                              working_hours_start TIME,
                              working_hours_end TIME,
                              provider_id BIGINT UNIQUE,
                              FOREIGN KEY (provider_id) REFERENCES provider(id)
);

-- Create Service table
CREATE TABLE service (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price DECIMAL(10, 2) NOT NULL,
                         duration INTEGER,
                         provider_id BIGINT NOT NULL,
                         FOREIGN KEY (provider_id) REFERENCES provider(id)
);

-- Create Image table
CREATE TABLE image (
                       id BIGSERIAL PRIMARY KEY,
                       data BYTEA,
                       provider_id BIGINT UNIQUE,
                       FOREIGN KEY (provider_id) REFERENCES provider(id)
);
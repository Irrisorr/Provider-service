CREATE TABLE images (
                        id BIGSERIAL PRIMARY KEY,
                        data BYTEA
);

CREATE TABLE providers (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           description TEXT,
                           image_id BIGINT,
                           FOREIGN KEY (image_id) REFERENCES images(id)
);

CREATE TABLE contact_info (
                              id BIGSERIAL PRIMARY KEY,
                              city VARCHAR(255),
                              street VARCHAR(255),
                              house VARCHAR(50),
                              phone VARCHAR(20),
                              working_hours_start TIME,
                              working_hours_end TIME,
                              provider_id BIGINT,
                              FOREIGN KEY (provider_id) REFERENCES providers(id)
);

CREATE TABLE services (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price DECIMAL(10, 2),
                          duration INTERVAL,
                          provider_id BIGINT NOT NULL,
                          FOREIGN KEY (provider_id) REFERENCES providers(id)
);
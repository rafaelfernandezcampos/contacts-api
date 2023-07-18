CREATE TABLE IF NOT EXISTS contacts
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    phone      VARCHAR(255) NOT NULL,
    birth      date         NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS addresses
(
    id         SERIAL PRIMARY KEY,
    street     VARCHAR(255) NOT NULL,
    number     integer      NOT NULL,
    cep        VARCHAR(255) NOT NULL,
    contact_id integer      NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_contact
        FOREIGN KEY (contact_id) REFERENCES contacts (id)
)
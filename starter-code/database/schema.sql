CREATE TABLE IF NOT EXISTS products (
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2) NOT NULL CHECK (price > 0),
    stock       INT            NOT NULL DEFAULT 0 CHECK (stock >= 0),
    created_at  TIMESTAMP      DEFAULT NOW()
);

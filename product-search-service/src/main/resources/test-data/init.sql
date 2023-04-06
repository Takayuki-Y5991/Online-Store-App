-- user-authenticate
CREATE TABLE accounts (
    uid VARCHAR(255) PRIMARY KEY,
    account_name VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

-- product-search-service
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE products (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price NUMERIC NOT NULL,
    description TEXT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    category_id INTEGER NOT NULL,
    version INTEGER NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE reviews (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    account_uid VARCHAR(255) NOT NULL REFERENCES accounts(uid) ON DELETE CASCADE,
    rating INTEGER NOT NULL,
    title VARCHAR(20) NOT NULL,
    comment TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CHECK(rating >= 1 AND rating <= 5)
);

--INSERT--
-------------------------------------------------------------------------------

-- accounts
INSERT INTO accounts (uid, account_name, email, first_name, last_name, created_at, updated_at)
VALUES ('3d3e6f11-32e7-4dd2-8263-a3a8fde49f7b', 'user1', 'user1@example.com', 'John', 'Doe', '2023-01-01 12:00:00', '2023-01-01 12:00:00');

-- categories
INSERT INTO categories (name) VALUES ('本');
INSERT INTO categories (name) VALUES ('家電製品');
INSERT INTO categories (name) VALUES ('食品');

-- products
INSERT INTO products (id, name, price, description, image_url, category_id, version)
VALUES ('d9e4c6de-4e3f-4a57-8aaf-06e54c6c45e1', '素晴らしい本', 1200, 'この素晴らしい本はあなたの人生を変えます。', 'https://example.com/book.jpg', 1, 1);

INSERT INTO products (id, name, price, description, image_url, category_id, version)
VALUES ('0cd36a3d-6d00-4a24-8f54-3a3c2d13b8c9', '便利な家電製品', 10000, 'この便利な家電製品はあなたの日常を助けます。', 'https://example.com/appliance.jpg', 2, 1);

INSERT INTO products (id, name, price, description, image_url, category_id, version)
VALUES ('6d302d6b-8c63-42f9-a9f0-56aebc32b8f6', '美味しい食品', 500, 'この美味しい食品はあなたの食事を豊かにします。', 'https://example.com/food.jpg', 3, 1);

-- reviews
INSERT INTO reviews (id, product_id, account_uid, rating, title, comment, created_at, updated_at)
VALUES ('d6194e4b-74a0-4b7e-8cfd-3b0f8b47abaf', 'd9e4c6de-4e3f-4a57-8aaf-06e54c6c45e1', '3d3e6f11-32e7-4dd2-8263-a3a8fde49f7b', 5, '最高の本', '私はこの本が大好きです。人生が変わりました。', '2023-01-15 15:30:00', '2023-01-15 15:30:00');

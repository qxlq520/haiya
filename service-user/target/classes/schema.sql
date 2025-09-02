CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    avatar_url VARCHAR(255),
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE IF NOT EXISTS user_experiences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    company VARCHAR(100),
    start_date BIGINT,
    end_date BIGINT,
    description TEXT,
    created_at BIGINT,
    updated_at BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_phone_number ON users(phone_number);
CREATE INDEX idx_user_experiences_user_id ON user_experiences(user_id);
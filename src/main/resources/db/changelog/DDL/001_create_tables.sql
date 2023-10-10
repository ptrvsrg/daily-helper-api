# liquibase formatted sql
# changeset ptrvsrg:1

CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(256) NOT NULL CHECK (LENGTH(first_name) > 0),
    last_name  VARCHAR(256) NOT NULL CHECK (LENGTH(last_name) > 0),
    email      VARCHAR(256) NOT NULL UNIQUE
);

CREATE TABLE user_details
(
    user_id   BIGINT PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
    password  TEXT NOT NULL,
    is_active BIT  NOT NULL
);

CREATE TABLE refresh_tokens
(
    user_id      BIGINT PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
    token        TEXT     NOT NULL,
    expired_time DATETIME NOT NULL
);

CREATE TABLE verification_tokens
(
    user_id      BIGINT PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
    token        TEXT     NOT NULL,
    expired_time DATETIME NOT NULL,
    delete_user  BIT      NOT NULL
);

CREATE TABLE projects
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(256) NOT NULL UNIQUE CHECK (LENGTH(name) > 0),
    description TEXT,
    user_id     BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE tasks
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(256) NOT NULL CHECK (LENGTH(name) > 0),
    description TEXT,
    project_id  BIGINT       NOT NULL REFERENCES projects (id) ON DELETE CASCADE
);
--liquibase formatted sql

--changeset didenko:1
CREATE TABLE telegram_chat(
    id BIGSERIAL PRIMARY KEY ,
    chat_id BIGINT UNIQUE ,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE NOT NULL UNIQUE ,
    token VARCHAR(12) UNIQUE NOT NULL
)
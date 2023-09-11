--liquibase formatted sql

--changeset didenko:1
ALTER TABLE users
ADD COLUMN role VARCHAR(16) NOT NULL DEFAULT 'USER';
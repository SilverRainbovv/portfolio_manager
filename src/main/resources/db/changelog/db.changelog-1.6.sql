--liquibase formatted sql

--changeset didenko:1
ALTER TABLE users
ALTER COLUMN password TYPE VARCHAR(128);
--liquibase formatted sql

--changeset didenko:1
ALTER TABLE portfolio
    ADD COLUMN name        VARCHAR(128) NOT NULL DEFAULT 'no name',
    ADD COLUMN description VARCHAR(512);
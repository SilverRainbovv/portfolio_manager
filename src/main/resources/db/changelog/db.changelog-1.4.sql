--liquibase formatted sql

--changeset didenko:1
ALTER TABLE asset
    ADD COLUMN asset_type VARCHAR(128) NOT NULL DEFAULT 'STOCK';
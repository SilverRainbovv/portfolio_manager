--liquibase formatted sql

--changeset didenko:1
ALTER TABLE asset_trade
ALTER COLUMN quantity TYPE numeric(19, 5);

--changeset didenko:2
ALTER TABLE asset_trade
    ALTER COLUMN open_price TYPE numeric(19, 5);

--changeset didenko:3
ALTER TABLE asset_trade
    ALTER COLUMN close_price TYPE numeric(19, 5);
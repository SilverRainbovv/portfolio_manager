--liquibase formatted sql

--changeset didenko:1
ALTER TABLE asset_transaction
    ADD COLUMN state VARCHAR(16);

--changeset didenko:2
UPDATE asset_transaction
SET state='OPEN'
WHERE close_price isnull;

--changeset didenko:3
UPDATE  asset_transaction
SET state='CLOSED'
WHERE close_price is not null;

--changeset didenko:4
ALTER TABLE asset_transaction
ALTER COLUMN state SET NOT NULL;
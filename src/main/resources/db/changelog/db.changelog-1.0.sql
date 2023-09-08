--liquibase formatted sql

--changeset didenko:1
CREATE TABLE users(
                      id BIGSERIAL PRIMARY KEY ,
                      email VARCHAR(64) UNIQUE NOT NULL ,
                      password VARCHAR(64) NOT NULL
);

--changeset didenko:2
CREATE TABLE user_info(
                          id BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE ,
                          firstname VARCHAR(64),
                          lastname VARCHAR(64),
                          birth_date DATE
);

--changeset didenko:3
CREATE TABLE portfolio(
                          id BIGSERIAL PRIMARY KEY ,
                          user_id BIGINT REFERENCES users(id) ON DELETE CASCADE NOT NULL
);

--changeset didenko:4
CREATE TABLE asset(
                      id BIGSERIAL PRIMARY KEY ,
                      portfolio_id BIGINT REFERENCES users(id) ON DELETE CASCADE NOT NULL ,
                      name VARCHAR(64) NOT NULL ,
                      comments VARCHAR(512)
);

--changeset didenko:5
CREATE TABLE asset_trade(
                            asset_id BIGINT PRIMARY KEY REFERENCES asset(id) ON DELETE CASCADE ,
                            position_direction VARCHAR(16) NOT NULL ,
                            quantity DECIMAL(2) NOT NULL ,
                            open_price DECIMAL(5) NOT NULL ,
                            close_price DECIMAL(5) NOT NULL ,
                            open_date TIMESTAMP WITH TIME ZONE NOT NULL ,
                            close_date TIMESTAMP WITH TIME ZONE NOT NULL
)

-- liquibase formatted sql
CREATE TABLE IF NOT EXISTS application_logs
(
    id       BIGINT,
    tweet_id BIGINT,
    content  VARCHAR,
    PRIMARY KEY ((tweet_id), id)
)
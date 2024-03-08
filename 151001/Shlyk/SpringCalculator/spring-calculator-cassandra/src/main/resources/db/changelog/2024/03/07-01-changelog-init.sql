use distcomp;
CREATE TABLE IF NOT EXISTS news_note
(
    note_id BIGINT,
    news_id BIGINT,
    country varchar,
    content text,
    PRIMARY KEY ( (note_id), news_id, country )
    )
WITH CLUSTERING ORDER BY (country ASC, news_id ASC)
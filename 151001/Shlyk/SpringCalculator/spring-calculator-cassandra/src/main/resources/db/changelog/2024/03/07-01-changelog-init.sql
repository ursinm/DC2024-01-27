CREATE TABLE IF NOT EXISTS notes_by_country_and_news
(
    note_id BIGINT,
    news_id BIGINT,
    country varchar,
    content text,
    PRIMARY KEY ( (country), news_id, note_id)
)
WITH CLUSTERING ORDER BY (news_id ASC, note_id ASC);
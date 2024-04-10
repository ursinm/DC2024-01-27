-- cql

-- changeset init:1
USE distcomp;
CREATE TABLE tbl_note
(
    country TEXT,
    tweet_id BIGINT,
    id BIGINT,
    content TEXT,
    PRIMARY KEY ((country), tweet_id, id)
)
WITH CLUSTERING ORDER BY (tweet_id ASC, id ASC);
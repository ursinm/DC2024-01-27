-- cql

-- changeset init:1
USE distcomp;
DROP TABLE tbl_comment;
CREATE TABLE IF NOT EXISTS tbl_comment
(
    country TEXT,
    story_id BIGINT,
    id      BIGINT,
    content TEXT,
    PRIMARY KEY ((country), story_id, id)
)
WITH CLUSTERING ORDER BY (story_id ASC, id ASC);
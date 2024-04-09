CREATE KEYSPACE distcomp WITH replication = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE distcomp;

CREATE TABLE IF NOT EXISTS tbl_messages (id TEXT PRIMARY KEY, "storyId" TEXT, content TEXT);
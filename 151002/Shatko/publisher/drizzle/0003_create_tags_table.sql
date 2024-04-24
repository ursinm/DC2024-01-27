CREATE TABLE IF NOT EXISTS "distcomp"."tbl_tags" (
	"id" serial PRIMARY KEY NOT NULL,
	"name" varchar(32) NOT NULL,
	CONSTRAINT "tbl_tags_name_unique" UNIQUE("name")
);

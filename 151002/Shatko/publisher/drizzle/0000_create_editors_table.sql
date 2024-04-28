CREATE SCHEMA "distcomp";
--> statement-breakpoint
CREATE TABLE IF NOT EXISTS "distcomp"."tbl_editors" (
	"id" serial PRIMARY KEY NOT NULL,
	"login" varchar(64) NOT NULL,
	"password" varchar(128) NOT NULL,
	"firstname" varchar(64) NOT NULL,
	"lastname" varchar(64) NOT NULL,
	CONSTRAINT "tbl_editors_login_unique" UNIQUE("login")
);

CREATE TABLE IF NOT EXISTS "tbl_editor" (
	"id" serial PRIMARY KEY NOT NULL,
	"login" varchar(64) NOT NULL,
	"password" varchar(128) NOT NULL,
	"firstname" varchar(64) NOT NULL,
	"lastname" varchar(64) NOT NULL,
	CONSTRAINT "tbl_editor_login_unique" UNIQUE("login")
);
--> statement-breakpoint
CREATE TABLE IF NOT EXISTS "tbl_story" (
	"id" serial PRIMARY KEY NOT NULL,
	"editor_id" integer NOT NULL,
	"title" varchar(64) NOT NULL,
	"content" varchar(2048) NOT NULL,
	"created" timestamp DEFAULT now() NOT NULL,
	"modified" timestamp DEFAULT now() NOT NULL,
	CONSTRAINT "tbl_story_title_unique" UNIQUE("title")
);
--> statement-breakpoint
CREATE TABLE IF NOT EXISTS "tbl_message" (
	"id" serial PRIMARY KEY NOT NULL,
	"story_id" integer NOT NULL,
	"content" varchar(2048) NOT NULL
);
--> statement-breakpoint
CREATE TABLE IF NOT EXISTS "tbl_tag" (
	"id" serial PRIMARY KEY NOT NULL,
	"name" varchar(32) NOT NULL,
	CONSTRAINT "tbl_tag_name_unique" UNIQUE("name")
);
--> statement-breakpoint
CREATE TABLE IF NOT EXISTS "tbl_stories_to_tags" (
	"id" serial PRIMARY KEY NOT NULL,
	"story_id" integer NOT NULL,
	"tag_id" integer NOT NULL
);
--> statement-breakpoint
DO $$ BEGIN
 ALTER TABLE "tbl_story" ADD CONSTRAINT "tbl_story_editor_id_tbl_editor_id_fk" FOREIGN KEY ("editor_id") REFERENCES "tbl_editor"("id") ON DELETE no action ON UPDATE no action;
EXCEPTION
 WHEN duplicate_object THEN null;
END $$;
--> statement-breakpoint
DO $$ BEGIN
 ALTER TABLE "tbl_message" ADD CONSTRAINT "tbl_message_story_id_tbl_story_id_fk" FOREIGN KEY ("story_id") REFERENCES "tbl_story"("id") ON DELETE no action ON UPDATE no action;
EXCEPTION
 WHEN duplicate_object THEN null;
END $$;
--> statement-breakpoint
DO $$ BEGIN
 ALTER TABLE "tbl_stories_to_tags" ADD CONSTRAINT "tbl_stories_to_tags_story_id_tbl_editor_id_fk" FOREIGN KEY ("story_id") REFERENCES "tbl_editor"("id") ON DELETE no action ON UPDATE no action;
EXCEPTION
 WHEN duplicate_object THEN null;
END $$;
--> statement-breakpoint
DO $$ BEGIN
 ALTER TABLE "tbl_stories_to_tags" ADD CONSTRAINT "tbl_stories_to_tags_tag_id_tbl_tag_id_fk" FOREIGN KEY ("tag_id") REFERENCES "tbl_tag"("id") ON DELETE no action ON UPDATE no action;
EXCEPTION
 WHEN duplicate_object THEN null;
END $$;

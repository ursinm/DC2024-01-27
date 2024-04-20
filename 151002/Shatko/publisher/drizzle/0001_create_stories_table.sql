CREATE TABLE IF NOT EXISTS "distcomp"."tbl_stories" (
	"id" serial PRIMARY KEY NOT NULL,
	"editor_id" integer NOT NULL,
	"title" varchar(64) NOT NULL,
	"content" varchar(2048) NOT NULL,
	"created" timestamp DEFAULT now() NOT NULL,
	"modified" timestamp DEFAULT now() NOT NULL,
	CONSTRAINT "tbl_stories_title_unique" UNIQUE("title")
);
--> statement-breakpoint
DO $$ BEGIN
 ALTER TABLE "distcomp"."tbl_stories" ADD CONSTRAINT "tbl_stories_editor_id_tbl_editors_id_fk" FOREIGN KEY ("editor_id") REFERENCES "distcomp"."tbl_editors"("id") ON DELETE no action ON UPDATE no action;
EXCEPTION
 WHEN duplicate_object THEN null;
END $$;

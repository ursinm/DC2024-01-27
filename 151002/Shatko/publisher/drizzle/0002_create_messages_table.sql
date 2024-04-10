CREATE TABLE IF NOT EXISTS "distcomp"."tbl_messages" (
	"id" serial PRIMARY KEY NOT NULL,
	"story_id" integer NOT NULL,
	"content" varchar(2048) NOT NULL
);
--> statement-breakpoint
DO $$ BEGIN
 ALTER TABLE "distcomp"."tbl_messages" ADD CONSTRAINT "tbl_messages_story_id_tbl_stories_id_fk" FOREIGN KEY ("story_id") REFERENCES "distcomp"."tbl_stories"("id") ON DELETE no action ON UPDATE no action;
EXCEPTION
 WHEN duplicate_object THEN null;
END $$;

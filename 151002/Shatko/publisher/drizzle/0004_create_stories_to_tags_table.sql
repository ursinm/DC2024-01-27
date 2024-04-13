CREATE TABLE IF NOT EXISTS "distcomp"."tbl_stories_to_tags" (
	"id" serial PRIMARY KEY NOT NULL,
	"story_id" integer NOT NULL,
	"tag_id" integer NOT NULL
);
--> statement-breakpoint
DO $$ BEGIN
 ALTER TABLE "distcomp"."tbl_stories_to_tags" ADD CONSTRAINT "tbl_stories_to_tags_story_id_tbl_editors_id_fk" FOREIGN KEY ("story_id") REFERENCES "distcomp"."tbl_editors"("id") ON DELETE no action ON UPDATE no action;
EXCEPTION
 WHEN duplicate_object THEN null;
END $$;
--> statement-breakpoint
DO $$ BEGIN
 ALTER TABLE "distcomp"."tbl_stories_to_tags" ADD CONSTRAINT "tbl_stories_to_tags_tag_id_tbl_tags_id_fk" FOREIGN KEY ("tag_id") REFERENCES "distcomp"."tbl_tags"("id") ON DELETE no action ON UPDATE no action;
EXCEPTION
 WHEN duplicate_object THEN null;
END $$;

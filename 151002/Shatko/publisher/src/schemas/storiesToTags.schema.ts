import { relations } from 'drizzle-orm';
import { integer, pgSchema, serial } from 'drizzle-orm/pg-core';
import { editors } from './editors.schema';
import { stories } from './stories.schema';
import { tags } from './tags.schema';

const schema = pgSchema('distcomp');

export const storiesToGroups = schema.table('tbl_stories_to_tags', {
  id: serial('id').primaryKey(),
  storyId: integer('story_id')
    .references(() => editors.id)
    .notNull(),
  tagId: integer('tag_id')
    .references(() => tags.id)
    .notNull(),
});

export const storiesToGroupsRelations = relations(storiesToGroups, ({ one }) => ({
  story: one(stories, {
    fields: [storiesToGroups.storyId],
    references: [stories.id],
  }),
  tag: one(tags, {
    fields: [storiesToGroups.tagId],
    references: [tags.id],
  }),
}));

export type StoryToTag = typeof storiesToGroups.$inferSelect;
export type NewStoryToTag = typeof storiesToGroups.$inferInsert;

import { relations } from 'drizzle-orm';
import { pgSchema, serial, varchar } from 'drizzle-orm/pg-core';
import { storiesToGroups } from './storiesToTags.schema';

const schema = pgSchema('distcomp');

export const tags = schema.table('tbl_tags', {
  id: serial('id').primaryKey(),
  name: varchar('name', { length: 32 }).notNull().unique(),
});

export const tagsRelations = relations(tags, ({ many }) => ({
  storiesToTags: many(storiesToGroups),
}));

export type Tag = typeof tags.$inferSelect;
export type NewTag = typeof tags.$inferInsert;

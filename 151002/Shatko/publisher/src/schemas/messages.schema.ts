import { relations } from 'drizzle-orm';
import { integer, pgSchema, serial, varchar } from 'drizzle-orm/pg-core';
import { stories } from './stories.schema';

const schema = pgSchema('distcomp');

export const messages = schema.table('tbl_messages', {
  id: serial('id').primaryKey(),
  storyId: integer('story_id')
    .references(() => stories.id)
    .notNull(),
  content: varchar('content', { length: 2048 }).notNull(),
});

export const messagesRelations = relations(messages, ({ one }) => ({
  story: one(stories, {
    fields: [messages.storyId],
    references: [stories.id],
  }),
}));

export type Message = typeof messages.$inferSelect;
export type NewMessage = typeof messages.$inferInsert;

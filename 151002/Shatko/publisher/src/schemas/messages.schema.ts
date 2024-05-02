import { relations } from "drizzle-orm";
import { integer, pgTable, serial, varchar } from "drizzle-orm/pg-core";
import { stories } from "./stories.schema";

export const messages = pgTable("tbl_message", {
  id: serial("id").primaryKey(),
  storyId: integer("story_id")
    .references(() => stories.id)
    .notNull(),
  content: varchar("content", { length: 2048 }).notNull(),
});

export const messagesRelations = relations(messages, ({ one }) => ({
  story: one(stories, {
    fields: [messages.storyId],
    references: [stories.id],
  }),
}));

export type Message = typeof messages.$inferSelect;
export type NewMessage = typeof messages.$inferInsert;

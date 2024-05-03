import {
  integer,
  pgTable,
  serial,
  timestamp,
  varchar,
} from "drizzle-orm/pg-core";
import { editors } from "./editors.schema";
import { relations } from "drizzle-orm";
import { messages } from "./messages.schema";
import { storiesToGroups } from "./storiesToTags.schema";

export const stories = pgTable("tbl_story", {
  id: serial("id").primaryKey(),
  editorId: integer("editor_id")
    .references(() => editors.id)
    .notNull(),
  title: varchar("title", { length: 64 }).notNull().unique(),
  content: varchar("content", { length: 2048 }).notNull(),
  created: timestamp("created").notNull().defaultNow(),
  modified: timestamp("modified").notNull().defaultNow(),
});

export const storiesRelations = relations(stories, ({ one, many }) => ({
  editor: one(editors, {
    fields: [stories.editorId],
    references: [editors.id],
  }),
  messages: many(messages),
  storiesToTags: many(storiesToGroups),
}));

export type Story = typeof stories.$inferSelect;
export type NewStory = typeof stories.$inferInsert;

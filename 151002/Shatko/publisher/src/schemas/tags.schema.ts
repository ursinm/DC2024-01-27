import { relations } from "drizzle-orm";
import { pgTable, serial, varchar } from "drizzle-orm/pg-core";
import { storiesToGroups } from "./storiesToTags.schema";

export const tags = pgTable("tbl_tag", {
  id: serial("id").primaryKey(),
  name: varchar("name", { length: 32 }).notNull().unique(),
});

export const tagsRelations = relations(tags, ({ many }) => ({
  storiesToTags: many(storiesToGroups),
}));

export type Tag = typeof tags.$inferSelect;
export type NewTag = typeof tags.$inferInsert;

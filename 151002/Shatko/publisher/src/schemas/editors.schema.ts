import { relations } from "drizzle-orm";
import { pgTable, serial, varchar } from "drizzle-orm/pg-core";
import { stories } from "./stories.schema";

export const editors = pgTable("tbl_editor", {
  id: serial("id").primaryKey(),
  login: varchar("login", { length: 64 }).notNull().unique(),
  password: varchar("password", { length: 128 }).notNull(),
  firstname: varchar("firstname", { length: 64 }).notNull(),
  lastname: varchar("lastname", { length: 64 }).notNull(),
});

export const editorsRelations = relations(editors, ({ many }) => ({
  stories: many(stories),
}));

export type Editor = typeof editors.$inferSelect;
export type NewEditor = typeof editors.$inferInsert;

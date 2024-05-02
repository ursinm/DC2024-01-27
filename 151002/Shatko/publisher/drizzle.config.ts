import "dotenv/config";
import type { Config } from "drizzle-kit";

export default {
  schema: "./src/schemas",
  out: "./drizzle",
  driver: "pg",
  dbCredentials: {
    host: process.env.DB_HOST as string,
    user: process.env.DB_USER as string,
    password: process.env.DB_PASSWORD as string,
    database: process.env.DB_DATABASE as string,
  },
} satisfies Config;

import "dotenv/config";
import { migrate } from "drizzle-orm/node-postgres/migrator";
import { drizzle } from "drizzle-orm/node-postgres";
import { Client } from "pg";

const client = new Client({
  connectionString: process.env.DB_URL,
});

async function bootstrap() {
  try {
    await client.connect();
    const db = drizzle(client);
    await migrate(db, { migrationsFolder: "drizzle" });
    console.log("Successfully applied migrations");
  } catch (error) {
    console.error(`Error while applying migrations:`);
    console.error(error);
  } finally {
    await client.end();
  }
}

bootstrap();

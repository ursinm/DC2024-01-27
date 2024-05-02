import { PROVIDERS } from "src/constants";
import { Client } from "pg";
import { drizzle } from "drizzle-orm/node-postgres";
import * as schema from "src/schemas";

export const DrizzleProvider = {
  provide: PROVIDERS.DRIZZLE,
  useFactory: async () => {
    const client = new Client({
      connectionString: process.env.DB_URL,
    });
    await client.connect();
    return drizzle(client, { schema });
  },
  exports: PROVIDERS.DRIZZLE,
};

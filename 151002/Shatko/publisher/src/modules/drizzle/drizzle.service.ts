import { Injectable } from '@nestjs/common';
import { NodePgClient, NodePgDatabase, drizzle } from 'drizzle-orm/node-postgres';
import { Client } from 'pg';
import * as schema from 'src/schemas/editors.schema';

@Injectable()
export class DrizzleService {
  private client: NodePgClient;
  private drizzle: NodePgDatabase<typeof schema>;

  constructor() {
    this.client = new Client({
      connectionString: process.env.DB_URL,
    });
  }

  async connect() {
    await this.client.connect();
    this.drizzle = drizzle(this.client, { schema });
  }

  getDatabase() {
    return this.drizzle;
  }
}

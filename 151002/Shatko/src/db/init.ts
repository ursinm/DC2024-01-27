import { Client } from "pg";
import dotenv from "dotenv";

dotenv.config();

export const client = new Client({
  host: process.env.DB_HOST,
  port: Number(process.env.DB_PORT),
  database: process.env.DB_DATABASE,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
});

export const schema = process.env.DB_SCHEMA;

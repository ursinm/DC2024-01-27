import express, { Express } from "express";
import dotenv from "dotenv";

import editors from "./routes/editors";
import stories from "./routes/stories";
import messages from "./routes/messages";
import tags from "./routes/tags";
import { client } from "./db/init";

dotenv.config();

const app: Express = express();
app.use(express.json());

const apiv1 = "/api/v1.0";

app.use(`${apiv1}/editors`, editors);
app.use(`${apiv1}/storys`, stories);
app.use(`${apiv1}/messages`, messages);
app.use(`${apiv1}/tags`, tags);

const port = process.env.PORT || 24110;

app.listen(port, async () => {
  await client.connect();
  console.log(`[server]: Server is running at http://localhost:${port}`);
});

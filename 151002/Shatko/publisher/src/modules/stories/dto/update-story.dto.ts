import { createZodDto } from "nestjs-zod";
import { z } from "zod";

const Schema = z.object({
  id: z.number(),
  editorId: z.number(),
  title: z.string().min(2).max(64),
  content: z.string().min(4).max(2048),
});

export class UpdateStoryDto extends createZodDto(Schema) {}

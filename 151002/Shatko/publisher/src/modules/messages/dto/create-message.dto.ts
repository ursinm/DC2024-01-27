import { createZodDto } from "nestjs-zod";
import { z } from "zod";

const Schema = z.object({
  storyId: z.number(),
  content: z.string().min(2).max(2048),
});

export class CreateMessageDto extends createZodDto(Schema) {}

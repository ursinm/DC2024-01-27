import { createZodDto } from "nestjs-zod";
import { z } from "zod";

const Schema = z.object({
  id: z.number(),
  name: z.string().min(2).max(32),
});

export class UpdateTagDto extends createZodDto(Schema) {}

import { createZodDto } from "nestjs-zod";
import { z } from "zod";

const Schema = z.object({
  name: z.string().min(2).max(32),
});

export class CreateTagDto extends createZodDto(Schema) {}

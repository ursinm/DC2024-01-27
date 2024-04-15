import { createZodDto } from "nestjs-zod";
import { z } from "zod";

const Schema = z.object({
  id: z.number(),
  login: z.string().min(2).max(64),
  password: z.string().min(8).max(128),
  firstname: z.string().min(2).max(64),
  lastname: z.string().min(2).max(64),
});

export class UpdateEditorDto extends createZodDto(Schema) {}

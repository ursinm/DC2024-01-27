import { createZodDto } from 'nestjs-zod';
import { z } from 'zod';

const Schema = z.object({
  id: z.number(),
  storyId: z.number(),
  content: z.string().min(2).max(2048),
});

export class UpdateMessageDto extends createZodDto(Schema) {}

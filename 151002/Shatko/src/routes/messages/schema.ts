import { z } from 'zod';

export const createMessageSchema = z.object({
    body: z.object({
        storyId: z.number(),
        content: z.string().min(2).max(2048),
    }),
});

export const updateMessageSchema = z.object({
    body: z.object({
        id: z.number(),
        storyId: z.number(),
        content: z.string().min(2).max(2048),
    }),
});

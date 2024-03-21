import { z } from 'zod';

export const createStorySchema = z.object({
    body: z.object({
        editorId: z.number(),
        title: z.string().min(2).max(64),
        content: z.string().min(4).max(2048),
    }),
});

export const updateStorySchema = z.object({
    body: z.object({
        id: z.number(),
        editorId: z.number(),
        title: z.string().min(2).max(64),
        content: z.string().min(4).max(2048),
    }),
});

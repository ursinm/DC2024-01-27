import { z } from 'zod';

export const createTagSchema = z.object({
    body: z.object({
        name: z.string().min(2).max(32),
    }),
});

export const updateTagSchema = z.object({
    body: z.object({
        id: z.number(),
        name: z.string().min(2).max(32),
    }),
});

import { z } from 'zod';

export const createEditorSchema = z.object({
    body: z.object({
        login: z.string().min(2).max(64),
        password: z.string().min(8).max(128),
        firstname: z.string().min(2).max(64),
        lastname: z.string().min(2).max(64),
    }),
});

export const updateEditorSchema = z.object({
    body: z.object({
        id: z.number(),
        login: z.string().min(2).max(64),
        password: z.string().min(8).max(128),
        firstname: z.string().min(2).max(64),
        lastname: z.string().min(2).max(64),
    }),
});

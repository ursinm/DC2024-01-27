import { z } from 'zod';

export const getAllSchema = z.object({
    query: z.object({
        limit: z.string().optional(),
        offset: z.string().optional(),
        filter: z.string().optional(),
        sort: z.string().optional(),
    }),
});

export const getByPkSchema = z.object({
    params: z.object({
        id: z.string(),
    }),
});

export const deleteSchema = z.object({
    params: z.object({
        id: z.string(),
    }),
});

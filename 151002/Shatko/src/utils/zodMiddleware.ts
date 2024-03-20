import { Request, Response, NextFunction } from 'express';
import { ZodObject } from 'zod';

const checkQuery = (query: object, result: { [key: string]: any }) => {
    if (!!Object.keys(query).length && JSON.stringify(query) !== JSON.stringify(result.query)) {
        throw Error();
    }
};

export const zodMiddleware =
    (schema: ZodObject<any>) => (req: Request, res: Response, next: NextFunction) => {
        try {
            const result = schema.parse({
                body: req.body,
                query: req.query,
                params: req.params,
            });
            checkQuery(req.query, result);
            next();
        } catch (error) {
            return res.status(422).json({ msg: 'Validation error' });
        }
    };

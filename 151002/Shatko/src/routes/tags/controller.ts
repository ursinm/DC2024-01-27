import { Request, Response } from 'express';
import { Tag } from '../../models';
import crudService from '../../services/crudService';
import { GetQueryParams, TABLES } from '../../types';

class TagsController {
    getTags = async (req: Request<unknown, unknown, unknown, GetQueryParams>, res: Response) => {
        try {
            const result = await crudService.getAll<Tag>(TABLES.TAGS, req.query);
            res.status(200).json(result);
        } catch (e) {
            res.status(500).json({ msg: 'Error while getting tags' });
        }
    };

    getTag = async (req: Request<{ id: string }>, res: Response) => {
        const { id } = req.params;
        try {
            if (!(await crudService.isEntityPresentByPk(id, TABLES.TAGS))) {
                return res.status(404).json({ msg: 'Tag not found' });
            }

            const result = await crudService.getByPk<Tag>(id, TABLES.TAGS);
            res.status(200).json(result);
        } catch (e) {
            res.status(500).json({ msg: 'Error while getting tag' });
        }
    };

    createTag = async (req: Request<unknown, unknown, Omit<Tag, 'id'>>, res: Response) => {
        try {
            if (await crudService.isEntityPresent('name', req.body.name, TABLES.TAGS)) {
                return res.status(403).json({ msg: 'Tag with this name already exists' });
            }

            const result = await crudService.create<Tag>(req.body, TABLES.TAGS);
            res.status(201).json(result);
        } catch (e) {
            res.status(500).json({ msg: 'Error while creating tag' });
        }
    };

    deleteTag = async (req: Request<{ id: string }>, res: Response) => {
        const { id } = req.params;
        try {
            if (!(await crudService.isEntityPresentByPk(id, TABLES.TAGS))) {
                return res.status(404).json({ msg: 'Tag not found' });
            }

            await crudService.delete(id, TABLES.TAGS);
            res.status(204).json();
        } catch (e) {
            res.status(500).json({ msg: 'Error while deleting tag' });
        }
    };

    updateTag = async (req: Request<unknown, unknown, Tag>, res: Response) => {
        const { id } = req.body;
        try {
            if (!(await crudService.isEntityPresentByPk(id, TABLES.TAGS))) {
                return res.status(404).json({ msg: 'Tag not found' });
            }

            if (await crudService.isEntityPresent('name', req.body.name, TABLES.TAGS)) {
                return res.status(403).json({ msg: 'Tag with this name already exists' });
            }

            const result = await crudService.update(req.body, TABLES.TAGS);
            res.status(200).json(result);
        } catch (e) {
            res.status(500).json({ msg: 'Error while updating tag' });
        }
    };
}

export default new TagsController();

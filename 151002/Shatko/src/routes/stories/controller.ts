import { Request, Response } from 'express';
import { Story } from '../../models';
import crudService from '../../services/crudService';
import { GetQueryParams, TABLES } from '../../types';

class StoriesController {
    getStories = async (req: Request<unknown, unknown, unknown, GetQueryParams>, res: Response) => {
        try {
            const result = await crudService.getAll<Story>(TABLES.STORIES, req.query);
            res.status(200).json(result);
        } catch (e) {
            res.status(500).json({ msg: 'Error while getting stories' });
        }
    };

    getStory = async (req: Request<{ id: string }>, res: Response) => {
        const { id } = req.params;
        try {
            if (!(await crudService.isEntityPresentByPk(id, TABLES.STORIES))) {
                return res.status(404).json({ msg: 'Story not found' });
            }

            const result = await crudService.getByPk<Story>(id, TABLES.STORIES);
            res.status(200).json(result);
        } catch (e) {
            res.status(500).json({ msg: 'Error while getting story' });
        }
    };

    createStory = async (req: Request<unknown, unknown, Omit<Story, 'id'>>, res: Response) => {
        try {
            if (await crudService.isEntityPresent('title', req.body.title, TABLES.STORIES)) {
                return res.status(403).json({ msg: 'Story with this title already exists' });
            }

            if (!(await crudService.isEntityPresent('id', req.body.editorId, TABLES.EDITORS))) {
                return res.status(404).json({ msg: 'Editor not found' });
            }

            const result = await crudService.create<Story>(req.body, TABLES.STORIES);
            res.status(201).json(result);
        } catch (e) {
            res.status(500).json({ msg: 'Error while creating story' });
        }
    };

    deleteStory = async (req: Request<{ id: string }>, res: Response) => {
        const { id } = req.params;
        try {
            if (!(await crudService.isEntityPresentByPk(id, TABLES.STORIES))) {
                return res.status(404).json({ msg: 'Story not found' });
            }

            await crudService.delete(id, TABLES.STORIES);
            res.status(204).json();
        } catch (e) {
            res.status(500).json({ msg: 'Error while deleting story' });
        }
    };

    updateStory = async (req: Request<unknown, unknown, Story>, res: Response) => {
        const { id } = req.body;
        try {
            if (!(await crudService.isEntityPresentByPk(id, TABLES.STORIES))) {
                return res.status(404).json({ msg: 'Story not found' });
            }

            if (await crudService.isEntityPresent('title', req.body.title, TABLES.STORIES)) {
                return res.status(403).json({ msg: 'Story with this title already exists' });
            }

            if (!(await crudService.isEntityPresent('id', req.body.editorId, TABLES.EDITORS))) {
                return res.status(404).json({ msg: 'Editor not found' });
            }

            const result = await crudService.update(req.body, TABLES.STORIES, true);
            res.status(200).json(result);
        } catch (e) {
            res.status(500).json({ msg: 'Error while updating story' });
        }
    };
}

export default new StoriesController();

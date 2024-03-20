import { Request, Response } from 'express';
import { Message } from '../../models';
import crudService from '../../services/crudService';
import { GetQueryParams, TABLES } from '../../types';

class MessagesController {
    getMessages = async (
        req: Request<unknown, unknown, unknown, GetQueryParams>,
        res: Response,
    ) => {
        try {
            const result = await crudService.getAll<Message>(TABLES.MESSAGES, req.query);
            res.status(200).json(result);
        } catch (e) {
            res.status(500).json({ msg: 'Error while getting messages' });
        }
    };

    getMessage = async (req: Request<{ id: string }>, res: Response) => {
        const { id } = req.params;
        try {
            if (!(await crudService.isEntityPresentByPk(id, TABLES.MESSAGES))) {
                return res.status(404).json({ msg: 'Message not found' });
            }

            const result = await crudService.getByPk<Message>(id, TABLES.MESSAGES);
            res.status(200).json(result);
        } catch (e) {
            res.status(500).json({ msg: 'Error while getting message' });
        }
    };

    createMessage = async (req: Request<unknown, unknown, Omit<Message, 'id'>>, res: Response) => {
        try {
            if (!(await crudService.isEntityPresent('id', req.body.storyId, TABLES.STORIES))) {
                return res.status(404).json({ msg: 'Story not found' });
            }

            const result = await crudService.create<Message>(req.body, TABLES.MESSAGES);
            res.status(201).json(result);
        } catch (e) {
            res.status(500).json({ msg: 'Error while creating message' });
        }
    };

    deleteMessage = async (req: Request<{ id: string }>, res: Response) => {
        const { id } = req.params;
        try {
            if (!(await crudService.isEntityPresentByPk(id, TABLES.MESSAGES))) {
                return res.status(404).json({ msg: 'Message not found' });
            }

            await crudService.delete(id, TABLES.MESSAGES);
            res.status(204).json();
        } catch (e) {
            res.status(500).json({ msg: 'Error while deleting message' });
        }
    };

    updateMessage = async (req: Request<unknown, unknown, Message>, res: Response) => {
        const { id } = req.body;
        try {
            if (!(await crudService.isEntityPresentByPk(id, TABLES.MESSAGES))) {
                return res.status(404).json({ msg: 'Message not found' });
            }

            if (!(await crudService.isEntityPresent('id', req.body.storyId, TABLES.STORIES))) {
                return res.status(404).json({ msg: 'Story not found' });
            }

            const result = await crudService.update(req.body, TABLES.MESSAGES);
            res.status(200).json(result);
        } catch (e) {
            res.status(500).json({ msg: 'Error while updating message' });
        }
    };
}

export default new MessagesController();

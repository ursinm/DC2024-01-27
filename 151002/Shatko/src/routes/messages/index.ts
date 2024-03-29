import express from 'express';
import controller from './controller';
import { zodMiddleware } from '../../utils/zodMiddleware';
import { createMessageSchema, updateMessageSchema } from './schema';
import { deleteSchema, getAllSchema, getByPkSchema } from '../../utils/commonSchema';

const router = express.Router();

router.get('/', zodMiddleware(getAllSchema), controller.getMessages);
router.get('/:id', zodMiddleware(getByPkSchema), controller.getMessage);
router.post('/', zodMiddleware(createMessageSchema), controller.createMessage);
router.delete('/:id', zodMiddleware(deleteSchema), controller.deleteMessage);
router.put('/', zodMiddleware(updateMessageSchema), controller.updateMessage);

export default router;

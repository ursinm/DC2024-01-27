import express from 'express';
import controller from './controller';
import { zodMiddleware } from '../../utils/zodMiddleware';
import { createTagSchema, updateTagSchema } from './schema';
import { deleteSchema, getAllSchema, getByPkSchema } from '../../utils/commonSchema';

const router = express.Router();

router.get('/', zodMiddleware(getAllSchema), controller.getTags);
router.get('/:id', zodMiddleware(getByPkSchema), controller.getTag);
router.post('/', zodMiddleware(createTagSchema), controller.createTag);
router.delete('/:id', zodMiddleware(deleteSchema), controller.deleteTag);
router.put('/', zodMiddleware(updateTagSchema), controller.updateTag);

export default router;

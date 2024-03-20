import express from 'express';
import controller from './controller';
import { zodMiddleware } from '../../utils/zodMiddleware';
import { createStorySchema, updateStorySchema } from './schema';
import { deleteSchema, getAllSchema, getByPkSchema } from '../../utils/commonSchema';

const router = express.Router();

router.get('/', zodMiddleware(getAllSchema), controller.getStories);
router.get('/:id', zodMiddleware(getByPkSchema), controller.getStory);
router.post('/', zodMiddleware(createStorySchema), controller.createStory);
router.delete('/:id', zodMiddleware(deleteSchema), controller.deleteStory);
router.put('/', zodMiddleware(updateStorySchema), controller.updateStory);

export default router;

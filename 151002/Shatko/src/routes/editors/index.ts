import express from 'express';
import controller from './controller';
import { zodMiddleware } from '../../utils/zodMiddleware';
import { createEditorSchema, updateEditorSchema } from './schema';
import { deleteSchema, getAllSchema, getByPkSchema } from '../../utils/commonSchema';

const router = express.Router();

router.get('/', zodMiddleware(getAllSchema), controller.getEditors);
router.get('/:id', zodMiddleware(getByPkSchema), controller.getEditor);
router.post('/', zodMiddleware(createEditorSchema), controller.createEditor);
router.delete('/:id', zodMiddleware(deleteSchema), controller.deleteEditor);
router.put('/', zodMiddleware(updateEditorSchema), controller.updateEditor);

export default router;

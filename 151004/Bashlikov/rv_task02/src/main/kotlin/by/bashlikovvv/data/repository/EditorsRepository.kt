package by.bashlikovvv.data.repository

import by.bashlikovvv.data.local.dao.EditorOfflineSource
import by.bashlikovvv.data.local.model.EditorEntity
import by.bashlikovvv.domain.exception.DataSourceExceptions
import by.bashlikovvv.domain.repository.IEditorsRepository

class EditorsRepository(
    private val editorOfflineSource: EditorOfflineSource
) : IEditorsRepository {
    override suspend fun create(editorEntity: EditorEntity): Long {
        return editorOfflineSource.create(editorEntity)
    }

    override suspend fun read(id: Long): EditorEntity? {
        return try {
            editorOfflineSource.read(id)
        } catch (e: DataSourceExceptions.RecordNotFoundException) {
            null
        }
    }

    override suspend fun readAll(): List<EditorEntity?> {
        return editorOfflineSource.readAll()
    }

    override suspend fun update(id: Long, editorEntity: EditorEntity): Boolean {
        return editorOfflineSource.update(id, editorEntity) > 0
    }

    override suspend fun delete(id: Long): Boolean {
        return editorOfflineSource.delete(id) > 0
    }

}
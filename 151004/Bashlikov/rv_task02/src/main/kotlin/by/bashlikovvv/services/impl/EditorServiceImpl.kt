package by.bashlikovvv.services.impl

import by.bashlikovvv.api.dto.mapper.EditorEntityToEditorDtoMapper
import by.bashlikovvv.api.dto.request.CreateEditorDto
import by.bashlikovvv.api.dto.request.UpdateEditorDto
import by.bashlikovvv.api.dto.response.EditorDto
import by.bashlikovvv.data.mapper.CreateEditorDtoToEditorMapper
import by.bashlikovvv.data.mapper.UpdateEditorDtoToEditorMapper
import by.bashlikovvv.domain.exception.ApplicationExceptions
import by.bashlikovvv.domain.repository.IEditorsRepository
import by.bashlikovvv.services.EditorService

class EditorServiceImpl(
    private val editorRepository: IEditorsRepository
) : EditorService {

    private val mapper = EditorEntityToEditorDtoMapper()

    override suspend fun create(createEditorDto: CreateEditorDto): EditorDto {
        val editorEntity = CreateEditorDtoToEditorMapper().mapFromEntity(createEditorDto)
        val id = editorRepository.create(editorEntity)

        return mapper.mapFromEntity(editorEntity.copy(id = id))
    }

    override suspend fun update(editorId: Long, updateEditorDto: UpdateEditorDto): EditorDto {
        val editorEntity = UpdateEditorDtoToEditorMapper().mapFromEntity(updateEditorDto)
        if (!editorRepository.update(editorId, editorEntity)) {
            throw ApplicationExceptions.UpdateException("Exception during editor updating")
        }

        return mapper.mapFromEntity(editorEntity)
    }

    override suspend fun getById(editorId: Long): EditorDto? {
        val editorEntity = editorRepository.read(editorId) ?: return null

        return mapper.mapFromEntity(editorEntity)
    }

    override suspend fun getAll(): List<EditorDto?> {
        return editorRepository.readAll()
            .filterNotNull()
            .map { mapper.mapFromEntity(it) }
    }

    override suspend fun delete(editorId: Long): Boolean {
        return editorRepository.delete(editorId)
    }

}
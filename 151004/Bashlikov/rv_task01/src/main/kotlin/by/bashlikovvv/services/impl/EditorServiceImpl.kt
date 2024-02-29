package by.bashlikovvv.services.impl

import by.bashlikovvv.api.dto.mappers.CreateEditorDtoToEditorDtoMapper
import by.bashlikovvv.api.dto.mappers.UpdateEditorDtoToEditorDtoMapper
import by.bashlikovvv.api.dto.request.CreateEditorDto
import by.bashlikovvv.api.dto.request.UpdateEditorDto
import by.bashlikovvv.api.dto.response.EditorDto
import by.bashlikovvv.domain.repository.IEditorsRepository
import by.bashlikovvv.services.EditorService

class EditorServiceImpl(
    private val editorRepository: IEditorsRepository
) : EditorService {

    override fun create(createEditorDto: CreateEditorDto): EditorDto? {
        val lastItemId = if (editorRepository.data.isEmpty()) {
            -1
        } else {
            editorRepository.getLastItem()?.id ?: return null
        }
        val mapper = CreateEditorDtoToEditorDtoMapper(lastItemId + 1)
        val savedEntity = editorRepository.addItem(
            id = lastItemId + 1,
            mapper.mapFromEntity(createEditorDto)
        )

        return savedEntity
    }

    override fun update(editorId: Long, updateEditorDto: UpdateEditorDto): EditorDto? {
        val mapper = UpdateEditorDtoToEditorDtoMapper()
        val editorDto: EditorDto = mapper.mapFromEntity(updateEditorDto)
        val savedEntity = editorRepository.addItem(editorId, editorDto)

        return savedEntity
    }

    override fun getById(editorId: Long): EditorDto? {
        val savedEntity = editorRepository.getItemById(editorId)

        return savedEntity?.second
    }

    override fun getAll(): List<EditorDto> {
        return editorRepository.data.map { it.second }
    }

    override fun delete(editorId: Long): Boolean {
        return editorRepository.removeItem(editorId)
    }

    override fun getByLogin(login: String): EditorDto? {
        return editorRepository.data.find { it.second.login == login }?.second
    }
}
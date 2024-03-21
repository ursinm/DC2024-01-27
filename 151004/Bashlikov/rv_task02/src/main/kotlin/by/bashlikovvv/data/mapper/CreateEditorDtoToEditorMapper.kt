package by.bashlikovvv.data.mapper

import by.bashlikovvv.api.dto.request.CreateEditorDto
import by.bashlikovvv.data.local.model.EditorEntity
import by.bashlikovvv.util.IMapper

class CreateEditorDtoToEditorMapper(
    private val id: Long? = null
) : IMapper<CreateEditorDto, EditorEntity> {
    override fun mapFromEntity(entity: CreateEditorDto): EditorEntity {
        return EditorEntity(
            id = id ?: 0,
            login = entity.login,
            password = entity.password,
            firstname = entity.firstname,
            lastname = entity.lastname
        )
    }

    override fun mapToEntity(domain: EditorEntity): CreateEditorDto {
        return CreateEditorDto(
            login = domain.login,
            password = domain.password,
            firstname = domain.firstname,
            lastname = domain.lastname
        )
    }
}
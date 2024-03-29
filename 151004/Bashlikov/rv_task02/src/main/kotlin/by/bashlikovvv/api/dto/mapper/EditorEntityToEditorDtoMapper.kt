package by.bashlikovvv.api.dto.mapper

import by.bashlikovvv.api.dto.response.EditorDto
import by.bashlikovvv.data.local.model.EditorEntity
import by.bashlikovvv.util.IMapper

class EditorEntityToEditorDtoMapper : IMapper<EditorEntity, EditorDto> {
    override fun mapFromEntity(entity: EditorEntity): EditorDto {
        return EditorDto(
            id = entity.id,
            login = entity.login,
            password = entity.password,
            firstname = entity.firstname,
            lastname = entity.lastname
        )
    }

    override fun mapToEntity(domain: EditorDto): EditorEntity {
        return EditorEntity(
            id = domain.id,
            login = domain.login,
            password = domain.password,
            firstname = domain.firstname,
            lastname = domain.lastname
        )
    }
}
package by.bashlikovvv.api.dto.mappers

import by.bashlikovvv.api.dto.request.UpdateEditorDto
import by.bashlikovvv.api.dto.response.EditorDto
import by.bashlikovvv.util.IMapper

class UpdateEditorDtoToEditorDtoMapper : IMapper<UpdateEditorDto, EditorDto> {
    override fun mapFromEntity(entity: UpdateEditorDto): EditorDto {
        return EditorDto(
            id = entity.id,
            login = entity.login,
            password = entity.password,
            firstname = entity.firstname,
            lastname = entity.lastname
        )
    }

    override fun mapToEntity(domain: EditorDto): UpdateEditorDto {
        return UpdateEditorDto(
            id = domain.id,
            login = domain.login,
            password = domain.password,
            firstname = domain.firstname,
            lastname = domain.lastname
        )
    }
}
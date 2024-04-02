package by.bashlikovvv.api.dto.mappers

import by.bashlikovvv.api.dto.request.UpdateEditorDto
import by.bashlikovvv.domain.model.Editor
import by.bashlikovvv.util.IMapper

class UpdateEditorDtoToEditorMapper : IMapper<UpdateEditorDto, Editor> {
    override fun mapFromEntity(entity: UpdateEditorDto): Editor {
        return Editor(
            id = entity.id,
            login = entity.login,
            password = entity.password,
            firstname = entity.firstname,
            lastname = entity.lastname
        )
    }

    override fun mapToEntity(domain: Editor): UpdateEditorDto {
        return UpdateEditorDto(
            id = domain.id,
            login = domain.login,
            password = domain.password,
            firstname = domain.firstname,
            lastname = domain.lastname
        )
    }
}
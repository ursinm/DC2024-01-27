package by.bashlikovvv.data.mapper

import by.bashlikovvv.api.dto.request.UpdateEditorDto
import by.bashlikovvv.data.local.model.EditorEntity
import by.bashlikovvv.util.IMapper

class UpdateEditorDtoToEditorMapper : IMapper<UpdateEditorDto, EditorEntity> {
    override fun mapFromEntity(entity: UpdateEditorDto): EditorEntity {
        return EditorEntity(
            id = entity.id,
            login = entity.login,
            password = entity.password,
            firstname = entity.firstname,
            lastname = entity.lastname
        )
    }

    override fun mapToEntity(domain: EditorEntity): UpdateEditorDto {
        return UpdateEditorDto(
            id = domain.id,
            login = domain.login,
            password = domain.password,
            firstname = domain.firstname,
            lastname = domain.lastname
        )
    }
}